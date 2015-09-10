package edu.upenn.cis.cis455.collectLinks;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class collectLinksReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		StringBuffer pageRank = new StringBuffer("1.0\t"); // initial page rank
															// is 1
		StringBuffer stringBuffer = new StringBuffer();
		for(int i = 0; i < 32; i++) {
			stringBuffer.append("*");
		}
		boolean begin = true;
		for (Text value : values) {
			if(!Text.decode(value.copyBytes()).equals(stringBuffer.toString())){ //TODO: special sign
				if (!begin)
					pageRank.append("&"); // reducer aggregate the initial page rank
											// with all pages it links to
				pageRank.append(Text.decode(value.copyBytes()));
				begin = false;
			}
		}
		context.write(key, new Text(pageRank.toString().trim()));

	}
}
