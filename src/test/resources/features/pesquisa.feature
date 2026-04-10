#language: pt

@pesquisa
Funcionalidade: Pesquisa de artigos no Blog do Agi
  Como um visitante do Blog do Agi
  Quero utilizar a funcionalidade de pesquisa
  Para encontrar artigos sobre temas financeiros de meu interesse

  Fundo:
    Dado que o usuario esta na pagina inicial do Blog do Agi

  @pesquisa_valida @smoke
  Cenario: Busca com termo valido exibe artigos relevantes
    Quando o usuario clica no icone de pesquisa
    E digita o termo de busca "tecnologia"
    E confirma a pesquisa pressionando Enter
    Entao a pagina de resultados e exibida
    E pelo menos um artigo e listado nos resultados
    E os artigos exibidos sao relacionados ao termo "tecnologia"

  @pesquisa_invalida @smoke
  Cenario: Busca com termo inexistente exibe mensagem de nenhum resultado
    Quando o usuario clica no icone de pesquisa
    E digita o termo de busca "quality assurance"
    E confirma a pesquisa pressionando Enter
    Entao a pagina de resultados e exibida
    E uma mensagem de nenhum resultado encontrado e exibida
    E nenhum card de artigo e listado na pagina
