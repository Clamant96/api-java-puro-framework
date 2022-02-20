package br.com.helpconnect.api.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
	private int id;
	
    private String username;
    
    private String senha;
    
    private List<Produto> produtos = new ArrayList<>();
    
    private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
}
