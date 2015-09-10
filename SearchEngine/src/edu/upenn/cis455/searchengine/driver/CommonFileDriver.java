package edu.upenn.cis455.searchengine.driver;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

import edu.upenn.cis455.searchengine.mapreduce.CommonFileMapper;
import edu.upenn.cis455.searchengine.mapreduce.CommonFileReducer;

public class CommonFileDriver extends Configured implements Tool {

	private int numWords;
	private String input;
	private String output;
	private String root;

	public CommonFileDriver(int numWords, String root, String input,
			String output) {
		this.numWords = numWords;
		this.input = input;
		this.root = root;
		this.output = output;
	}

	@Override
	public int run(String[] arg0) throws Exception {
		boolean complete = findCommon(root + input, root + output);
		if (!complete)
			return 1;
		return 0;
	}

	public boolean findCommon(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		configuration.setBoolean("fs.hdfs.impl.disable.cache", true);
		configuration.set("wordNum", numWords + "");
		Job findCommon = Job.getInstance(configuration, "findCommon");
		findCommon.setJarByClass(CommonFileDriver.class);
		findCommon.setMapperClass(CommonFileMapper.class);
		findCommon.setReducerClass(CommonFileReducer.class);

		findCommon.setInputFormatClass(KeyValueTextInputFormat.class);
		findCommon.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(findCommon, new Path(inputPath));
		FileOutputFormat.setOutputPath(findCommon, new Path(outputPath));

		findCommon.setMapOutputKeyClass(Text.class);
		findCommon.setMapOutputValueClass(Text.class);
		findCommon.setOutputKeyClass(Text.class);
		findCommon.setOutputValueClass(Text.class);

		return findCommon.waitForCompletion(true);
	}

}
