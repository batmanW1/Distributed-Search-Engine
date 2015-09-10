package edu.upenn.cis.cis455.sortResults;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class sortResultsMapper extends
		Mapper<Text, Text, FloatWritable, Text> {
	// TODO db location
	String dbPos = "/home/muruoliu/db/";
	ToDB db = null;

	protected void setup(Context context) throws IOException,
			InterruptedException {
		db = new ToDB(dbPos);
	}

	public void map(Text key, Text value, Context context) throws IOException,
			InterruptedException {
		String id = Text.decode(key.copyBytes());
		String temp = Text.decode(value.copyBytes());
		String[] args = temp.split("\\t");
		PageScore pageScore = new PageScore(id, args[0]);
		db.pageScoreTable.put(pageScore);
		//db.sync();
	}

	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		db.close();
	}
}
