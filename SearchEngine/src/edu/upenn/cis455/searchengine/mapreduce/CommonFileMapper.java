package edu.upenn.cis455.searchengine.mapreduce;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CommonFileMapper extends Mapper<Text, Text, Text, Text> {
	public static HashMap<String, String> freq = null;
	protected void setup(Context context) throws IOException,
			InterruptedException {
		freq = new HashMap<String, String>();
	}

	public void map(Text key, Text value, Context context) throws IOException,
			InterruptedException {

		// get the wordID and a list of docID from the value first
		String wordID = key.toString();
		String[] docs = value.toString().split(",");
		// for each (wordID, docID) pair, emit it as (docID, wordID)
		freq.put(wordID, docs.length + "");
		for (String doc : docs) {
			context.write(new Text(doc), new Text(wordID));
			// System.out.println(doc + "+" + wordID);
		}
	}

}
