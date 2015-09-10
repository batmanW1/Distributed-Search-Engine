package edu.upenn.cis455.searchengine.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FileProcessor {

	private String inputDir;
	private static NumberFormat numberFormat = new DecimalFormat("000");
	private static PrintWriter out = null;
	private static int fileNum = 0;
	private static int lineNum = 0;
	private static int lineLimit = 1000;
	public FileProcessor() {}
	
	public FileProcessor(String directory) {
		this.inputDir = directory;
	}
	
	public void setInputDir(String filename) {
		this.inputDir = filename;
	}
	
	/**
	 * Write a
	 * @param wordID
	 * @param docs
	 */
	public synchronized void write(String wordID, String docs) {
		// set up the output file writer
		File file = new File(inputDir + "/in" + numberFormat.format(fileNum));
		if(!file.exists()) {
			System.out.println("not exist");
			try {
				file.createNewFile();
				out = new PrintWriter(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// write the entry into the file
		out.append(wordID + "\t" + docs + "\n");
		out.flush();
		lineNum ++;
		if(lineNum > lineLimit) {
			out.flush();
			out.close();
			fileNum ++;
			lineNum = 0;
		}
	}
	public void close(){
		if(out != null) out.close();
		lineNum = 0;
		fileNum = 0;
	}
}
