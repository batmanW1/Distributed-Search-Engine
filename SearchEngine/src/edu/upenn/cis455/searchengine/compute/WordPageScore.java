package edu.upenn.cis455.searchengine.compute;

public class WordPageScore {
	int wordFreq;
	int maxFreq;
	int allDocNum;
	int docFreq;
	int docLength;
	int avgDocLength;
	private static final double k1 = 2.0;
	private static final double b = 0.75;
	public WordPageScore(int wordFreq, int maxFreq, int allDocNum, int docFreq, int docLength, int avgDocLength){
		this.wordFreq = wordFreq;
		this.maxFreq = maxFreq;
		this.allDocNum = allDocNum;
		this.docFreq = docFreq;
		this.docLength = docLength;
		this.avgDocLength = avgDocLength;
	}
	public double compute(){
		double w = Math.log((allDocNum - docFreq + 0.5) / (docFreq + 0.5) + 1);
		double k = k1 * (1 - b + b * (docLength + 0.0) / avgDocLength);
		double r = wordFreq * (k1 + 1) / (wordFreq + k);
		return w * r;
	}
}
