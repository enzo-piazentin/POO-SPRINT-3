# POO Sprint 3 — persistência Oracle/JDBC

## Checklist de entrega
- [x] `seu-script-criacao.sql`: tabelas baseadas nas entidades da Sprint 2.
- [x] `seu-script-dados.sql`: dados de teste.
- [ ] Banco configurado/executado: executar os scripts no laboratório Oracle.
- [x] `ConexaoBanco.java`: mantida no formato original, com `HOST`, `PORT`, `SID`, `USER`, `PASSWORD`, `getConexao()` e `fechar()`.
- [x] DAOs de equipe, trecho e intervenção com CRUD e `PreparedStatement`.
- [x] DAO de relatório integrado ao `GeradorRelatorio`.
- [x] `Main` demonstra conexão, inserção, consulta, atualização, relatório e histórico.
- [ ] Testes de integração Oracle: executar com credenciais válidas no laboratório.
- [x] Exceções de domínio em `src/exception`.

## Configuração do banco

Antes de executar, abra `src/database/ConexaoBanco.java` e substitua:

- `USER` pelo usuário Oracle do laboratório;
- `PASSWORD` pela senha Oracle do laboratório.

Não envie credenciais reais para o GitHub.

## Execução dos scripts

Execute nesta ordem no Oracle SQL Developer ou SQL*Plus:

1. `seu-script-criacao.sql`;
2. `seu-script-dados.sql`.

O script de dados cadastra equipes, trechos e uma intervenção usando valores compatíveis com as classes do projeto.

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

O `Main` cria valores em Java e os envia ao Oracle pelos DAOs. As conexões, `PreparedStatement` e `ResultSet` são fechados com try-with-resources ou no bloco `finally`.
