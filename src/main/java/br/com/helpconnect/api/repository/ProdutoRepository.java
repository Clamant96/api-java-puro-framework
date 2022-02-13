package br.com.helpconnect.api.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Produto;
import br.com.helpconnect.api.service.ProdutoService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class ProdutoRepository {
	
	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	public static List<Produto> findAllProdutos() {
		
		List<Produto> listaProdutos = new ArrayList<Produto>();
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM produto");
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				Produto produto = new Produto();
				
				produto.setId(resultSet.getInt("id"));
				produto.setTitulo(resultSet.getString("titulo"));
				produto.setDescricao(resultSet.getString("descricao"));
				produto.setEstoque(resultSet.getString("estoque"));
				produto.setUsuarios(ProdutoService.carregaListaDeUsuariosQueTemProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE USUARIO QUE TEM O PRODUTO
				produto.setImgs(ProdutoService.carregaListaDeImagensProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE IMAGENS DO PRODUTO
				
				listaProdutos.add(produto);
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
		
		}
		
		return listaProdutos;
	}
	
	/* GET BY ID - TRAZ UM DADO DE ACORDO COM O ID PASSADO */
	public static Produto findByProdutosId(int id) {
		
		Produto produto = null;
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM produto WHERE id = ?");
			prepare.setInt(1, id);
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				produto = new Produto();
				
				produto.setId(resultSet.getInt("id"));
				produto.setTitulo(resultSet.getString("titulo"));
				produto.setDescricao(resultSet.getString("descricao"));
				produto.setEstoque(resultSet.getString("estoque"));
				produto.setUsuarios(ProdutoService.carregaListaDeUsuariosQueTemProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE USUARIO QUE TEM O PRODUTO
				produto.setImgs(ProdutoService.carregaListaDeImagensProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE IMAGENS DO PRODUTO
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return produto;
	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	public static Response postProdutos(Produto produto) {
		
		try {
			
			if(ProdutoService.verificaSeExisteProdutoNoBanco(produto) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("INSERT INTO produto (titulo, descricao, estoque) VALUES (?, ?, ?)");
			prepare.setString(1, produto.getTitulo());
			prepare.setString(2, produto.getDescricao());
			prepare.setString(3, produto.getEstoque());
			
			prepare.executeUpdate();
			
			return Response.status(Status.CREATED).build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	public static Response putProdutos(Produto produto) {
		
		try {
			
			if(produto.getId() == 0) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("UPDATE produto SET titulo = ?, descricao = ?, estoque = ? WHERE id = ?");
			prepare.setString(1, produto.getTitulo());
			prepare.setString(2, produto.getDescricao());
			prepare.setString(3, produto.getEstoque());
			
			prepare.setInt(4, produto.getId());
			
			prepare.executeUpdate();
			
			return Response.status(Status.ACCEPTED).build();
		
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* DELETE - DELETA UM DETERMINADO DADO DE ACORDO COMO O ID INFORMADO */
	public static Response deleteProdutos(int id) {
        
        try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("DELETE FROM produto WHERE id = ?");
			prepare.setInt(1, id);
			
			prepare.executeUpdate();
			
			return Response.ok().build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
        
    }
	
}
