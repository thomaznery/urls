package br.com.urls.modelos;

import java.util.List;

import com.google.gson.Gson;

public class Stats {
	private int hits;
	private int urlCount;
	private List<URL> topUrls;
	
	public Stats() {
		
	}
	
	public Stats(int hits,int urlCount, List<URL> topUrls){
		this.setHits(hits);
		this.setUrlCount(urlCount);
		this.setTopUrls(topUrls);
	}
	
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getUrlCount() {
		return urlCount;
	}
	public void setUrlCount(int urlCount) {
		this.urlCount = urlCount;
	}
	public List<URL> getTopUrls() {
		return topUrls;
	}
	public void setTopUrls(List<URL> topUrls) {
		this.topUrls = topUrls;
	}
	
	public String toJson(){
		return new Gson().toJson(this);
	}

	
}
