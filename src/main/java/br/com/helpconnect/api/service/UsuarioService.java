package br.com.helpconnect.api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Produto;
import br.com.helpconnect.api.model.Usuario;

public class UsuarioService {
	
	public static Usuario autorizaAcessoEndpoint(String token) {
		
		Usuario usuario = new Usuario();
		
		try {
			
			String tokenDecode = new String(Base64.getDecoder().decode(token.getBytes()));
			
			usuario.setUsername(tokenDecode.split(":")[0]);
			usuario.setSenha(tokenDecode.split(":")[1]);
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM usuario WHERE username = ? AND senha = ?");
			prepare.setString(1, usuario.getUsername());
			prepare.setString(2, usuario.getSenha());
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				usuario.setId(resultSet.getInt("id"));
				usuario.setUsername(resultSet.getString("username"));
				usuario.setSenha(resultSet.getString("senha"));
				
			}
			
			/* CASO NAO TENHA SIDO LOCALIZADO O LOGIN NA BASE DE DADOS, RETORNA UM ERRO AO USUARIO */
			if(usuario.getId() == 0) {
				return null;
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return usuario;
	}
	
	public static String verificaSeExisteUsuarioNoBanco(Usuario usuario) {
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM usuario WHERE username = ? AND senha = ?");
			prepare.setString(1, usuario.getUsername());
			prepare.setString(2, usuario.getSenha());
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				usuario.setId(resultSet.getInt("id"));
				usuario.setUsername(resultSet.getString("username"));
				usuario.setSenha(resultSet.getString("senha"));
				
			}
			
			/* CASO NAO TENHA SIDO LOCALIZADO O LOGIN NA BASE DE DADOS, RETORNA UM ERRO AO USUARIO */
			if(usuario.getId() != 0) {
				return null;
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return "Nao existe esse usuario no banco de dados";
	}
	
	public static List<Produto> carregaListaDeProdutoPorIdDeUsuario(Connection connection, int idUsuario) throws SQLException {
		
		List<Produto> listaProdutos = new ArrayList<Produto>();
		
		/* RETORNA UM OBJETO PRODUTO */
		PreparedStatement prepareTableAssociativa = connection.prepareStatement("SELECT p.id, p.titulo, p.descricao, p.estoque FROM usuario AS u INNER JOIN usuario_produto AS up INNER JOIN produto AS p ON u.id = up.id_usuario AND up.id_produto = p.id WHERE u.id = ?");
		prepareTableAssociativa.setInt(1, idUsuario);
		
		ResultSet resultSetTableAssociativa = prepareTableAssociativa.executeQuery();
		
		while(resultSetTableAssociativa.next()) {
			
			Produto produto = new Produto();
			
			produto.setId(resultSetTableAssociativa.getInt("id"));
			produto.setTitulo(resultSetTableAssociativa.getString("titulo"));
			produto.setDescricao(resultSetTableAssociativa.getString("descricao"));
			produto.setEstoque(resultSetTableAssociativa.getString("estoque"));
			produto.setImgs(ProdutoService.carregaListaDeImagensProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE IMAGENS DO PRODUTO
			
			listaProdutos.add(produto);
			
		}
		
		return listaProdutos;
	}
	
}
