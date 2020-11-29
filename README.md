# O que é?

Este repositório contém a implementação do desafio de código proposto
[aqui](https://github.com/ddd-by-examples/library) pelo olhar do CDD. 

Resumidamente existe a necessidade de implementar uma API para possibilitar
o controle de empréstimo e devolução de livros de uma biblioteca pública.

## Cognitive Driven Development

O design de código escolhido para realizar a implementação é inspirado
no [Cognitive Driven Development(CDD)](https://github.com/asouza/pilares-design-codigo/blob/master/ICSME-2020-cognitive-driven-development.pdf).

## Métrica que vai ser utilizada

A ideia do CDD é definir um limite de pontos de complexidade intrínseca para guiar
o nosso desenvolvimento e com isso fazer a definição do que conta ponto
de dificuldade de entendimento. 

Aqui temos tudo que conta ponto de complexidade intrínseca para este projeto:

1. Acoplamento com classes específicas do projeto
2. Utilização de branches de código (if, else, switch,operador ternário(?/:),try,catch)
3. Funções sendo passada como argumento. Ex: ```lista.foreach(e -> e.toString());```.

O *limite* de pontos por classe é de 7. Vamos utilizar 7 porque este é
um número importante na psicologia da cognição e referenciado faz muito tempo. 
Você pode saber mais no artigo o [mágico número sete](http://www2.psych.utoronto.ca/users/peterson/psy430s2001/Miller%20GA%20Magical%20Seven%20Psych%20Review%201955.pdf)

## Tecnologias

Aqui vamos de básico:

* Java como linguagem
* Spring como framework fundamental da aplicação
 * Spring Boot
 * Spring MVC
 * Spring Validation
 * Spring Scheduler
* Hibernate como implementação da JPA para ORM

