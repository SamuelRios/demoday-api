# DemoDay-api
Repositório da API do Sistema DemoDay

- Documento de Requisitos:
https://docs.google.com/document/d/1WxX1Q8yOPc812UFwMu00FkKZp7S3j5BIyUZi6pdiwp0/edit?usp=sharing


# Rotas
## POST /createuser

Esta rota é usada para criar um novo usuário.

### Parâmetros de entrada

A requisição deve incluir um corpo JSON contendo os dados do usuário a ser criado.

| Nome     | Tipo     | Descrição                                    | Obrigatório | 
|----------|----------|----------------------------------------------|-------------|
| `cpf`    | String   | CPF do usuário | Sim |
| `name`   | String   | Nome do usuário | Sim |
| `email`   | String   | Email do usuário | Sim |
| `universidade`   | String   | Email do usuário | Não |
| `tipo`   | Integer   | Tipo do usuário. 0 indica Aluno e 1 indica Professor. | Sim |
| `senha` | String | Senha do usuário. Deve conter pelo menos 6 caracteres.| Sim|

Exemplo de corpo da requisição:

```json
{
    "name": "Joaquim Joarez",
    "email": "testejoaqui@exemplo.com",
    "cpf": "12345678910",
    "university": "Universidade Federal da Bahia",
    "password": "123456"

}
```

### Resposta
 - 200 OK: A solicitação foi bem-sucedida. O usuário foi criado com sucesso.

    Exemplo de resposta:
   ```json
    {
      "userId": "Yc6TnPmYSXb03ssg72uR6kL567d8",
    }
    ```
-  400 BAD REQUEST: Os dados fornecidos são inválidos.

     Exemplo de resposta:
   ```json
    {
      "timestamp": "2024-04-08T20:34:29.166234075Z",
      "status": 400,
      "errors": {
          "name": "Nome é obrigatório.",
          "password": "A senha deve possuir pelo meno 6 caracteres."
      },
      "message": "Argumentos Inválidos.",
      "path": "/createuser"
    }
    ```
 -  409 CONFLICT: Já existe um usuário com o CPF ou e-mail fornecido.

     Exemplo de resposta:
     ```json
    {
      "timestamp": "2024-04-08T20:38:17.357155782Z",
      "status": 409,
      "errors": {
          "email": "Email já registrado."
      },
      "message": "Email já cadastrado.",
      "path": "/createuser"
    }
    ```
 -  500 Internal Server Error: Ocorreu um erro interno ao tentar criar o usuário.