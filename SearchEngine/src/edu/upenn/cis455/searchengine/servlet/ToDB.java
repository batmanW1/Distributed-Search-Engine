package edu.upenn.cis455.searchengine.servlet;

import java.io.File;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

import edu.upenn.cis.cis455.sortResults.PageScore;


public class ToDB {
	/** The my environment. */
	private Environment myEnv;
	/** The store. */
	private EntityStore store;

	/** The web page table. */
	public PrimaryIndex<String, PageScore> pageScoreTable;
	
	public ToDB(String location){
		if (!location.endsWith("/"))
			location += "/";
		File file = new File(location);
		if (!file.exists())
			file.mkdir();
		if (!file.isDirectory()){
			throw new IllegalArgumentException();
		}
		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		environmentConfig.setAllowCreate(true);
		myEnv = new Environment(file, environmentConfig);
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		store = new EntityStore(myEnv, "myStore", storeConfig);
		pageScoreTable = store.getPrimaryIndex(String.class, PageScore.class);
	}
	public synchronized void sync(){
		store.sync();
		myEnv.sync();
	}
	public void close() {
		store.close();
		myEnv.close();
	}
}
