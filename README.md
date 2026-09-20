# POO Sprint 3 — persistência Oracle/JDBC

## Checklist de entrega
- [x] `seu-script-criacao.sql`: tabelas baseadas nas entidades da Sprint 2.
- [x] `seu-script-dados.sql`: dados de teste.
- [ ] Banco configurado/executado: depende da execução no laboratório Oracle.
- [x] `ConexaoBanco.java`: usa `DB_URL`, `DB_USER` e `DB_PASSWORD` sem versionar credenciais.
- [x] DAOs de equipe, trecho e intervenção com CRUD e `PreparedStatement`.
- [x] DAO de relatório integrado ao `GeradorRelatorio`.
- [x] `Main` demonstra conexão, CRUD, relatório e histórico.
- [ ] Testes de integração Oracle: executar com credenciais válidas no laboratório.
- [x] Exceções de domínio em `src/exception`.

## Execução
1. Execute `seu-script-criacao.sql` e depois `seu-script-dados.sql` no Oracle.
2. Configure as variáveis: `DB_URL` (opcional), `DB_USER` e `DB_PASSWORD`.
3. Compile incluindo o driver: `javac -cp "lib/ojdbc17.jar" -d out $(find src -name "*.java")`.
4. Execute: `java -cp "out:lib/ojdbc17.jar" Main.Main` (Windows: use `;` no classpath).

Os DAOs fecham `Connection`, `PreparedStatement` e `ResultSet` com try-with-resources. O `ojdbc17.jar` já está em `lib/`; credenciais reais não devem ser commitadas.
