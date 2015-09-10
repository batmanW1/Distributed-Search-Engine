package edu.upenn.cis455.searchengine.mapreduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.math3.util.Pair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import edu.upenn.cis.cis455.sortResults.PageScore;
import edu.upenn.cis455.searchengine.compute.FindCommon;
import edu.upenn.cis455.searchengine.compute.WordPageScore;
import edu.upenn.cis455.searchengine.handler.SearchHandler;
import edu.upenn.cis455.searchengine.servlet.SearchEngineServlet;

public class CommonFileReducer extends Reducer<Text, Text, Text, Text> {
	private static final String TITLE = "1";
	private static final String META = "2";
	private static final String ANCHOR = "3";
	private static final String TEXT = "4";
	private static final double TITLE_VAL = 0.7;
	private static final double META_VAL = 0.15;
	private static final double ANCHOR_VAL = 0.1;
	private static final double TEXT_VAL = 0.05;
	private ArrayList<Pair<String, Double>> finalList;
	protected void setup(Context context) throws IOException,
			InterruptedException {
		finalList = new ArrayList<Pair<String, Double>>();
	}

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		// get number of words to be searched
		Configuration conf = context.getConfiguration();
		int numWords = Integer.parseInt(conf.get("wordNum"));

		// if current doc contains all searched word,
		int count = 0;
		HashSet<String> wordSet = new HashSet<String>();
		for (Text value : values) {
			count++;
			wordSet.add(value.toString());
		}
		if (count == numWords) {
			SearchHandler searchHandler = new SearchHandler();
			String ip = searchHandler.getAddressToSend(key.toString());
			StringBuffer query = new StringBuffer();
			query.append("doc=" + key.toString() + "&");
			query.append("word=");
			for (String val : wordSet) {
				query.append(val + ",");
			}
			String qString = query.toString();
			// URL url = new URL("http://" + ip + ":8080" +
			// "/Indexer/searchdocument" + "?"
			// + qString.substring(0, qString.length() - 1)); //TODO ip address
			URL url = new URL(ip + "/Indexer/searchdocument" + "?"
					+ qString.substring(0, qString.length() - 1));
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// read response
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String line = in.readLine();
				in.close();
				System.out.println(line);
				if (line != null && !line.equals("invalid")) {
					String[] words = line.split("`");
					if (words.length <= 5) {
						System.out.println("no list");
					} else {
						try {
							int allDocNum = Integer.parseInt(words[0]);
							int avgDocLength = Integer.parseInt(words[1]);
							String link = words[2];
							int docLength = Integer.parseInt(words[3]);
							int maxFreq = Integer.parseInt(words[4]);
							ArrayList<Pair<String, String[]>> positionList = new ArrayList<Pair<String, String[]>>();
							HashMap<String, String> typeMap = new HashMap<String, String>();
							HashMap<String, String> freqMap = new HashMap<String, String>();
							String[] temp = new String[2];
							String[] temp2 = new String[3];
							String[] positions = null;
							double res = 0.0;
							for (int i = 5; i < words.length; i++) {
								String word = words[i];
								temp = word.split(":");
								temp2 = temp[1].split(";");
								positions = temp2[0].split(",");
								Pair<String, String[]> positionPair = new Pair<String, String[]>(
										temp[0], positions);
								positionList.add(positionPair);
								typeMap.put(temp[0], temp2[1]);
								freqMap.put(temp[0],
										CommonFileMapper.freq.get(DigestUtils.md5Hex(temp[0])));
							}
							FindCommon findCommon = new FindCommon(positionList);
							if (words.length == 6 || findCommon.find()) {
								context.write(new Text(key), new Text());
								for (int i = 0; i < positionList.size(); i++) {
									Pair<String, String[]> tempPair = positionList
											.get(i);
									WordPageScore wordPageScore = new WordPageScore(
											tempPair.getSecond().length, maxFreq,
											allDocNum, Integer.parseInt(freqMap
													.get(tempPair.getFirst())),
											docLength, avgDocLength);
									String allType = typeMap.get(tempPair
											.getFirst());
									String[] types = allType.split(",");
									double typesVal = 0.0;
									for (String type : types) {
										if (type.equals(TITLE))
											typesVal += TITLE_VAL;
										else if (type.equals(META))
											typesVal += META_VAL;
										else if (type.equals(ANCHOR))
											typesVal += ANCHOR_VAL;
										else if (type.equals(TEXT))
											typesVal += TEXT_VAL;
									}
									typesVal /= types.length;
									res += wordPageScore.compute() * typesVal;
								}
								//TODO page rank
								PageScore pageScore = SearchEngineServlet.myDB.pageScoreTable.get(key.toString());
								if(pageScore == null) System.out.println("wrong");
								else{
									res *= Double.parseDouble(pageScore.score);
								}
								finalList.add(new Pair<String, Double>(link, res));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}
			}
			catch(Exception e) {
				
			}
		}
	}

	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		Collections.sort(finalList, new Comparator<Pair<String, Double>>() {
			@Override
			public int compare(final Pair<String, Double> o1,
					final Pair<String, Double> o2) {
				if (o1.getSecond() < o2.getSecond())
					return 1;
				else if (o1.getSecond() > o2.getSecond())
					return -1;
				else
					return 0;
			}
		});
		SearchEngineServlet.setFinalList(finalList);
		context.getConfiguration().clear();
	}
}
