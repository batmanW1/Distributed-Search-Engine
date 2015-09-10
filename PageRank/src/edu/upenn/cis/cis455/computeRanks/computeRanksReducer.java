package edu.upenn.cis.cis455.computeRanks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class computeRanksReducer extends Reducer<Text, Text, Text, Text> {
	Path path = new Path("totalNum");
	int totalNum = 0;
	float totalDanglings = 0f;

	protected void setup(Context context) throws IOException,
			InterruptedException {
		FileSystem fs = FileSystem.get(new Configuration());
		BufferedReader br = new BufferedReader(new InputStreamReader(
				fs.open(path)));
		String lineString = br.readLine();
		totalNum = Integer.parseInt(lineString);
		br.close();
		path = new Path("totalDanglings");
		br = new BufferedReader(new InputStreamReader(fs.open(path)));
		lineString = br.readLine();
		totalDanglings = Float.parseFloat(lineString);
		fs.close();
	}

	public void reduce(Text page, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		float cum = 0.0f;
		String links = "";
		for (Text value : values) {
			String content = Text.decode(value.copyBytes());
			if (content.startsWith("*")) {
				links = "\t" + content.substring(1);
				continue;
			}
			String[] parts = content.split("\\t");
			if (parts.length == 3) {
				float pageRank = Float.valueOf(parts[1]);
				int countOutLinks = Integer.valueOf(parts[2]);

				cum += (pageRank / countOutLinks);
			}
		}
		float newRank = 0.85f * (cum + totalDanglings / totalNum) + 0.15f;
		context.write(page, new Text(newRank + links));
	}
}
