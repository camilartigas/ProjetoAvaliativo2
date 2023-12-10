# README - Sistema de Gerenciamento de Medicamentos

## Visão Geral
O Sistema de Gerenciamento de Medicamentos é uma aplicação Java desenvolvida para gerenciar inventários de medicamentos
em farmácias. Permite a adição, venda e troca de medicamentos entre farmácias, além de manter informações de estoque.

## Funcionalidades
- **FarmaciaController**: Gerencia operações relacionadas a farmácias, como buscar todas as farmácias, recuperar uma 
farmácia pelo seu CNPJ e cadastrar uma nova farmácia.
- **MedicamentoController**: Trata funcionalidades relacionadas a medicamentos, incluindo listar todos os medicamentos 
e adicionar novos medicamentos ao sistema.
- **EstoqueController**: Gerencia operações de inventário, como verificação de estoque por CNPJ da farmácia, adição de 
medicamentos ao estoque, venda de medicamentos e troca de medicamentos entre farmácias.
- **InicializacaoController**: Lida com a inicialização do sistema, populando dados iniciais.
- **EnderecoRequest/EstoqueDetalheDTO/EstoqueRequest/EstoqueResponse/FarmaciaRequest/MedicamentoRequest/
TrocaMedicamentosRequest/TrocaMedicamentosResponse**: Estes são Objetos de Transferência de Dados (DTOs) utilizados para
- comunicação entre controladores e serviços.
- **Tratamento de Exceções**: Exceções personalizadas e tratadores para gerenciar erros e exceções em toda a aplicação.

## Tecnologias Utilizadas
- **Java**: Linguagem de programação utilizada no desenvolvimento da aplicação.
- **Spring Boot**: Framework utilizado para construir e gerenciar a aplicação.
- **Hibernate/JPA**: Utilizado para Mapeamento Objeto-Relacional e operações de banco de dados.
- **Banco de Dados PostgreSQL**: Utilizado como sistema de gerenciamento de banco de dados para armazenar dados da 
aplicação.
- **RESTful API**: Controladores são RESTful e lidam com requisições e respostas HTTP.
- **Maven**: Ferramenta de gerenciamento de dependências utilizada para construir o projeto.
- **Validação**: Validação de entrada para requisições usando anotações como `@NotNull`, `@NotBlank`, etc.
- **Tratamento de Exceções**: Exceções personalizadas e tratadores para gerenciamento de erros e tratamento adequado 
de exceções.

## Como Começar
1. **Clonar o Repositório**: Clone o repositório para o seu ambiente local.
2. **Configurar o PostgreSQL**: Certifique-se de que o banco de dados PostgreSQL está instalado e em execução.
3. **Configurar o Banco de Dados**: Modifique o arquivo `application.properties` para configurar as informações de 
conexão com o banco de dados PostgreSQL.

## Configuração
- Atualize o arquivo `application.properties` com as configurações apropriadas para a conexão com o banco de dados
PostgreSQL, incluindo URL, nome de usuário, senha, etc.

## Uso
- Utilize clientes de API como Postman ou cURL para interagir com os endpoints RESTful expostos.
- Acesse diferentes funcionalidades enviando requisições HTTP para os respectivos endpoints definidos nos controladores.

## Possíveis Melhorias no Código

- Implementar autenticação e autorização para controlar o acesso aos endpoints.
- Adicionar logs para rastrear e solucionar problemas mais facilmente.
- Implementação de um sistema de autenticação utilizando Spring Security.
- Criação de usuários, atribuição de permissões e controle de acesso a diferentes funcionalidades com base nos papéis dos usuários.


## Informações de Contato
Para dúvidas ou suporte, entre em contato com Camila Artigas de Prá em camiart@gmail.com.

---

Este README fornece uma visão geral do Sistema de Gerenciamento de Medicamentos, suas características, tecnologias 
utilizadas, instruções de configuração e outras informações relevantes para usuários e desenvolvedores. 

Projeto desenvolvido como forma avaliativa do DEVInHouse.

Segue link do vídeo da apresentação do projeto:
https://drive.google.com/drive/folders/19IRmqfIEWpsqDaaWM6rtnzgkWsmmel71?usp=sharing
