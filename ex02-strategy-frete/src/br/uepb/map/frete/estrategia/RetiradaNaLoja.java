package br.uepb.map.frete.estrategia;

/** O cliente busca o produto na loja: não há custo de entrega. */
public class RetiradaNaLoja implements EstrategiaDeFrete {

    @Override
    public double calcularFrete(double peso) {
        return 0.0;
    }
}
