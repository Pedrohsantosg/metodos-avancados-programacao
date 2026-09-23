package br.uepb.map.frete.estrategia;

/**
 * Papel no padrão: STRATEGY.
 *
 * Define a única pergunta que toda forma de entrega precisa saber responder:
 * "para este peso, quanto custa o frete?". Quem usa (o Pedido) conhece apenas
 * este contrato, nunca as implementações.
 */
public interface EstrategiaDeFrete {

    double calcularFrete(double peso);
}
