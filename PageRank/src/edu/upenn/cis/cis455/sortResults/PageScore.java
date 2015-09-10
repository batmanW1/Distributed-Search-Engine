package edu.upenn.cis.cis455.sortResults;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class PageScore {
	@PrimaryKey
	public String id;
	
	public String score;
	
	public PageScore(String id, String score){
		this.id = id;
		this.score = score;
	}
	
	private PageScore(){
		
	}
}
