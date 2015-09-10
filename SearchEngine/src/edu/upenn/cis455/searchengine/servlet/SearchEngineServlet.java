package edu.upenn.cis455.searchengine.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.util.Pair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.upenn.cis455.searchengine.driver.CommonFileDriver;
import edu.upenn.cis455.searchengine.handler.SearchHandler;

public class SearchEngineServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SearchHandler handler;
	public static String[] indexerAddresses;
	// public static String root = "file:///home/juntao/word-files/";
	public static String root = "/home/bitnami/"; // TODO root address
	public static String host = "http://ec2-52-4-140-62.compute-1.amazonaws.com"; // TODO host address
	//public static String host = "http://localhost:8080";
	public static String input = "input";
	public static String output = "out";
	private static ArrayList<Pair<String, Double>> finalList = new ArrayList<Pair<String, Double>>();
	private StanfordCoreNLP pipeline = null;
	private static final int resultLimit = 100;
	private static JLanguageTool langTool = new JLanguageTool(
			new AmericanEnglish());
	private static List<RuleMatch> matches;
	public static ToDB myDB;
	private ArrayList<Pair<String, Double>> returnList = new ArrayList<Pair<String, Double>>();
	public void init() {
		// get the index addresses from context parameters
		String addresses = getServletContext().getInitParameter("Indexers");
		indexerAddresses = addresses.split(",");
		SearchHandler.setAddresses(indexerAddresses);
		handler = new SearchHandler(root, input);
		Properties properties = new Properties();
		properties.setProperty("annotators",
				"tokenize, ssplit, pos, lemma, ner");
		pipeline = new StanfordCoreNLP(properties);
		myDB = new ToDB("/home/muruoliu/db"); //TODO db location
	}

	public static void setFinalList(ArrayList<Pair<String, Double>> list) {
		finalList = list;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		// get search words first
		String searchFor = req.getParameter("searchwords");
		Annotation annotation = pipeline.process(searchFor);
		List<CoreLabel> words = annotation
				.get(CoreAnnotations.TokensAnnotation.class);
		StringBuffer stringBuffer = new StringBuffer();
		String prev = "";
		ArrayList<String> searchWords = new ArrayList<String>();
		ArrayList<String> alterSearchWords = new ArrayList<String>();
		boolean diff = false;
		for (CoreLabel word : words) {
			if (word.originalText().matches(".*[a-zA-Z0-9].*")) {
				alterSearchWords.add(word.lemma().toLowerCase());
				String ner = word.ner();
				if (!ner.equals("O")) {
					diff = true;
					if (stringBuffer.length() == 0 || prev.equals(ner)) {
						stringBuffer.append(word.originalText() + " ");
						prev = ner;
					} else {
						String curr = stringBuffer.toString();
						searchWords.add(curr.substring(0, curr.length() - 1));
						stringBuffer = new StringBuffer();
						stringBuffer.append(word.originalText());
					}
				} else {
					if (stringBuffer.length() != 0) {
						String curr = stringBuffer.toString();
						searchWords.add(curr.substring(0, curr.length() - 1));
						stringBuffer = new StringBuffer();
					}
					searchWords.add(word.lemma().toLowerCase());
				}
			}
		}
		if (stringBuffer.length() != 0) {
			String curr = stringBuffer.toString();
			searchWords.add(curr.substring(0, curr.length() - 1));
		}

		// search the given words, rank and return results
		handleSearch(searchWords);
		FileUtils.cleanDirectory(new File(root + input));
		FileUtils.deleteDirectory(new File(root + output));
		returnList.addAll(finalList);
		if (finalList.size() < resultLimit && diff) {
			L1: for (int i = alterSearchWords.size(); i > 0; i--) {
				try {
					CombinationIterator<String> combinationIterator = new CombinationIterator<String>(
							alterSearchWords, i);
					while (combinationIterator.hasNext()) {
						handleSearch(combinationIterator.next());
						FileUtils.cleanDirectory(new File(root + input));
						FileUtils.deleteDirectory(new File(root + output));
						returnList.addAll(finalList);
						if (finalList.size() > resultLimit)
							break L1;
					}
				} catch (IllegalArgumentException e1) {

				} catch (NoSuchElementException e2) {

				}
			}
		}
		res.setContentType("text/html");
		PrintWriter printWriter = res.getWriter();
		for (int i = 0; i < returnList.size(); i++) {
			printWriter.println(returnList.get(i).getFirst() + "\t"
					+ returnList.get(i).getSecond());
			if (i >= 100)
				break;
		}
		returnList = new ArrayList<Pair<String, Double>>();
		try {
			String pre = host + "/SearchEngine/startsearch?searchwords=";
			matches = langTool.check(searchFor);
			ArrayList<String> out = new ArrayList<String>();
			ArrayList<String> in = new ArrayList<String>();
			int start = 0;
			boolean outFirst = false;
			for (RuleMatch match : matches) {
				String potential = match.getSuggestedReplacements().get(0);
				if(potential.equals(searchFor.substring(match.getColumn() - 1, match.getEndColumn() - 1).toLowerCase())) {
					continue;
				}
				if (match.getColumn() != 1) {
					out.add(searchFor.substring(start, match.getColumn() - 1));
					outFirst = true;
				}
				in.add(potential);
				start = match.getEndColumn() - 1;
			}
			if (start != searchFor.length()) {
				out.add(searchFor.substring(start));
			}
			StringBuffer fixed = new StringBuffer();
			int i = 0;
			if(outFirst) {
				while(i < in.size() && i < out.size()) {
					fixed.append(out.get(i));
					fixed.append(in.get(i));
					i++;
				}
				if(i < out.size())fixed.append(out.get(out.size() - 1));
			}
			else{
				while(i < in.size() && i < out.size()) {
					fixed.append(in.get(i));
					fixed.append(out.get(i));
					i++;
				}
				if(i < in.size())fixed.append(in.get(in.size() - 1));
			}
			if(fixed.length() != 0 && !fixed.toString().toLowerCase().equals(searchFor)) {
				String urlString = URLEncoder.encode(pre + fixed.toString(), "UTF-8");
				printWriter.println("<a href=\"" + urlString + "\">" + fixed
						+ "</a>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy(){
		myDB.close();
	}
	
	private void handleSearch(List<String> words) throws IOException {
		Set<String> uniqueWords = new HashSet<String>(words);
		handler.search(uniqueWords);
		CommonFileDriver driver = new CommonFileDriver(uniqueWords.size(),
				root, input, output);
		try {
			if (ToolRunner
					.run(new Configuration(), driver, new String[] { "" }) == 1)
				System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileUtils.cleanDirectory(new File(root + input));
		FileUtils.deleteDirectory(new File(root + output));
	}
}
