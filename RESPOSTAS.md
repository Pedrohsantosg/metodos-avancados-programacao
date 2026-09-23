# Respostas: Exercício 02 (Strategy)

## Pergunta sobre o código original

**O que acontece com a classe `Pedido` toda vez que uma nova forma de frete precisa ser adicionada?**

Ela precisa ser aberta e editada: acrescenta-se mais um `else if` dentro de `calcularFrete()`. Isso traz três consequências:

- O `Pedido` vira o dono de todas as tabelas de frete da loja. Ele passa a ter vários motivos para mudar (reajuste do Sedex, novo contrato com transportadora...), e nenhum deles tem relação com o que um pedido é.
- Mexer em um método que já funciona pode quebrar as outras modalidades. Por isso, toda alteração obriga a testar tudo de novo.
- A escolha do frete é feita por uma `String`**.** Um `"SEDX"` digitado errado compila normalmente e só estoura em tempo de execução, no `IllegalArgumentException`.

---

## Questões discursivas

### 1. Qual problema o padrão Strategy resolve?

Resolve o caso em que uma classe precisa executar uma mesma tarefa de várias maneiras e acaba acumulando todas elas dentro de si. O Strategy separa cada maneira em uma classe própria atrás de uma interface comum (`EstrategiaDeFrete`). A classe que usa o comportamento (`Pedido`) passa a depender apenas dessa interface e pode receber ou trocar a estratégia em tempo de execução, sem condicionais. Cada regra de frete pode então ser lida, alterada e testada isoladamente.

### 2. O que aconteceria se usássemos apenas if/else?

Cada modalidade nova aumentaria o método e obrigaria a modificar o `Pedido`. Com o tempo, o método fica longo, cheio de caminhos e difícil de testar. Uma alteração em uma regra pode afetar as demais, e o mesmo bloco de `if` tende a se repetir em outros pontos do sistema (tela de checkout, relatórios), multiplicando os lugares a corrigir. Na prática, o código fica mais frágil a cada nova regra de negócio.

### 3. Como o sistema fica aberto para extensão?

Uma nova modalidade entra no sistema criando uma classe nova que implementa `EstrategiaDeFrete`. Nada do que já existe é modificado: nem o `Pedido`, nem as outras estratégias. Isso é o Princípio Aberto/Fechado: aberto para extensão e fechado para modificação. O item 5 do exercício demonstra isso: o `FreteInternacional` foi incluído sem que o `Pedido` sofresse qualquer alteração.

### 4. O que acontece se cada país tiver uma regra de cálculo diferente no frete internacional?

A primeira coisa é identificar o que muda entre os países:

- Se muda só o valor (taxa de importação e preço por kg), criar uma classe por país seria repetir a mesma fórmula várias vezes. Basta uma estratégia configurável, `FreteInternacionalPorPais`, que recebe a tarifa do país a partir de uma tabela (`TabelaTarifasInternacionais`). Incluir um país passa a ser incluir um dado, não escrever código.
- Se muda a fórmula (por exemplo, isenção de taxa abaixo de certo peso), aquele destino ganha sua própria estratégia, como a `FreteMercosul` desta solução.

O que não se deve fazer é colocar um `if (pais.equals(...))` dentro de `FreteInternacional`. Isso levaria para dentro da estratégia exatamente o problema que o padrão tirou do `Pedido`. Em qualquer dos dois caminhos, o `Pedido` continua sem alteração.

### 5. Em quais outros cenários Strategy poderia ser utilizado?

Em qualquer situação com uma tarefa fixa e várias formas intercambiáveis de executá-la:

- Pagamento: Pix, cartão de crédito, boleto.
- Tributação: cálculo de ICMS conforme o estado de destino.
- Transporte público: tarifa por categoria de passageiro (inteira, meia-estudante, gratuidade do idoso).
- Trânsito: valor de multa conforme a natureza da infração (leve, média, grave, gravíssima).
- Navegação: rota mais rápida, mais curta ou sem pedágio.
- Processamento de dados: algoritmos de ordenação, compressão ou exportação (PDF, CSV, JSON).

---

## Desafio extra: regras promocionais

### 1. Essas regras são novas estratégias de cálculo ou promoções?

São promoções. Uma estratégia descreve *como uma modalidade de entrega cobra*. "Frete grátis a partir de R$ 399" não é uma forma de entregar: é uma condição comercial que pode valer para qualquer modalidade. O produto continua indo por Sedex ou PAC; o que muda é o valor final cobrado do cliente.

### 2. Elas deveriam modificar Sedex, PAC, Internacional e as demais regras?

Não. Colocar a promoção dentro de cada estratégia teria três efeitos ruins:

- Duplicação: a mesma regra seria copiada em todas as classes. Se o mínimo mudar para R$ 499, seria preciso alterar todas.
- Mistura de responsabilidades: a tabela da transportadora ficaria junto com a política de marketing da loja.
- Violação do Princípio Aberto/Fechado: cada campanha nova obrigaria a modificar classes que já estão prontas e testadas.

Além disso, a regra depende do valor dos produtos, uma informação que a estratégia de frete nem recebe.

### 3. Como evitar alterar todas as classes já implementadas?

Com o padrão Decorator.Nesta solução, `FreteComPromocao` implementa a mesma interface `EstrategiaDeFrete` e guarda dentro de si uma modalidade real. Ao calcular, ela pede o valor à modalidade embrulhada e aplica o seu ajuste:

```java
EstrategiaDeFrete frete = new FreteGratisAPartirDe(new Sedex(), 399.00, pedido.getValorDosProdutos());
pedido.definirEstrategia(frete);
```

Como a promoção também é uma `EstrategiaDeFrete`, o `Pedido` a aceita sem saber que ela existe. As promoções podem ainda ser empilhadas: `new CupomDeFrete(new FreteGratisAPartirDe(new PAC(), 399, valor), 20)`. Nenhuma estratégia existente foi alterada, e uma nova campanha é apenas uma nova subclasse de `FreteComPromocao`.
