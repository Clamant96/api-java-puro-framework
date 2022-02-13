package br.com.helpconnect.api.controller;

import java.io.IOException;
import java.util.List;

import br.com.helpconnect.api.anotations.Authorize;
import br.com.helpconnect.api.model.Produto;
import br.com.helpconnect.api.repository.ProdutoRepository;
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

@Path("/produtos")
public class ProdutoController {
	
	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	@Authorize
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public static List<Produto> getAllProdutos() throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return ProdutoRepository.findAllProdutos();
	}
	
	/* GET BY ID - TRAZ UM DADO DE ACORDO COM O ID PASSADO */
	@Authorize
	@GET
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto getByProdutosId(@PathParam("id") int id) throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return ProdutoRepository.findByProdutosId(id);
	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	@Authorize
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response postProdutos(Produto produto) throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return ProdutoRepository.postProdutos(produto);	
	}
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	@Authorize
	@PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response putProdutos(Produto produto) throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return ProdutoRepository.putProdutos(produto);
	}
	
	/* DELETE - DELETA UM DETERMINADO DADO DE ACORDO COMO O ID INFORMADO */
	@Authorize
	@DELETE
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProdutos(@PathParam("id") int id) throws IOException {
		
		if(AuthorizeFilter.validaToken() == null) {
			return null;
		}
		
		return ProdutoRepository.deleteProdutos(id); 
    }
	
}
