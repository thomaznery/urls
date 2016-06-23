package br.com.urls.modelos;

import com.google.gson.Gson;

public class Usuario {
	private int id;
	private String nome;
	
	public Usuario(int id, String nome){
		this.setId(id);
		this.setNome(nome);
	}
	public Usuario(String nome){
		this.setNome(nome);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String toJson(){
		return new Gson().toJson(this);
	}
	
}
