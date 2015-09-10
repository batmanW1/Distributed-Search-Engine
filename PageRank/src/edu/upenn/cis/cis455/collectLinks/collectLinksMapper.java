package edu.upenn.cis.cis455.collectLinks;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class collectLinksMapper extends Mapper<Text, Text, Text, Text> {
	//static int i = 0;
	public void map(Text key, Text value, Context context) throws IOException,
			InterruptedException {
		context.write(key, value);  // direct write key and value to reducer
	}
}
