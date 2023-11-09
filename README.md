# Parking Control API - API de controle de estacionamento

Onde será utilizado:
Spring Boot - Para iniciar o projeto/aplicação

Será utilizado: 
Spring MVC - Para contruir a aplicação web

Será utilizado: 
Spring Data JPA - Para construir as transações com o banco de dados Postgres

Será utilizado:
Spring Validation - Para realizar algumas validações iniciais

O recurso principal que vamos trabalhar: Vaga de estacionamento.

## Metodos:

Get com paginação - onde da pra solicitar uma determinada pagina, definir o numero de elementos daquela pagina, e tb a ordenação

Get One - onde obtem os detalhes de um unico recurso passando o Id

Post - salvar novos controles de vagas estacionamento

Put - para atualizar uma determinada vaga de estacionamento 

Delete -  Para deletrar um registro.
 
## Objetivo do projeto: Colocar em pratica a criação de um projeto de Spring Boot com os outros projetos do ecossitema Spring 

Vamos simular que o controle de estacionamento de uma pousada e as vagas serão fixas e destinadas de acordo com os quartos dessa pousada.
quando formos fazer o cadastro de uma determinada vaga de uma pousada pra o hospede que bookou o quarto, a vaga terá um numeros e letras então será do tipo string, a vaga terá um veicula que pertecera por toda estadia do hospede,o veiculo tem placa, modelo, cor, marca e qual o quarto que pertencerá a vaga, qual a data de registro (chekin) e a data de checkout, quem é o responsavel pelo veiculo, qual a placa do veiculo que pertencerá a vaga.

Regra: Cada quarto que o hospede reservar tem direito a uma vaga.
