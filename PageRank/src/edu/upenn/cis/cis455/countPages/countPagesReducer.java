package edu.upenn.cis.cis455.countPages;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Progressable;

public class countPagesReducer extends Reducer<Text, Text, Text, Text> {
	static long totalNum = 0L;

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		totalNum += 1;
		boolean isDangling = true;
		String links = null;
		for (Text value : values) {
			String content = Text.decode(value.copyBytes());

			if (content.equals("@")) {
				isDangling = false;
				continue;
			}

			if (content.startsWith("*")) {
				links = content.substring(1);
				continue;
			}
		}
		if (isDangling)
			context.write(new Text(key.copyBytes()), new Text("1.0"));
		else
			context.write(new Text(key.copyBytes()), new Text("1.0\t" + links));
	}

	protected void cleanup(Context context) throws IOException,
			InterruptedException {
		Configuration configuration = new Configuration();
		FileSystem hdfs;
		// try {
		hdfs = FileSystem.get(configuration);
		Path file = new Path("totalNum");
		if (hdfs.exists(file)) {
			hdfs.delete(file, true);
		}
		OutputStream os = hdfs.create(file, new Progressable() {
			public void progress() {
			}
		});
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os,
				"UTF-8"));
		br.write(totalNum + "");
		br.close();
		hdfs.close();
		// } catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		totalNum = 0;
	}
}
