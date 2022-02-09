package br.com.helpconnect.api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Produto;

public class ProdutoService {
	
	public static String verificaSeExisteProdutoNoBanco(Produto produto) {
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM produto WHERE titulo = ? AND descricao = ?");
			prepare.setString(1, produto.getTitulo());
			prepare.setString(2, produto.getDescricao());
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				produto.setId(resultSet.getInt("id"));
				produto.setTitulo(resultSet.getString("titulo"));
				produto.setDescricao(resultSet.getString("descricao"));
				
			}
			
			/* CASO NAO TENHA SIDO LOCALIZADO O LOGIN NA BASE DE DADOS, RETORNA UM ERRO AO USUARIO */
			if(produto.getId() != 0) {
				return null;
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return "Nao existe esse usuario no banco de dados";
	}
	
	public static void ajustaEstoqueAdicionandoProdutoRetornadoAoEstoque(Produto produto, Connection connection, PreparedStatement prepare) throws SQLException {
		prepare = connection.prepareStatement("UPDATE produto SET titulo = ?, descricao = ?, estoque = ? WHERE id = ?");
		prepare.setString(1, produto.getTitulo());
		prepare.setString(2, produto.getDescricao());
		prepare.setString(3,  String.valueOf(Integer.parseInt(produto.getEstoque()) + 1));
		
		prepare.setInt(4, produto.getId());
		
		prepare.executeUpdate();
		
	}
	
	public static void ajustaEstoqueRetiraProdutoDoEstoque(Produto produto, Connection connection, PreparedStatement prepare) throws SQLException {
		prepare = connection.prepareStatement("UPDATE produto SET titulo = ?, descricao = ?, estoque = ? WHERE id = ?");
		prepare.setString(1, produto.getTitulo());
		prepare.setString(2, produto.getDescricao());
		prepare.setString(3,  String.valueOf(Integer.parseInt(produto.getEstoque()) - 1));
		
		prepare.setInt(4, produto.getId());
		
		prepare.executeUpdate();
		
	}

}
