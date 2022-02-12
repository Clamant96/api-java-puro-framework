package br.com.helpconnect.api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Img;
import br.com.helpconnect.api.model.Produto;
import br.com.helpconnect.api.model.Usuario;

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
	
	public static List<Usuario> carregaListaDeUsuariosQueTemProdutoPorId(Connection connection, int idProduto) throws SQLException {
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		/* RETORNA UM OBJETO USUARIO */
		PreparedStatement prepareTableAssociativa = connection.prepareStatement("SELECT u.id, u.username, u.senha FROM usuario AS u INNER JOIN usuario_produto AS up INNER JOIN produto AS p ON u.id = up.id_usuario AND up.id_produto = p.id WHERE p.id = ?");
		prepareTableAssociativa.setInt(1, idProduto);
		
		ResultSet resultSetTableAssociativa = prepareTableAssociativa.executeQuery();
		
		while(resultSetTableAssociativa.next()) {
			
			Usuario usuario = new Usuario();
			
			usuario.setId(resultSetTableAssociativa.getInt("id"));
			usuario.setUsername(resultSetTableAssociativa.getString("username"));
			usuario.setSenha(resultSetTableAssociativa.getString("senha"));
			
			listaUsuarios.add(usuario);
			
		}
		
		return listaUsuarios;
	}
	
	public static List<Img> carregaListaDeImagensProdutoPorId(Connection connection, int idProduto) throws SQLException {
		
		List<Img> listaImgs = new ArrayList<Img>();
		
		/* RETORNA UM OBJETO IMG */
		PreparedStatement prepare = connection.prepareStatement("SELECT * FROM img_produto WHERE id_produto = ?");
		prepare.setInt(1, idProduto);
		
		ResultSet resultSet = prepare.executeQuery();
		
		while(resultSet.next()) {
			
			Img img = new Img();
			
			Produto produto = new Produto();
			
			produto.setId(resultSet.getInt("id_produto"));
			
			img.setId(resultSet.getInt("id"));
			img.setImg_1(resultSet.getString("img_1"));
			img.setImg_2(resultSet.getString("img_2"));
			img.setImg_3(resultSet.getString("img_3"));
			img.setImg_4(resultSet.getString("img_4"));
			img.setImg_5(resultSet.getString("img_5"));
			
			img.setProduto(produto); // RECEBE UM OBJETO DE PRODUTO
			
			listaImgs.add(img);
			
		}
		
		return listaImgs;
	}

}
