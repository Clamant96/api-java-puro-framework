# API REST JAVA PURO - FRAMEWORK 

## REFERENCIAS PARA O DESENVOLVIMENTO DO FRAMEWORK
[X] - https://www.treinaweb.com.br/blog/criando-uma-api-restful-com-a-jax-rs-api <br />
[X] - https://www.youtube.com/watch?v=HV16Z6dWOCk&list=PLXbKgo5jPQE8ZvE1AGB_2guuBhRa3VPOh <br />
[X] - https://www.youtube.com/watch?v=KXTOVbj2NEE&list=PLi75dzoFwEbrKsOZsp_bVS4_dHl2sZBva <br />
[X] - https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-File-Upload-Servlet-Ajax-Example (FILE UPLOAD EXEMPLO)
[X] - https://www.treinaweb.com.br/blog/implementando-autenticacao-baseada-em-jwt-em-uma-api-restful-jax-rs (AUTENTICAÇÃO AUTH)

## ENDPOINTS CRIADOS ATE O MOMENTO

### ENDPOINTS USUARIO:
##### GET ALL USUARIOS: http://localhost:8080/usuarios/token/{token}
##### GET BY ID USUARIO: http://localhost:8080/usuarios/{id_usuario}/token/{token}
##### GET COMPRAR PRODUTO: http://localhost:8080/usuarios/comprar/id_produto/{id_produto}/id_usuario/{id_usuario}/token/{token}

##### POST CADASTRO USUARIO: http://localhost:8080/usuarios/cadastro
##### Body: {
    "username": "Ola teste 3",
    "senha": "123456"
}
##### POST LOGIN USUARIO: http://localhost:8080/usuarios/login
##### Body: {
    "username": "Ola teste 3",
    "senha": "123456"
}

##### PUT ATUALIZAR USUARIO: http://localhost:8080/usuarios/atualizar
##### Body: {
    "id": 1,
    "username": "Ola teste 3 atualizado",
    "senha": "654321"
}

##### DELETE USUARIO: http://localhost:8080/usuarios/{id_usuario}/token/{token}

### ENDPOINTS PRODUTO:
##### GET ALL PRODUTOS: http://localhost:8080/produtos/token/{token}
##### GET BY ID PRODUTO: http://localhost:8080/produtos/{id_produto}/token/{token}

##### POST CADASTRO PRODUTO: http://localhost:8080/produtos/token/{token}
##### Body: {
    "titulo": "Produto 3",
    "descricao": "Teste de produto 3"
}

##### PUT ATUALIZA PRODUTO: http://localhost:8080/produtos/token/{token}
##### Body: {
	"id": 1,
    "titulo": "Produto 3 atualizado",
    "descricao": "Teste de produto 3 atualizado"
}

##### DELETE PRODUTO: http://localhost:8080/produtos/{id_produto}/token/{token}

### ENDPOINTS IMG:
##### GET ALL IMG: http://localhost:8080/imgs/token/{token}
##### GET BY ID IMG: http://localhost:8080/imgs/{id_img}/token/{token}

##### POST CADASTRO IMG: http://localhost:8080/imgs/token/{token}
##### Body: {
    "img_1": "https://www.ifrr.edu.br/midia/teste/image",
    "img_2": "https://23103.cdn.simplo7.net/static/23103/sku/Mecanica-produto-teste-nao-comprar--p-1533242083167.jpg",
    "img_3": "https://st.depositphotos.com/1032577/3238/i/600/depositphotos_32382611-stock-photo-test.jpg",
    "img_4": "https://static3.tcdn.com.br/img/img_prod/460977/teste_100485_1_cbc226c7d23a19c784fb4752ffe61337.png",
    "img_5": "https://rockcontent.com/br/wp-content/uploads/sites/2/2020/02/teste-de-lideran%C3%A7a.png",
    "produto": {
        "id": 1
    }
}

##### PUT ATUALIZA IMG: http://localhost:8080/imgs/token/{token}
##### Body: {
    "id": 1,
    "img_1": "https://www.ifrr.edu.br/midia/teste/image",
    "img_2": "https://rockcontent.com/br/wp-content/uploads/sites/2/2020/02/teste-de-lideran%C3%A7a.png",
    "img_3": "",
    "img_4": "",
    "img_5": "",
    "produto": {
        "id": 1
    }
}

##### DELETE IMG: http://localhost:8080/imgs/{id_img}/token/{token}