# POO Sprint 3 — persistência Oracle/JDBC

## Checklist de entrega

- [x] `seu-script-criacao.sql`: cria as tabelas `EQUIPE_MANUTENCAO`, `TRECHO_RODOVIA`, `INTERVENCAO_OPERACIONAL` e `RELATORIO_PRIORIDADE`, baseadas nas entidades utilizadas pelo projeto.
- [x] `seu-script-dados.sql`: contém dados de teste para equipes, trechos e intervenção.
- [ ] Banco configurado e scripts executados: confirmar a execução no Oracle SQL Developer/SQL*Plus do laboratório.
- [x] `ConexaoBanco.java`: contém o host, porta, SID, usuário e senha Oracle configurados, além de `getConexao()` e `fechar(Connection)`.
- [x] DAOs criados para as entidades persistentes: equipe, trecho e intervenção, com inserir, buscar, listar, atualizar e deletar.
- [x] `RelatorioPrioridadeDAO` criado e integrado ao `GeradorRelatorio`.
- [x] `GeradorRelatorio.java` imprime o relatório e salva seu histórico no banco.
- [x] `Main.java` demonstra conexão, inserção, consulta, atualização, geração do relatório e consulta do histórico.
- [ ] Testes executados com sucesso: os testes unitários existentes precisam ser executados; o teste de integração depende do acesso ao Oracle.
- [x] README atualizado com instruções de configuração e execução.

> Observação: o enunciado menciona `ConexaoBD.java`, mas o projeto utiliza o nome `ConexaoBanco.java`. A implementação foi mantida com o nome existente no repositório.

## Configuração do banco

A conexão está em `src/database/ConexaoBanco.java` e utiliza o Oracle FIAP:

- host: `oracle.fiap.com.br`
- porta: `1521`
- SID: `ORCL`
- usuário e senha: configurados nas constantes `USER` e `PASSWORD`

Por segurança, não publique credenciais reais no GitHub. Caso este repositório seja público, altere a senha do banco e remova as credenciais do código antes da entrega final. O ideal é carregar esses valores por variáveis de ambiente ou por configuração local ignorada pelo Git.

## Execução dos scripts

Execute os scripts nesta ordem no Oracle SQL Developer ou SQL*Plus:

1. `seu-script-criacao.sql`;
2. `seu-script-dados.sql`.

O script de criação remove as tabelas anteriores e recria as tabelas com suas chaves primárias e estrangeiras. O script de dados deve ser executado após a criação.

## Compilação e execução

Linux/macOS:

```bash
javac -cp "lib/ojdbc17.jar" -d out $(find src -name "*.java")
java -cp "out:lib/ojdbc17.jar" Main.Main
```

Windows PowerShell:

```powershell
javac -cp "lib/ojdbc17.jar" -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp "out;lib/ojdbc17.jar" Main.Main
```

O `Main` cria valores em Java e os envia ao Oracle pelos DAOs. As conexões, `PreparedStatement` e `ResultSet` são fechados com try-with-resources; a conexão principal é fechada no bloco `finally`.

## Testes

Os testes atuais estão em `src/test` e validam as regras da Sprint 2. Para validar a Sprint 3 completamente, execute também o `Main` com o Oracle acessível e verifique:

- conexão realizada com sucesso;
- inserção, consulta e atualização de equipe;
- inserção e listagem de trecho;
- inserção e consulta de intervenção;
- relatório salvo em `RELATORIO_PRIORIDADE`;
- histórico de relatórios retornado pelo DAO.
