package br.com.helpconnect.api.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.helpconnect.api.DB.ConnectionDB;
import br.com.helpconnect.api.model.Img;
import br.com.helpconnect.api.model.Produto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class ImgRepository {
	
	/* GET ALL - TRAZ TODOS OS DADOS CADASTRADOS NA BASE DE DADOS */
	public static List<Img> findAllImgs() {
		
		List<Img> listaImgs= new ArrayList<Img>();
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM img_produto");
			
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
			
		}catch(Exception erro) {
			erro.printStackTrace();
		
		}
		
		return listaImgs;
	}
	
	/* GET BY ID - TRAZ UM DADO DE ACORDO COM O ID PASSADO */
	public static Img findByImgId(int id) {
		
		Img img = null;
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("SELECT * FROM img_produto WHERE id = ?");
			prepare.setInt(1, id);
			
			ResultSet resultSet = prepare.executeQuery();
			
			while(resultSet.next()) {
				
				img = new Img();
				
				Produto produto = new Produto();
				
				produto.setId(resultSet.getInt("id_produto"));
				
				img.setId(resultSet.getInt("id"));
				img.setImg_1(resultSet.getString("img_1"));
				img.setImg_2(resultSet.getString("img_2"));
				img.setImg_3(resultSet.getString("img_3"));
				img.setImg_4(resultSet.getString("img_4"));
				img.setImg_5(resultSet.getString("img_5"));

				img.setProduto(produto); // RECEBE UM OBJETO DE PRODUTO
				
			}
			
		}catch(Exception erro) {
			erro.printStackTrace();
			
		}
		
		return img;
	}
	
	/* POST - CADASTRA UM NOVO DADO NA BASE DE DADOS */
	public static Response postImg(Img img) {
		
		try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("INSERT INTO img_produto (img_1, img_2, img_3, img_4, img_5, id_produto) VALUES (?, ?, ?, ?, ?, ?)");
			
			prepare.setString(1, img.getImg_1());
			prepare.setString(2, img.getImg_2());
			prepare.setString(3, img.getImg_3());
			prepare.setString(4, img.getImg_4());
			prepare.setString(5, img.getImg_5());
			
			prepare.setInt(6, img.getProduto().getId()); // INSERE O ID DO OBJETO DE PRODUTO INSERIDO NO JSON
			
			prepare.executeUpdate();
			
			return Response.status(Status.CREATED).build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* PUT - ATUALIZA UM DADO NA BASE DE DADOS, PARA ISSO USANDO COMO REFERENCIA O ID ENVIADO NO BODY */
	public static Response putImg(Img img) {
		
		try {
			
			if(img.getId() == 0) {
				return null;
			}
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("UPDATE img_produto SET img_1 = ?, img_2 = ?, img_3 = ?, img_4 = ?, img_5 = ?, id_produto = ? WHERE id = ?");
			prepare.setString(1, img.getImg_1());
			prepare.setString(2, img.getImg_2());
			prepare.setString(3, img.getImg_3());
			prepare.setString(4, img.getImg_4());
			prepare.setString(5, img.getImg_5());
			
			prepare.setInt(6, img.getProduto().getId()); // INSERE O ID DO OBJETO DE PRODUTO INSERIDO NO JSON
			
			prepare.setInt(7, img.getId());
			
			prepare.executeUpdate();
			
			return Response.status(Status.ACCEPTED).build();
		
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
		
	}
	
	/* DELETE - DELETA UM DETERMINADO DADO DE ACORDO COMO O ID INFORMADO */
	public static Response deleteImg(int id) {
        
        try {
			
			Connection connection = ConnectionDB.getConnection();
			PreparedStatement prepare = connection.prepareStatement("DELETE FROM img_produto WHERE id = ?");
			prepare.setInt(1, id);
			
			prepare.executeUpdate();
			
			return Response.ok().build();
			
		}catch(Exception erro) {
			return Response.notModified().build();
			
		}
        
    }
	
}
