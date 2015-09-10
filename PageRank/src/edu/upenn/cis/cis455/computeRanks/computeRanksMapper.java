package edu.upenn.cis.cis455.computeRanks;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.Progressable;

public class computeRanksMapper extends Mapper<Text, Text, Text, Text> {
	static float totalDanglingRanks = 0f;

	public void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {
		String fromPage = Text.decode(key.copyBytes());
		String val = Text.decode(value.copyBytes());
		int fromPageRankIndex = val.indexOf("\t");
		// determine this page as non-dangling page(if crawled and have outer
		// links)
		if (fromPageRankIndex != -1) {
			String[] parts = val.split("\\t");
			String fromPageRank = fromPage + "\t" + parts[0];
			// all the pages this page links to
			String toLinks = parts[1];
			String[] toPages = toLinks.split("\\&");
			int toLinksNum = toPages.length; // number of outer links

			for (String toPage : toPages) {
				Text pageRankTotalLinks = new Text(fromPageRank +  "\t" + toLinksNum);
				context.write(new Text(toPage), pageRankTotalLinks);
			}

			// Put the original links of the page for the reduce output
			context.write(new Text(fromPage), new Text("*" + toLinks));
		} else {
			//totalDanglingRanks += Float.parseFloat(val);
			totalDanglingRanks += 1f;
		}
	}

	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		Configuration configuration = new Configuration();
		FileSystem hdfs;
		// try {
		hdfs = FileSystem.get(configuration);
		Path file = new Path("totalDanglings");
		if (hdfs.exists(file)) {
			hdfs.delete(file, true);
		}
		OutputStream os = hdfs.create(file, new Progressable() {
			public void progress() {
			}
		});
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os,
				"UTF-8"));
		br.write(totalDanglingRanks + "");
		br.close();
		hdfs.close();
		// } catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		totalDanglingRanks = 0f;
	}
}
