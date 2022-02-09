package br.com.helpconnect.api.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Produto;
import br.com.helpconnect.api.model.Usuario;
import br.com.helpconnect.api.model.UsuarioLogin;
import br.com.helpconnect.api.service.ProdutoService;
import br.com.helpconnect.api.service.UsuarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuarios")
public class UsuarioController {
	
	/*@GET
	@Path("/autorizacao/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public static Usuario testeAutorizacao(@PathParam("token") String token) {
		return UsuarioService.autorizaAcessoEndpoint(token);
	}*/
	
	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	@GET
	@Path("/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
	public static List<Usuario> getAllUsuarios(@PathParam("token") String token) {
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM usuario");
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				Usuario usuario = new Usuario();
				
				List<Produto> listaProdutos = new ArrayList<Produto>(); // INSTANCIA UMA LISTA DE PRODUTOS, PARA INSERIR ESSE PRODUTOS NO ARRAY DE PRODUTO DO USUARIO
				
				/* RETORNA UM OBJETO PRODUTO */
				PreparedStatement prepareTableAssociativa = connection.prepareStatement("SELECT p.id, p.titulo, p.descricao, p.estoque FROM usuario AS u INNER JOIN usuario_produto AS up INNER JOIN produto AS p ON u.id = up.id_usuario AND up.id_produto = p.id WHERE u.id = ?");
				prepareTableAssociativa.setInt(1, resultSet.getInt("id"));
				
				ResultSet resultSetTableAssociativa = prepareTableAssociativa.executeQuery();
				
				while(resultSetTableAssociativa.next()) {
					
					Produto produto = new Produto();
					
					produto.setId(resultSetTableAssociativa.getInt("id"));
					produto.setTitulo(resultSetTableAssociativa.getString("titulo"));
					produto.setDescricao(resultSetTableAssociativa.getString("descricao"));
					produto.setEstoque(resultSetTableAssociativa.getString("estoque"));
					
					listaProdutos.add(produto);
					
				}
				
				usuario.setId(resultSet.getInt("id"));
				usuario.setUsername(resultSet.getString("username"));
				usuario.setSenha(resultSet.getString("senha"));
				usuario.setProdutos(listaProdutos); // INSERE A LISTA DE PRODUTOS RECUPERADOS DO BANCO NO ARRAY DO USUARIO
				
				listaUsuarios.add(usuario);
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
		
		}
		
		return listaUsuarios;
	}
	
	/* GET BY ID - TRAZ UM DADO DE ACORDO COM O ID PASSADO */
	@GET
    @Path("/{id}/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getByUsuarioId(@PathParam("id") int id, @PathParam("token") String token) {
		
		Usuario usuario = null;
		
		try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM usuario WHERE id = ?");
			prepare.setInt(1, id);
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				List<Produto> listaProdutos = new ArrayList<Produto>(); // INSTANCIA UMA LISTA DE PRODUTOS, PARA INSERIR ESSE PRODUTOS NO ARRAY DE PRODUTO DO USUARIO
				
				/* RETORNA UM OBJETO PRODUTO */
				PreparedStatement prepareTableAssociativa = connection.prepareStatement("SELECT p.id, p.titulo, p.descricao, p.estoque FROM usuario AS u INNER JOIN usuario_produto AS up INNER JOIN produto AS p ON u.id = up.id_usuario AND up.id_produto = p.id WHERE u.id = ?");
				prepareTableAssociativa.setInt(1, resultSet.getInt("id"));
				
				ResultSet resultSetTableAssociativa = prepareTableAssociativa.executeQuery();
				
				while(resultSetTableAssociativa.next()) {
					
					Produto produto = new Produto();
					
					produto.setId(resultSetTableAssociativa.getInt("id"));
					produto.setTitulo(resultSetTableAssociativa.getString("titulo"));
					produto.setDescricao(resultSetTableAssociativa.getString("descricao"));
					produto.setEstoque(resultSetTableAssociativa.getString("estoque"));
					
					listaProdutos.add(produto);
					
				}
				
				usuario = new Usuario();
				
				usuario.setId(resultSet.getInt("id"));
				usuario.setUsername(resultSet.getString("username"));
				usuario.setSenha(resultSet.getString("senha"));
				usuario.setProdutos(listaProdutos); // INSERE A LISTA DE PRODUTOS RECUPERADOS DO BANCO NO ARRAY DO USUARIO
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return usuario;
	}
	
	/* GET COMPRAR - ADICIONA UM PRODUTO AO USUARIO */
	@GET
    @Path("/comprar/id_produto/{idProduto}/id_usuario/{idUsuario}/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public static Response comprarProduto(@PathParam("idUsuario") int idUsuario, @PathParam("idProduto") int idProduto, @PathParam("token") String token) {
		
		boolean disponibilidade = false;
		
		try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = null;
			
			/* VERIFICA SE EXISTE ITENS DISPONIVEIS NO ESTOQUE DO PRODUTO SELECIONADO */
			prepare = connection.prepareStatement("SELECT * FROM produto WHERE id = ?");
			prepare.setInt(1, idProduto);
			
			ResultSet resultSetQtdItensEstoque = prepare.executeQuery();
			
			Produto produto = null;
			
			while(resultSetQtdItensEstoque.next()) {
				
				produto = new Produto();
				
				produto.setId(resultSetQtdItensEstoque.getInt("id"));
				produto.setTitulo(resultSetQtdItensEstoque.getString("titulo"));
				produto.setDescricao(resultSetQtdItensEstoque.getString("descricao"));
				produto.setEstoque(resultSetQtdItensEstoque.getString("estoque"));
				
				if(Integer.parseInt(produto.getEstoque()) > 0) {
					disponibilidade = true;
				}

				
			}
			
			/* VERIFICA SE O ITEM JA ESTA INCLUSO NA LISTA DO USUARIO CASO ESTEJA O MESMO E REMOVIDO DA LISTA */
			try {
				
				prepare = connection.prepareStatement("SELECT * FROM usuario_produto WHERE id_usuario = ? AND id_produto = ?");
				prepare.setInt(1, idUsuario);
				prepare.setInt(2, idProduto);
				
				ResultSet resultSet = prepare.executeQuery();
				
				int contador = 0;
				
				while(resultSet.next()) {	
					contador++;
					
				}
				
				if(contador > 0) {
					prepare = connection.prepareStatement("DELETE FROM usuario_produto WHERE id_usuario = ? AND id_produto = ?");
					prepare.setInt(1, idUsuario);
					prepare.setInt(2, idProduto);
					
					prepare.executeUpdate();
					
					ProdutoService.ajustaEstoqueAdicionandoProdutoRetornadoAoEstoque(produto, connection, prepare);
					
					return Response.status(Status.OK).build();
				}
				
			}catch(Exception erro) {
				erro.printStackTrace();
				
			}
					
			/* INSERE UM NOVO NA TABELA ASSOCIATIVA, MAS PARA ISSO O DADO NAO PODE EXISTIR NESSA TABELA */
			if(disponibilidade) {
				/* AJUSTA O VALOR DO ESTOQUE RETIRANDO UMA UNIDADE DO ESTOQUE */
				ProdutoService.ajustaEstoqueRetiraProdutoDoEstoque(produto, connection, prepare);
				
				prepare = connection.prepareStatement("INSERT INTO usuario_produto (id_usuario, id_produto) VALUES (?, ?)");
				prepare.setInt(1, idUsuario);
				prepare.setInt(2, idProduto);
				
				prepare.executeUpdate();
				
				return Response.status(Status.CREATED).build();
			}else {
				
				return Response.status(Status.NOT_MODIFIED).build();
			}
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}

	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	@POST
	@Path("/cadastro")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response postUsuario(Usuario usuario) {
		
		try {
			
			if(UsuarioService.verificaSeExisteUsuarioNoBanco(usuario) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("INSERT INTO usuario (username, senha) VALUES (?, ?)");
			prepare.setString(1, usuario.getUsername());
			prepare.setString(2, usuario.getSenha());
			
			prepare.executeUpdate();
			
			return Response.ok().build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* POST - LOGA UM NOVO USUARIO NO SISTEMA, GERANDO UM TOKEN DE AUTENTICACAO */
	@POST
	@Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static UsuarioLogin postLoginUsuario(UsuarioLogin usuarioLogin) {
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM usuario WHERE username = ? AND senha = ?");
			prepare.setString(1, usuarioLogin.getUsername());
			prepare.setString(2, usuarioLogin.getSenha());
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				usuarioLogin.setId(resultSet.getInt("id"));
				usuarioLogin.setUsername(resultSet.getString("username"));
				usuarioLogin.setSenha(resultSet.getString("senha"));
				
				String token = usuarioLogin.getUsername() +":"+ usuarioLogin.getSenha();
				
				usuarioLogin.setToken(new String(Base64.getEncoder().encode(token.getBytes()))); // CRIPTOGRAFA OS DADOS DO LOGIN DE USUARIO E INSERE DE DO TOKEN DO OBJETO DE USUARIO LOGIN
				
			}
			
			/* CASO NAO TENHA SIDO LOCALIZADO O LOGIN NA BASE DE DADOS, RETORNA UM ERRO AO USUARIO */
			if(usuarioLogin.getId() == 0) {
				return null;
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return usuarioLogin;
	}
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	@PUT
	@Path("/atualizar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response putUsuario(Usuario usuario) {
		
		try {
			
			if(usuario.getId() == 0) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("UPDATE usuario SET username = ?, senha = ? WHERE id = ?");
			prepare.setString(1, usuario.getUsername());
			prepare.setString(2, usuario.getSenha());
			
			prepare.setInt(3, usuario.getId());
			
			prepare.executeUpdate();
			
			return Response.ok().build();
		
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* DELETE - DELETA UM DETERMINADO DADO DE ACORDO COMO O ID INFORMADO */
	@DELETE
    @Path("/{id}/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePessoa(@PathParam("id") int id, @PathParam("token") String token) {
        
        try {
        	
        	if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("DELETE FROM usuario WHERE id = ?");
			prepare.setInt(1, id);
			
			prepare.executeUpdate();
			
			return Response.ok().build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
        
    }
	
}
