package br.com.helpconnect.api.controller;

import java.util.List;

import br.com.helpconnect.api.model.Produto;
import br.com.helpconnect.api.repository.ProdutoRepository;
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

@Path("/produtos")
public class ProdutoController {
	
	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	@GET
	@Path("/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
	public static List<Produto> getAllProdutos(@PathParam("token") String token) {
		
		/*List<Produto> listaProdutos = new ArrayList<Produto>();
		
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
				produto.setEstoque(resultSet.getString("estoque"));
				produto.setUsuarios(ProdutoService.carregaListaDeUsuariosQueTemProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE USUARIO QUE TEM O PRODUTO
				produto.setImgs(ProdutoService.carregaListaDeImagensProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE IMAGENS DO PRODUTO
				
				listaProdutos.add(produto);
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
		
		}
		
		return listaProdutos;*/
		
		if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
			return null;
		}
		
		return ProdutoRepository.findAllProdutos();
	}
	
	/* GET BY ID - TRAZ UM DADO DE ACORDO COM O ID PASSADO */
	@GET
    @Path("/{id}/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto getByProdutosId(@PathParam("id") int id, @PathParam("token") String token) {
		
		/*Produto produto = null;
		
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
				produto.setEstoque(resultSet.getString("estoque"));
				produto.setUsuarios(ProdutoService.carregaListaDeUsuariosQueTemProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE USUARIO QUE TEM O PRODUTO
				produto.setImgs(ProdutoService.carregaListaDeImagensProdutoPorId(connection, produto.getId())); // CARREGA A LISTA DE IMAGENS DO PRODUTO
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return produto;*/
		
		if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
			return null;
		}
		
		return ProdutoRepository.findByProdutosId(id);
	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	@POST
	@Path("/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response postProdutos(Produto produto, @PathParam("token") String token) {
		
		/*try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
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
			
		}*/
		
		if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
			return null;
		}
		
		return ProdutoRepository.postProdutos(produto);	
	}
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	@PUT
	@Path("/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public static Response putProdutos(Produto produto, @PathParam("token") String token) {
		
		/*try {
			
			if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
				return null;
			}
			
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
			
		}*/
		
		if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
			return null;
		}
		
		return ProdutoRepository.putProdutos(produto);
	}
	
	/* DELETE - DELETA UM DETERMINADO DADO DE ACORDO COMO O ID INFORMADO */
	@DELETE
    @Path("/{id}/token/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProdutos(@PathParam("id") int id, @PathParam("token") String token) {
        
        /*try {
        	
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
			
		}*/
		
		if(UsuarioService.autorizaAcessoEndpoint(token) == null) {
			return null;
		}
		
		return ProdutoRepository.deleteProdutos(id); 
    }
	
}
