* Na hora que eu fui testar o limite de emprestimo, caí na outra validação. 
  Já que eu tinha criado apenas 5 instancias e todas elas tinha sido emprestadas
  para o usuário em questão no teste
  
* História do refactor para facilitar o teste, mas sem criar código para o teste. Usuario
  cria o emprestimo e não o livro agora.  
  
* Utilização da Optional.ofNullable para fugir do operador ternário e do teste
  
* Boundary testing para olhar se o usuario pode ainda solicitar emprestimo

* Teste parametrizado para não ficar escrevendo código a toa  