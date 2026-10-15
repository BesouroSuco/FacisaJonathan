# Como executar o Orion Cadastro

Este arquivo explica, de forma simples, como rodar o projeto no computador.

1. O que precisa estar instalado

Antes de começar, e necessario ter instalado:

- Java JDK 17 ou superior;
- Node.js 20.19 ou superior;
- MongoDB local ou Docker Desktop.

2. Iniciar o banco de dados

O projeto usa o MongoDB para salvar os produtos e clientes.

Se o MongoDB ja estiver instalado como serviço do Windows, verifique se ele está iniciado.

Se preferir usar Docker, abra um terminal na pasta principal do projeto e execute:

no powershell
docker compose up -d


Depois disso, o banco estará disponível em 'localhost:27017'.

3. Rodar o backend

O backend e a parte Java que recebe os pedidos do frontend e conversa com o MongoDB.

Abra um terminal na pasta principal do projeto e execute:

noe powershell
cd backend
.\mvnw.cmd spring-boot:run

Aguarde aparecer uma mensagem informando que o Spring Boot foi iniciado. A API ficará disponível em:

Navegador
http://localhost:8080


Não feche esse terminal enquanto estiver usando o sistema.

4. Rodar o frontend

O frontend é a tela que será aberta no navegador.

Abra outro terminal na pasta principal do projeto e execute:

no powershell
cd frontend
npm ci
npm start


Na primeira vez, o comando 'npm ci' pode demorar um pouco, porque instala as dependências do Angular.

Quando aparecer o endereço abaixo, abra-o no navegador:

Navegador 
http://localhost:4200


5. Fazer um teste

Na tela de Produtos, tente fazer o seguinte:

1. Preencher o formulario com nome, descrição e preco.
2. Clique para salvar o produto.
3. Clique em editar e altere alguma informação.
4. Atualize a pagina e confira se o produto continua salvo.
5. Exclua o produto.

Depois, abra a opção Clientes no menu e repita o mesmo teste.

O frontend já está configurado para enviar automaticamente as chamadas para a API na porta 8080.

6. Se aparecer algum erro

- Confira se o MongoDB está iniciado.
- Veja se o terminal do backend continua aberto.
- Confira se o frontend está rodando na porta 4200.
- Se aparecer erro no 'npm', execute 'npm ci' dentro da pasta 'frontend'.
- Se uma porta estiver ocupada, feche outro programa que possa estar usando a mesma porta.

7. Parar o projeto

Para parar o backend ou o frontend, volte ao terminal correspondente e pressione 'Ctrl+C'.

Se o MongoDB foi iniciado pelo Docker, use:

no powershell
docker compose down


Evite usar 'docker compose down -v', porque esse comando pode apagar os dados salvos no banco.
