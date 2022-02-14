package br.com.helpconnect.api.security;

import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

//import javax.crypto.SecretKey;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.anotations.Authorize;
import br.com.helpconnect.api.model.Usuario;
//import br.com.helpconnect.api.service.UsuarioService;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Priority;
//import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Authorize
@Priority(Priorities.AUTHENTICATION)
public class AuthorizeFilter implements ContainerRequestFilter {
	
	//private final SecretKey CHAVE = Keys.hmacShaKeyFor("7f-j&CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&RNbDHUX6".getBytes(StandardCharsets.UTF_8));
	
	private static String tokenArmazenado;

	public static String getTokenArmazenado() {
		return tokenArmazenado;
	}

	public static void setTokenArmazenado(String tokenArmazenado) {
		AuthorizeFilter.tokenArmazenado = tokenArmazenado;
	}

	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        try {
            //String token = authorizationHeader.substring("Bearer".length()).trim();
            
            /*Jwts.parserBuilder()
				.setSigningKey(CHAVE)
				.build()
				.parseClaimsJws(token);*/
            
        	String token = authorizationHeader;
        	
        	tokenArmazenado = token; // ARMAZENA O TOKEN ENVIADO PELO USUARIO
            
        } catch (Exception erro) {
        	AuthorizeFilter.setTokenArmazenado(null);
        	
            requestContext
				.abortWith(Response.status(Response.Status.UNAUTHORIZED)
				.build());
        }

    }

	public static Usuario validaToken() throws IOException {
		
		/* CASO NAO TENHA SIDO PASSADO O TOKEN COMO PARAMETRO NO HEADER */
		if(AuthorizeFilter.getTokenArmazenado() == null || AuthorizeFilter.getTokenArmazenado() == "") {
			return null;
		}
    	
    	Usuario usuario = new Usuario();
		
		try {
			
			String tokenDecode = new String(Base64.getDecoder().decode(AuthorizeFilter.getTokenArmazenado().getBytes()));
			
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

}