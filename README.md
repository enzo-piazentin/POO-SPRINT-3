# Perguntas de Reflexão - Sprint 2

## 1. Por que não faz sentido instanciar apenas uma Intervenção Operacional genérica sem especificar qual é?

Não faz sentido instanciar uma `IntervencaoOperacional` genérica porque o sistema precisa saber exatamente qual serviço será executado em um trecho da rodovia. Uma intervenção operacional pode representar diferentes ações, como roçada mecanizada ou pulverização, e cada uma possui comportamentos específicos.

Por esse motivo, a classe `IntervencaoOperacional` foi definida como abstrata, servindo apenas como modelo base para as demais intervenções. Dessa forma, cada classe filha é obrigada a implementar o método `executarServico()`, garantindo que o comportamento correto seja executado.

Além disso, essa abordagem segue os princípios da Programação Orientada a Objetos, promovendo abstração, reutilização de código e polimorfismo.

---

## 2. Qual a diferença arquitetural entre fazer um Trecho herdar de uma classe abstrata e implementar uma Interface?

Embora ambos os mecanismos permitam reutilização e organização do código, eles possuem objetivos diferentes.

### Classe Abstrata

Uma classe abstrata representa uma relação do tipo **"é um"**. Ela define características e comportamentos comuns que serão compartilhados pelas classes filhas.

Exemplo:

* `RocadaMecanizada` é uma `IntervencaoOperacional`.
* `Pulverizacao` é uma `IntervencaoOperacional`.

A classe abstrata pode conter:

* Atributos;
* Métodos implementados;
* Métodos abstratos;
* Regras de negócio compartilhadas.

### Interface

Uma interface representa uma capacidade ou comportamento que uma classe pode possuir.

Exemplo:

* `TrechoRodovia` implementa `MonitoravelViaIoT`.

Nesse caso, a interface apenas define um contrato, obrigando a implementação do método `transmitirDadosSensor()`, sem determinar como ele será executado.

As principais vantagens são:

* Baixo acoplamento;
* Maior flexibilidade;
* Possibilidade de implementar múltiplas interfaces.

### Comparação

| Classe Abstrata                         | Interface                                    |
| --------------------------------------- | -------------------------------------------- |
| Representa uma relação "é um".          | Representa uma capacidade ou comportamento.  |
| Pode possuir atributos e implementação. | Define apenas contratos.                     |
| Permite herança única.                  | Permite múltiplas implementações.            |
| Compartilha regras de negócio.          | Compartilha apenas comportamentos esperados. |

No projeto, a classe abstrata foi utilizada para representar os diferentes tipos de intervenção operacional, enquanto a interface foi utilizada para representar a capacidade de um trecho da rodovia transmitir dados automaticamente por meio de sensores IoT.
