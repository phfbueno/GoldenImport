# Projeto: Importação de Arquivos CSV

Este projeto permite a importação de dados a partir de arquivos CSV, que podem ser configurados para serem lidos no momento da inicialização da aplicação ou manualmente via uma chamada HTTP POST.

## Requisitos

- **Java**: Versão 21
- **Spring Boot**: Versão 3.5
- **Maven**: Para gerenciamento de dependências e execução da aplicação

## Configuração Inicial

1. **Configurar o arquivo `application.properties`**:  
   No arquivo `src/main/resources/application.properties`, é possível configurar o caminho do arquivo CSV e o nome para ser buscado na inicialização da aplicação.

   Adicione as seguintes propriedades ao arquivo `application.properties`:

   ```properties
   csv.filepath=<caminho-do-arquivo>
   csv.filename=<nome-do-arquivo>

- `csv.filepath`: O caminho onde o arquivo CSV está localizado.
- `csv.filename`: O nome do arquivo CSV a ser lido e importado.

Essas configurações permitem que o sistema leia o arquivo CSV automaticamente no início e salve os dados no banco.


2. **Leitura do Arquivo via Endpoint**  
   Caso prefira, a importação do arquivo pode ser feita manualmente via uma requisição HTTP POST para o endpoint:
    ```bash
    POST http://localhost:8080/csv/upload

### Como Fazer a Requisição no Postman ou Insomnia

- **URL**: `http://localhost:8080/csv/upload`
- **Método**: POST
- **Tipo de Body**: `form-data`
- **Parâmetro do Body**:
    - `file` - Selecione o arquivo CSV no campo de upload

Isso permitirá o envio do arquivo diretamente para a aplicação, que irá processá-lo e salvar os dados no banco de dados.

## Obtendo Informações de Intervalos de Prêmios

Você pode realizar uma requisição GET para obter o produtor com o maior intervalo entre dois prêmios consecutivos, e também o produtor que obteve dois prêmios mais rapidamente.
   
      ```bash
   GET http://localhost:8080/awards/intervals


### Retorno Esperado

O retorno será um JSON similar ao seguinte:

      ```json
   {
     "min": [
       {
         "producer": "Producer 1",
         "interval": 1,
         "previousWin": 2008,
         "followingWin": 2009
       },
       {
         "producer": "Producer 2",
         "interval": 1,
         "previousWin": 2018,
         "followingWin": 2019
       }
     ],
     "max": [
       {
         "producer": "Producer 1",
         "interval": 99,
         "previousWin": 1900,
         "followingWin": 1999
       },
       {
         "producer": "Producer 2",
         "interval": 99,
         "previousWin": 2000,
         "followingWin": 2099
       }
     ]
   }

### Endpoint
## Rodando a Aplicação

No terminal, execute o comando para compilar e iniciar a aplicação:

    ```bash
mvn spring-boot:run

A aplicação estará disponível em http://localhost:8080.

## Executando os Testes de Integração

Para rodar os testes de integração, execute o comando:

      ```bash
   mvn test

Os testes de integração estão configurados para verificar o funcionamento dos endpoints e a importação dos dados no banco de dados.

## Observação

Conforme previamente alinhado, a estrutura do arquivo CSV é fixa e não será alterada, portanto, o sistema está preparado para processá-lo com essa estrutura padrão.

    