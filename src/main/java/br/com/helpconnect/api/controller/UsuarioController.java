package br.com.helpconnect.api.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Usuario;
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

@Path("usuarios")
public class UsuarioController {
	
	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public static List<Usuario> getAllUsuarios() {
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM usuario");
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				Usuario usuario = new Usuario();
				
				usuario.setId(resultSet.getInt("id"));
				usuario.setUsername(resultSet.getString("username"));
				usuario.setSenha(resultSet.getString("senha"));
				
				listaUsuarios.add(usuario);
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
		
		}
		
		return listaUsuarios;
	}
	
	/* GET BY ID - TRAZ UM DADO DE ACORDO COM O ID PASSADO */
	@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getByUsuarioId(@PathParam("id") int id) {
		
		Usuario usuario = null;
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM usuario WHERE id = ?");
			prepare.setInt(1, id);
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				usuario = new Usuario();
				
				usuario.setId(resultSet.getInt("id"));
				usuario.setUsername(resultSet.getString("username"));
				usuario.setSenha(resultSet.getString("senha"));
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return usuario;
	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response postUsuario(Usuario usuario) {
		
		try {
			
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
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	@PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response putUsuario(Usuario usuario) {
		
		try {
			
			if(usuario.getId() == 0) {
				return Response.notModified().build();
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePessoa(@PathParam("id") int id) {
        
        try {
			
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
