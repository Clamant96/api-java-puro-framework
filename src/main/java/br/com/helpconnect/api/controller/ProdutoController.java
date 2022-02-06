package br.com.helpconnect.api.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Produto;
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

@Path("/produtos")
public class ProdutoController {
	
	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	@GET
	@Path("/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
	public static List<Produto> getAllProdutos(@PathParam("token") String token) {
		
		List<Produto> listaProdutos = new ArrayList<Produto>();
		
		try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM produto");
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				Produto produto = new Produto();
				
				produto.setId(resultSet.getInt("id"));
				produto.setTitulo(resultSet.getString("titulo"));
				produto.setDescricao(resultSet.getString("descricao"));
				
				listaProdutos.add(produto);
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
		
		}
		
		return listaProdutos;
	}
	
	/* GET BY ID - TRAZ UM DADO DE ACORDO COM O ID PASSADO */
	@GET
    @Path("/{id}/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto getByProdutosId(@PathParam("id") int id, @PathParam("token") String token) {
		
		Produto produto = null;
		
		try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM produto WHERE id = ?");
			prepare.setInt(1, id);
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				produto = new Produto();
				
				produto.setId(resultSet.getInt("id"));
				produto.setTitulo(resultSet.getString("titulo"));
				produto.setDescricao(resultSet.getString("descricao"));
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return produto;
	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	@POST
	@Path("/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response postProdutos(Produto produto, @PathParam("token") String token) {
		
		try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
			if(ProdutoService.verificaSeExisteProdutoNoBanco(produto) == null) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("INSERT INTO produto (titulo, descricao) VALUES (?, ?)");
			prepare.setString(1, produto.getTitulo());
			prepare.setString(2, produto.getDescricao());
			
			prepare.executeUpdate();
			
			return Response.status(Status.CREATED).build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	@PUT
	@Path("/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response putProdutos(Produto produto, @PathParam("token") String token) {
		
		try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
			if(produto.getId() == 0) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("UPDATE produto SET titulo = ?, descricao = ? WHERE id = ?");
			prepare.setString(1, produto.getTitulo());
			prepare.setString(2, produto.getDescricao());
			
			prepare.setInt(3, produto.getId());
			
			prepare.executeUpdate();
			
			return Response.status(Status.ACCEPTED).build();
		
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* DELETE - DELETA UM DETERMINADO DADO DE ACORDO COMO O ID INFORMADO */
	@DELETE
    @Path("/{id}/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProdutos(@PathParam("id") int id, @PathParam("token") String token) {
        
        try {
        	
        	if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
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
