package edu.upenn.cis.cis455.countPages;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class countPagesMapper extends Mapper<Text, Text, Text, Text> {
	public void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {
		String fromPage = Text.decode(key.copyBytes());
		String val = Text.decode(value.copyBytes());
		int fromPageRankIndex = val.indexOf("\t");
		// determine this page as non-dangling page(if crawled and have outer
		// links)
		if (fromPageRankIndex != -1) {
			context.write(new Text(fromPage), new Text("@"));
			// all the pages this page links to
			String toLinks = Text.decode(value.copyBytes(), fromPageRankIndex + 1,
					value.getLength() - (fromPageRankIndex + 1));
			String[] toPages = toLinks.split("\\&");

			for (String toPage : toPages) {
				context.write(new Text(toPage), new Text("1"));
			}

			// Put the original links of the page for the reduce output
			context.write(new Text(fromPage), new Text("*" + toLinks));
		}
		else{
			context.write(new Text(fromPage), new Text("1"));
		}
	}
}
