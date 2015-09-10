package edu.upenn.cis455.searchengine.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpClient {
	
	private String ip;  // destination to send
	private String id; // variable to store wordID or docID to be sent
	private FileProcessor fileProcessor;
	private boolean isSearchWordMode;
	
	public MyHttpClient(String ip, String id, FileProcessor fileProcessor, boolean isSearchWordMode) {
		this.ip = ip;
		this.id = id;
		this.isSearchWordMode = isSearchWordMode;
		this.fileProcessor = fileProcessor;
	}
	
	/**
	 * Send /searchword request to the destination server, 
	 * return the response which contains a list of documents
	 * @throws IOException
	 */
	private void sendSearchWordRequest() throws IOException {

		// create a connection with indexer server and send a GET request
		StringBuffer temp = new StringBuffer();
		while(true){
			String query = "word=" + id;
			URL url = new URL(ip + "/Indexer/searchword" + "?" + query);
			try{
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				System.out.println(id);
				String line = "";
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(connection.getInputStream())); 
				while((line = in.readLine()) != null) {
					temp.append(line);
				}
				int status = connection.getResponseCode();
				System.out.println(status);
				String res = temp.toString();
				System.out.println(res);
				if(res.contains("finish")) break;
				fileProcessor.write(id, temp.toString());
				in.close();
				temp = new StringBuffer();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	public void start() {
		if (isSearchWordMode) {
			try {
				sendSearchWordRequest();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			
		}
	}

}
