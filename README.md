# Projeto school

## Sobre esse projeto

Esta é uma API simples que tem o objetivo de gerenciar usuários, cursos e matrículas.

São utilizadas as tecnologias:

- Java 11
- Maven 3+  
- Spring Boot
- Spring Web
- Bean Validation
- Spring Data JPA
- H2, o BD relacional em memória

Abra o projeto na IDE da sua preferência.

Execute os testes automatizados. 

Suba a aplicação e explore a API com uma ferramenta como [cURL](https://curl.se/), [Insomnia](https://insomnia.rest/), [Postman](https://www.postman.com/).

Alguns exemplos de chamadas usando cURL estão em [exemplos-curl.md](exemplos-curl.md).

### O que já está implementado?

Os seguintes endpoints estão implementados:

- `GET /users/{username}` obtém os detalhes de um usuário
- `POST /users` adiciona um novo usuário
- `GET /courses` lista os cursos já cadastrado
- `POST /courses` adiciona um novo curso
- `GET /courses/{code}` obtém os detalhes de um curso

## Sobre o que você deve fazer

Devem ser implementadas as seguintes tarefas.

### 1: Correção dos testes automatizados

Nem todos os testes automatizados que já existem no projeto estão passando.

Altere o código de produção para que todos os testes automatizados passem.

Observação: **NÃO** modifique os testes atuais do projeto!

### 2: Implementar a Matrícula de usuário

Uma Matrícula associa um usuário a um curso, em uma data específica.

Um usuário não pode matricular-se mais de uma vez em um curso.

Implemente um endpoint no projeto que recebe um `POST` em `/courses/{courseCode}/enroll`.

O `username` do usuário a ser matriculado deve estar em um JSON no corpo da requisição.

Por exemplo, para matricular o usuário `alex` no curso de código `java-1`, deve ser feito o seguinte:

```
POST /courses/java-1/enroll
Content-Type: application/json

{"username": "alex"}
```

O _status code_ de retorno deve ser [201 Created](https://httpstatusdogs.com/201-created).

Não é necessário ter um corpo na resposta nem um cabeçalho `Location` por enquanto.

Caso o usuário já esteja matriculado no curso, deve ser retornado um _status code_ [400 Bad Request](https://httpstatusdogs.com/400-bad-request).

Alguns pontos importantes:

- Não deixe de implementar validações. Pense em campos que devem ser obrigatórios e outros cenários de validação.
- Implemente testes automatizados para essa funcionalidade. Baseie-se nos já implementados.
- Seja consistente com o estilo de código que já existe no projeto.

Caso tenha dificuldade, use como referência os posts sobre _Relationships_ do blog do Vlad Mihalcea:  https://vladmihalcea.com/tutorials/hibernate/

Observação: **NÃO** é preciso implementar listagem, edição ou remoção de matrículas.

### 3: Implementar relatório de matrículas

O relátorio deve retornar os usuários que tenha pelo ao menos uma matrícula, ordenada pela quantidade.

Implemente um endpoint `GET` no projeto que retorne os dados em `/courses/enroll/report`.

Exemplo, ao chamar o recurso, o retorno deve ser no seguinte formato:

```
GET /courses/enroll/report
[
    {
        "email": "alex@alura.com.br",
        "quantidade_matriculas": 10
    },
    {
        "email": "caio@alura.com.br",
        "quantidade_matriculas": 5
    }
]
```

Caso não tenha nenhum usuário com matrícula, deve ser retornado um _status code_ [204 No Content](https://httpstatusdogs.com/204-no-content).

Alguns pontos importantes:

- Favoreça o uso do spring data
- Implemente testes automatizados para essa funcionalidade. Baseie-se nos já implementados.


### Bônus (não obrigatório): ampliar testes automatizados

Caso você tenha tempo disponível, amplie os testes automatizados tanto para a funcionalidade que você implementou como para as que já estavam implementadas no projeto.