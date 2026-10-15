# Orion Cadastro — Produtos e Clientes

Projeto acadêmico com API REST Java, frontend Angular e persistência MongoDB. O sistema permite cadastrar os produtos de uma loja de impressão 3D e os contatos dos clientes.

**Para executar no Windows/VS Code, leia primeiro [COMECE_AQUI.md](COMECE_AQUI.md).**

## Autoria

Luis Antonio Sarmento Maracajá — matrícula 2515050029.



## Funcionalidades

- Produtos: nome, descrição e preço em reais.
- Clientes: nome, telefone e e-mail.
- Listagem, consulta por ID, inclusão, edição e exclusão nas duas entidades.
- Formulários com validação, confirmação antes de excluir e mensagens de erro/sucesso.
- Rotas `/produtos` e `/clientes`; aplicação responsiva.
- Dados persistidos no MongoDB; não usa localStorage como substituto do banco.

## Correspondência com o enunciado

| Exigência | Implementação |
|---|---|
| API Java com pelo menos duas entidades | Produto e Cliente |
| GET ALL, GET BY ID, POST, PUT, DELETE por entidade | Controllers de produtos e clientes |
| Entidades, Repositorys, Services, Controllers | entities, repositories, services, controllers |
| Angular consumindo API | HttpClient nos dois services Angular |
| Listar, criar, atualizar e apagar no frontend | Duas telas com tabela e formulário |
| Rotas e formulários | Angular Router e Reactive Forms |
| MongoDB | Spring Data MongoDB, coleções produtos e clientes |
| Entrega no GitHub | Instruções em docs/GITHUB.md |

A nomenclatura usa a grafia convencional `repositories`; corresponde à camada chamada “Repositorys” no enunciado. O projeto de referência FacisaFlix foi indicado pelo professor, mas não estava acessível durante a montagem. A implementação segue os requisitos escritos fornecidos; eventuais convenções adicionais de sala devem ser conferidas.

## Tecnologias

- Java 17; Spring Boot 3.5.0; Maven 3.9.9 (bootstrap incluído).
- Angular 20.3.0; TypeScript 5.9.2; Reactive Forms; HttpClient.
- Node compatível: ^20.19.0, ^22.12.0 ou ^24.0.0. Para instalar agora, prefira Node 22.12+ ou 24.
- MongoDB local; compose opcional com MongoDB 8.0.

Versões fixadas para reproduzir este projeto. O arquivo package-lock.json registra as dependências do frontend.

## Estrutura

```text
orion-cadastro/
  backend/
    src/main/java/br/com/orion/cadastro/
      entities/       # Documentos Produto e Cliente
      repositories/   # Acesso ao MongoDB
      services/       # Regras e tratamento de registros inexistentes
      controllers/    # Rotas HTTP
      exceptions/     # Respostas de erro
    src/main/resources/application.properties
    src/test/java/    # Testes HTTP e persistência em servidor de teste
    pom.xml
    mvnw.cmd
    mvnw
  frontend/
    src/app/
      models/         # Interfaces TypeScript
      services/       # Chamadas HTTP
      pages/          # Produtos e Clientes
      app.routes.ts
    proxy.conf.json
    package.json
    package-lock.json
  docs/
  api.http
  compose.yaml
```

## Configuração

A API usa por padrão `mongodb://localhost:27017/orion_cadastro`, com tempo limite de seleção de servidor de cinco segundos. A porta da API é 8080. Para mudar, use as variáveis MONGODB_URI e PORT. A aplicação não carrega um arquivo .env automaticamente.

O frontend de desenvolvimento usa proxy: todas as chamadas são relativas a `/api`. Isso evita depender de permissões CORS globais. Para publicar fora do ambiente local, configure um reverse proxy que encaminhe `/api` ao backend; o build estático isolado não fornece uma API.

O banco e as coleções aparecem no MongoDB após o primeiro cadastro. Os exemplos não são inseridos automaticamente.

## Rotas da API

| Método | Produtos | Clientes | Retorno |
|---|---|---|---|
| GET | /api/produtos | /api/clientes | 200, lista |
| GET | /api/produtos/{id} | /api/clientes/{id} | 200 ou 404 |
| POST | /api/produtos | /api/clientes | 201 e Location |
| PUT | /api/produtos/{id} | /api/clientes/{id} | 200 ou 404 |
| DELETE | /api/produtos/{id} | /api/clientes/{id} | 204 ou 404 |

Produto:

```json
{"nome":"Luminária Cubic","descricao":"Luminária decorativa em PLA bege","preco":200.00}
```

Cliente:

```json
{"nome":"Ana Silva","telefone":"83999999999","email":"ana@example.com"}
```

O ID é uma string gerada pelo MongoDB. O POST ignora um ID enviado pelo cliente. O PUT usa o ID da URL e preserva a identidade do documento. Valores inválidos retornam 400, registros ausentes 404 e indisponibilidade de banco 503 com mensagem sem expor credenciais. Preços aceitam até duas casas decimais e são armazenados como Decimal128 no banco.

Há exemplos de todas as rotas em `api.http`, utilizável com a extensão REST Client do VS Code ou como referência para Postman.

## Testes e build

Backend no Windows:

```powershell
cd backend
.\mvnw.cmd test
.\mvnw.cmd package
```

Linux/macOS: `sh mvnw test`. O script usa Maven instalado ou baixa uma cópia local com conferência SHA-512. O equivalente Windows usa PowerShell para baixar Maven, se necessário.

Frontend:

```powershell
cd frontend
npm ci
npm run build
```

Os testes Java sobem um **servidor de protocolo Mongo em memória (mongo-java-server)** exclusivamente para testar as rotas com persistência e sem exigir MongoDB instalado na máquina de testes. Isso não substitui o teste manual com MongoDB real. A aplicação normal sempre acessa a URI Mongo configurada.

A matriz de testes executados consta em `docs/VALIDACAO.md`. Testes manuais: `docs/TESTE_MANUAL.md`.

## Como explicar o código

1. O usuário preenche o formulário Angular.
2. O componente valida e chama o service Angular.
3. HttpClient envia uma requisição para o controller Java.
4. O controller valida o JSON e chama o service Java.
5. O service busca/grava pelo repository.
6. Spring Data MongoDB persiste o documento e a resposta volta à tela.

Não há login, pagamentos ou vínculo entre produto e cliente: são dois cadastros independentes para manter o escopo da atividade. Revise os arquivos e faça os testes para conseguir demonstrar cada etapa ao professor.

## Referências

- Enunciados das fases 1 e 2 e aviso de final fornecidos pelo aluno.
- Referência indicada pelo professor: https://github.com/JonathanDSLima/FacisaFlix
- Angular: https://angular.dev/reference/versions
- Spring Boot: https://docs.spring.io/spring-boot/3.5/system-requirements.html
- Spring Data MongoDB: https://docs.spring.io/spring-data/mongodb/reference/
