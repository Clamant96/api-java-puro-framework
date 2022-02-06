package br.com.helpconnect.api.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Pessoa;
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

@Path("pessoas")
public class PessoaController {
	
	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public static List<Pessoa> getAllPessoas() {
		
		List<Pessoa> listaUsuarios = new ArrayList<Pessoa>();
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM pessoa");
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				Pessoa pessoa = new Pessoa();
				
				pessoa.setId(resultSet.getInt("id"));
				pessoa.setNome(resultSet.getString("nome"));
				pessoa.setIdade(resultSet.getInt("idade"));
				pessoa.setPassword(resultSet.getString("password"));
				pessoa.setEmail(resultSet.getString("email"));
				pessoa.setSexo(resultSet.getString("sexo"));
				pessoa.setPais(resultSet.getString("pais"));
				
				listaUsuarios.add(pessoa);
				
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
    public Pessoa getByPessoaId(@PathParam("id") int id) {
		
		Pessoa pessoa = null;
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM pessoa WHERE id = ?");
			prepare.setInt(1, id);
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				pessoa = new Pessoa();
				
				pessoa.setId(resultSet.getInt("id"));
				pessoa.setNome(resultSet.getString("nome"));
				pessoa.setIdade(resultSet.getInt("idade"));
				pessoa.setEmail(resultSet.getString("email"));
				pessoa.setPais(resultSet.getString("pais"));
				pessoa.setPassword(resultSet.getString("password"));
				pessoa.setSexo(resultSet.getString("sexo"));
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return pessoa;
	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response postPessoa(Pessoa pessoa) {
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("INSERT INTO pessoa (nome, idade, password, email, sexo, pais) VALUES (?, ?, ?, ?, ?, ?)");
			prepare.setString(1, pessoa.getNome());
			prepare.setInt(2, pessoa.getIdade());
			prepare.setString(3, pessoa.getPassword());
			prepare.setString(4, pessoa.getEmail());
			prepare.setString(5, pessoa.getSexo());
			prepare.setString(6, pessoa.getPais());
			
			prepare.executeUpdate();
			
			return Response.status(Status.CREATED).build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	@PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response putPessoa(Pessoa pessoa) {
		
		try {
			
			if(pessoa.getId() == 0) {
				return Response.notModified().build();
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("UPDATE pessoa SET nome = ?, idade = ?, password = ?, email = ?, sexo = ?, pais = ? WHERE id = ?");
			prepare.setString(1, pessoa.getNome());
			prepare.setInt(2, pessoa.getIdade());
			prepare.setString(3, pessoa.getPassword());
			prepare.setString(4, pessoa.getEmail());
			prepare.setString(5, pessoa.getSexo());
			prepare.setString(6, pessoa.getPais());
			
			prepare.setInt(7, pessoa.getId());
			
			prepare.executeUpdate();
			
			return Response.status(Status.ACCEPTED).build();
		
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
			PreparedStatement prepare = connection.prepareStatement("DELETE FROM pessoa WHERE id = ?");
			prepare.setInt(1, id);
			
			prepare.executeUpdate();
			
			return Response.ok().build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
        
    }
	
}
