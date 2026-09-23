# Testes unitários

O projeto possui testes JUnit para os modelos, regras de priorização, intervenções e fluxo principal sem banco de dados.

## Testes incluídos

- `TrechoRodoviaTest`: validação de KM, altura, terreno, crescimento da vegetação e equipe.
- `EquipeManutencaoTest`: validação de nome, especialidade e direções permitidas.
- `IntervencaoRegistroTest`: validação do registro de intervenção e data padrão.
- `MotorPriorizacaoTest`: classificação por altura da vegetação e geração do relatório.
- `IntervencoesTest`: descrições das intervenções manual, mecanizada e pulverização.
- `TrechoMonitoradoIoTTest`: transmissão dos dados do sensor.
- `AplicacaoTest`: teste de fluxo da aplicação sem depender do Oracle.

## Executar no IntelliJ

1. Abra a pasta `src/test`.
2. Clique com o botão direito em uma classe de teste ou na pasta.
3. Selecione **Run Tests**.
4. Confira o resultado na janela **Run**.

Os testes unitários não precisam de conexão com o Oracle. Os testes de DAO, quando necessários, devem ser executados como testes de integração com um banco configurado.

## Dependências

Os testes usam JUnit 5. Se o IntelliJ não reconhecer `org.junit.jupiter.api.Test`, adicione as dependências JUnit 5 ao projeto ou configure-as no Maven/Gradle.
