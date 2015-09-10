package edu.upenn.cis455.searchengine.handler;

import java.math.BigInteger;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

public class SearchHandler {

	private static String[] indexerAddresses;
	private String[] searchWordIDs;

	// hash key range shared by each indexer
	private static BigInteger share = null;
	private FileProcessor fileProcessor = new FileProcessor();
	private String root;
	private String input;
	// instance of FileProcessor shared by all working threads
	public SearchHandler(){}
	public SearchHandler(String root, String input) {
		this.root = root;
		this.input = input;
		fileProcessor.setInputDir(this.root + this.input); 
	}
	public static void setAddresses(String[] add){
		indexerAddresses = add;
	}
	/**
	 * Search the given words by sending request to indexers, 
	 * collecting common documents and rank them via PageRank
	 * and TF/IDF
	 */
	public void search(Set<String> searchWords) {
		setSearchWords(searchWords);
		// get common documents containing all key words
		sendSearchWordRequests();		
	}

	/**
	 * Given a list of words to be searched, hash every word 
	 * to get the id and store it
	 * @param searchWords
	 */
	public void setSearchWords(Set<String> searchWords) {
		String[] ids = new String[searchWords.size()];
		// hash every word into id and store it
		int i = 0;
		for (String key: searchWords) {
			System.out.println(key + "****");
			String id = DigestUtils.md5Hex(key.getBytes());
			ids[i] = id;
			i++;
		}
		this.searchWordIDs = ids;
	}
	
	/**
	 * Send word id(s) to be searched via http request to
	 * corresponding indexers and write response to the 
	 * file to be processed by MapReduce later
	 */
	public void sendSearchWordRequests() {
		// for each wordID to be searched, create a new client thread
		// to send a /searchword request to corresponding indexer
		for (String wordID : searchWordIDs) {
			// get destination indexer's ip address
			String ip = getAddressToSend(wordID);
			// start a new client and send the request
			//ip = "http://" + ip + ":8080";  //TODO ip address
			MyHttpClient client = new MyHttpClient(ip, wordID, fileProcessor, true);
			client.start();
		}
		fileProcessor.close();
	}

	/**
	 * Calculate the address of indexer to send request based on hash value of
	 * given wordID
	 * 
	 * @param wordID
	 * @return
	 */
	public String getAddressToSend(String wordID) {
		// calculate the share size for each indexer if not yet
		if (share == null) {
			int numIndexer = indexerAddresses.length;
			char[] maxString = new char[32];
			for (int i = 0; i < 32; i++) {
				maxString[i] = 'f';
			}
			BigInteger max = new BigInteger(new String(maxString), 16);
			share = max.divide(new BigInteger("" + numIndexer));
		}

		// get the bucket number of which indexer to send, based
		// on md5 hash value of wordID
		BigInteger hash = new BigInteger(wordID, 16);
		BigInteger bucket = hash.divide(share);
		int bucketNum = bucket.intValue();

		// get the address of indexer to be sent
		String ip = indexerAddresses[bucketNum];

		return ip;
	}

}
