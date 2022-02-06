# API REST JAVA PURO - FRAMEWORK 

## REFERENCIAS PARA O DESENVOLVIMENTO DO FRAMEWORK
[X] - https://www.treinaweb.com.br/blog/criando-uma-api-restful-com-a-jax-rs-api <br />
[X] - https://www.youtube.com/watch?v=HV16Z6dWOCk&list=PLXbKgo5jPQE8ZvE1AGB_2guuBhRa3VPOh <br />
[X] - https://www.youtube.com/watch?v=KXTOVbj2NEE&list=PLi75dzoFwEbrKsOZsp_bVS4_dHl2sZBva

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