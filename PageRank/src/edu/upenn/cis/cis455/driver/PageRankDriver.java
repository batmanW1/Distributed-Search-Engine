package edu.upenn.cis.cis455.driver;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import edu.upenn.cis.cis455.collectLinks.collectLinksMapper;
import edu.upenn.cis.cis455.collectLinks.collectLinksReducer;
import edu.upenn.cis.cis455.computeRanks.computeRanksMapper;
import edu.upenn.cis.cis455.computeRanks.computeRanksReducer;
import edu.upenn.cis.cis455.countPages.countPagesMapper;
import edu.upenn.cis.cis455.countPages.countPagesReducer;
import edu.upenn.cis.cis455.sortResults.sortResultsMapper;

public class PageRankDriver extends Configured implements Tool {
	// TODO root hdfs
	String root = "file:///home/muruoliu/";
	public static void main(String[] args) throws Exception {
		System.exit(ToolRunner.run(new Configuration(), new PageRankDriver(),
				args));
	}
	@Override
	public int run(String[] arg0) throws Exception {
		NumberFormat numberFormat = new DecimalFormat("00"); // format for
																// output files
//		boolean complete = collectLinks("in", "ranks/temp"); // first collect
//																// links from
//																// input file
//		if (!complete)
//			return 1;
//		complete = countPages("ranks/temp", "ranks/iter00");
//		if(!complete)
//			return 1;
//		String outPath = null;
//		for (int pass = 0; pass < 10; pass++) { // run for 5 times 
//			String inPath = "ranks/iter" + numberFormat.format(pass);
//			outPath = "ranks/iter" + numberFormat.format(pass + 1);
//			complete = computeRanks(inPath, outPath);
//			if (!complete)
//				return 1;
//			if(pass > 0){ //TODO delete temp file
//				FileUtils.deleteDirectory(new File("/home/muruoliu/ranks/iter" + numberFormat.format(pass - 1)));
//			}
//		}
		//boolean complete = sortResults(outPath, "results");
		boolean complete = sortResults("ranks/iter10", "results");
		if (!complete)
			return 1;
		return 0;
	}

	public boolean collectLinks(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		inputPath = root + inputPath;
		outputPath = root + outputPath;
		Configuration configuration = new Configuration();
		configuration.setBoolean("fs.hdfs.impl.disable.cache", true);
		
		Job collectLinks = Job.getInstance(configuration, "collectLinks");
		collectLinks.setJarByClass(PageRankDriver.class);
		collectLinks.setMapperClass(collectLinksMapper.class);
		collectLinks.setReducerClass(collectLinksReducer.class);

		collectLinks.setInputFormatClass(KeyValueTextInputFormat.class);
		collectLinks.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(collectLinks, new Path(inputPath));
		FileOutputFormat.setOutputPath(collectLinks, new Path(outputPath));

		collectLinks.setMapOutputKeyClass(Text.class);
		collectLinks.setMapOutputValueClass(Text.class);
		collectLinks.setOutputKeyClass(Text.class);
		collectLinks.setOutputValueClass(Text.class);

		return collectLinks.waitForCompletion(true);
	}

	public boolean countPages(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		inputPath = root + inputPath;
		outputPath = root + outputPath;
		Configuration configuration = new Configuration();
		configuration.setBoolean("fs.hdfs.impl.disable.cache", true);
		Job countPages = Job.getInstance(configuration, "countPages");
		countPages.setJarByClass(PageRankDriver.class);
		countPages.setMapperClass(countPagesMapper.class);
		countPages.setReducerClass(countPagesReducer.class);

		countPages.setInputFormatClass(KeyValueTextInputFormat.class);
		countPages.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(countPages, new Path(inputPath));
		FileOutputFormat.setOutputPath(countPages, new Path(outputPath));

		countPages.setMapOutputKeyClass(Text.class);
		countPages.setMapOutputValueClass(Text.class);
		countPages.setOutputKeyClass(Text.class);
		countPages.setOutputValueClass(Text.class);

		return countPages.waitForCompletion(true);
	}

	public boolean computeRanks(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		inputPath = root + inputPath;
		outputPath = root + outputPath;
		Configuration configuration = new Configuration();
		configuration.setBoolean("fs.hdfs.impl.disable.cache", true);
		Job computRanks = Job.getInstance(configuration, "computRanks");
		computRanks.setJarByClass(PageRankDriver.class);
		computRanks.setMapperClass(computeRanksMapper.class);
		computRanks.setReducerClass(computeRanksReducer.class);

		computRanks.setInputFormatClass(KeyValueTextInputFormat.class);
		computRanks.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(computRanks, new Path(inputPath));
		FileOutputFormat.setOutputPath(computRanks, new Path(outputPath));

		computRanks.setMapOutputKeyClass(Text.class);
		computRanks.setMapOutputValueClass(Text.class);
		computRanks.setOutputKeyClass(Text.class);
		computRanks.setOutputValueClass(Text.class);

		return computRanks.waitForCompletion(true);
	}

	public boolean sortResults(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		inputPath = root + inputPath;
		outputPath = root + outputPath;
		Configuration conf = new Configuration();
		conf.setBoolean("fs.hdfs.impl.disable.cache", true);
		Job sortResults = Job.getInstance(conf, "sortResults");
		sortResults.setJarByClass(PageRankDriver.class);
		sortResults.setMapperClass(sortResultsMapper.class);

		sortResults.setInputFormatClass(KeyValueTextInputFormat.class);
		sortResults.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(sortResults, new Path(inputPath));
		FileOutputFormat.setOutputPath(sortResults, new Path(outputPath));

		sortResults.setMapOutputKeyClass(FloatWritable.class);
		sortResults.setMapOutputValueClass(Text.class);
		sortResults.setOutputKeyClass(FloatWritable.class);
		sortResults.setOutputValueClass(Text.class);
		return sortResults.waitForCompletion(true);
	}
}
