# POO Sprint 3 — persistência Oracle/JDBC

## Diagnóstico do erro ORA-04043

O erro abaixo:

```text
ORA-04043: o objeto EQUIPE_MANUTENCAO não existe
```

significa que a tabela `EQUIPE_MANUTENCAO` não existe no schema Oracle usado pela conexão, ou que os scripts foram executados com outro usuário.

A classe `ConexaoBanco` conseguiu abrir a conexão; portanto, o problema não é o driver nem a senha. O erro ocorreu ao inserir a equipe no `EquipeManutencaoDAO`.

## Como corrigir no Oracle

Execute o script de criação conectado com o mesmo usuário configurado em `ConexaoBanco.java`:

```sql
SELECT USER FROM DUAL;

@seu-script-criacao.sql
@seu-script-dados.sql

SELECT TABLE_NAME
FROM USER_TABLES
WHERE TABLE_NAME IN (
    'EQUIPE_MANUTENCAO',
    'TRECHO_RODOVIA',
    'INTERVENCAO_OPERACIONAL',
    'RELATORIO_PRIORIDADE'
);
```

Se o resultado não listar as quatro tabelas, o script não foi executado no schema correto. Execute-o no Oracle SQL Developer ou SQL*Plus antes de iniciar o Java.

## Tratamento implementado

Foi criada a exceção `exception.TabelaNaoEncontradaException`. O `EquipeManutencaoDAO` agora identifica o código Oracle `4043` e exibe uma mensagem orientando a executar `seu-script-criacao.sql` com o mesmo usuário da conexão, em vez de apresentar apenas uma exceção genérica.

## Checklist de entrega

- [x] `seu-script-criacao.sql` com as tabelas do projeto.
- [x] `seu-script-dados.sql` com dados de teste.
- [ ] Scripts executados no schema Oracle correto.
- [x] `ConexaoBanco.java` com `getConexao()` e `fechar(Connection)`.
- [x] DAOs de equipe, trecho e intervenção.
- [x] `RelatorioPrioridadeDAO` integrado ao `GeradorRelatorio`.
- [x] `Main.java` demonstrando conexão, CRUD e relatório.
- [ ] Teste de integração executado após a criação das tabelas.

## Execução

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
