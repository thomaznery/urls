package br.com.urls.modelos;

import com.google.gson.Gson;

public class URL {
	private int id;
	private int hits;
	private String url;
	private String shortUrl;
	private int idUser;
	
	public URL(int id,int hits,String url,String shortUrl){
		this.setId(id);
		this.setHits(hits);
		this.setUrl(url);
		this.setShortUrl(shortUrl);
	}
	public URL(int id,int hits,String url,String longUrl,int idUser){
		this.setId(id);
		this.setHits(hits);
		this.setUrl(url);
		this.setUrl(longUrl);
		this.setIdUser(idUser);
	}
	
	
	public URL(int hit, String longUrl, String url){
		this.setHits(hit);
		this.setUrl(longUrl);
		this.setShortUrl(url);
	}

	public URL(String url){
		this.setUrl(url);
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String longUrl) {
		this.url = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
	public void setShortUrl(){
		//shortUrl ,  id na base 36
		this.shortUrl = "http://localhost:8080/urls/"+(Integer.toString(this.getId(), 36));
	}
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String toJson(){
		return new Gson().toJson(this);
	}
	
}
