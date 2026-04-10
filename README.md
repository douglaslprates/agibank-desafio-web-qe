# Agibank Desafio QE - Automação de Testes Web

Projeto de automação de testes web desenvolvido como desafio técnico utilizando Java, Selenium WebDriver, Cucumber e Maven. O projeto cobre o fluxo de pesquisa de artigos no Blog do Agi.

## Pré-requisitos

- Java JDK 17+
- Maven 3.6+
- Git (para clonar o repositório)
- Google Chrome ou Mozilla Firefox instalado
- IDE (IntelliJ IDEA, Eclipse ou VS Code)

## Instalação e Configuração

1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd agibank-desafio-web-qe
```

2. Instale as dependências

```bash
mvn clean install
```

3. Execute os testes

```bash
mvn test
```

## Cenários Testados

### Pesquisa de artigos

- Busca com termo válido exibindo resultados relevantes
- Busca com termo inexistente exibindo mensagem de nenhum resultado

## Estrutura do Projeto

```text
src/
  main/java/
    pages/
      BasePage.java
      HomePage.java
      ResultadosPesquisaPage.java
    utils/
      DriverFactory.java

  test/java/
    hooks/
      Hooks.java
    runner/
      TestRunner.java
    steps/
      PesquisaSteps.java

  test/resources/
    features/
      pesquisa.feature
```

## Executando os Testes

### Via terminal

```bash
mvn test
```

### Parâmetros de execução

Voce pode alterar navegador e modo headless via variáveis de ambiente:

- `BROWSER=chrome` (padrão) ou `BROWSER=firefox`
- `HEADLESS=true` ou `HEADLESS=false` (padrão)

Exemplo (PowerShell):

```powershell
$env:BROWSER="firefox"
$env:HEADLESS="true"
mvn test
```

## Pipeline (CI)

A pipeline do projeto está em `.github/workflows/pipeline.yml` com o nome `Test Pipeline - Blog do Agi`.

### Quando executa

- `push` para `main`, `master` e `develop`
- `pull_request` para `main`, `master` e `develop`
- agendamento diário (`cron: 0 5 * * *`)
- execução manual (`workflow_dispatch`)

### O que executa

- ambiente `ubuntu-latest`
- JDK 17 (Temurin) com cache Maven
- execução dos testes em matriz de navegadores:
  - `chrome`
  - `firefox`
- comando de execução:

```bash
mvn clean test -Dbrowser=<chrome|firefox> -Dheadless=true
```

### Artefatos publicados

- `cucumber-report-<browser>` com `target/cucumber-reports/`
- `screenshots-<browser>` com `target/screenshots/`
- geração de relatório Allure via `simple-elf/allure-report-action@v1`

## Relatórios de Teste

A execução gera relatórios Cucumber em:

- `target/cucumber-reports/relatorio.html`
- `target/cucumber-reports/relatorio.json`

## Observações

- O runner esta configurado em `src/test/java/runner/TestRunner.java`.
- O projeto utiliza Cucumber com JUnit 4 (`cucumber-junit`).

## Licença

Este projeto foi criado para fins de desafio técnico e estudo.
