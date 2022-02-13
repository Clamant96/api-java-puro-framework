package br.com.helpconnect.api.controller;

import java.io.IOException;
import java.util.List;

import br.com.helpconnect.api.anotations.Authorize;
import br.com.helpconnect.api.model.Usuario;
import br.com.helpconnect.api.model.UsuarioLogin;
import br.com.helpconnect.api.repository.UsuarioRepository;
import br.com.helpconnect.api.security.AuthorizeFilter;
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

@Path("/usuarios")
public class UsuarioController {

	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	@Authorize
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public static List<Usuario> getAllUsuarios() throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return UsuarioRepository.findAllUsuarios();
	}
	
	/* GET BY ID - TRAZ UM DADO DE ACORDO COM O ID PASSADO */
	@Authorize
	@GET
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getByUsuarioId(@PathParam("id") int id) throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return UsuarioRepository.findByUsuarioId(id);
	}
	
	/* GET COMPRAR - ADICIONA UM PRODUTO AO USUARIO */
	@Authorize
	@GET
	@Path("/comprar/id_produto/{idProduto}/id_usuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public static Response comprarProduto(@PathParam("idUsuario") int idUsuario, @PathParam("idProduto") int idProduto) throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return UsuarioRepository.comprarProduto(idUsuario, idProduto);
	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	@POST
	@Path("/cadastro")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response postUsuario(Usuario usuario) {
		
		return UsuarioRepository.cadastroUsuario(usuario);
	}
	
	/* POST - LOGA UM NOVO USUARIO NO SISTEMA, GERANDO UM TOKEN DE AUTENTICACAO */
	@POST
	@Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static UsuarioLogin postLoginUsuario(UsuarioLogin usuarioLogin) {
		
		return UsuarioRepository.loginUsuario(usuarioLogin);
	}
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	@Authorize
	@PUT
	@Path("/atualizar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response putUsuario(Usuario usuario) throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return UsuarioRepository.putUsuario(usuario);
	}
	
	/* DELETE - DELETA UM DETERMINADO DADO DE ACORDO COMO O ID INFORMADO */
	@Authorize
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response deletePessoa(@PathParam("id") int id) throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return UsuarioRepository.deleteUsuario(id);
    }
	
}
