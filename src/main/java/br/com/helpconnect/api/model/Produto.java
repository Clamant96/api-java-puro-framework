package br.com.helpconnect.api.model;

import java.util.ArrayList;
import java.util.List;

public class Produto {
	
	private int id;
	
	private String titulo;
	
    private String descricao;
    
    private List<Usuario> usuarios = new ArrayList<Usuario>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
    
}
