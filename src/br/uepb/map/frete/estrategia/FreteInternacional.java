package br.uepb.map.frete.estrategia;

/**
 * Regra incluída depois (item 5 do enunciado).
 * Tarifa: taxa de importação de R$ 25,00 + R$ 12,00 por kg.
 *
 * Para esta regra existir foi criado só este arquivo; a classe Pedido
 * permaneceu exatamente como estava.
 */
public class FreteInternacional implements EstrategiaDeFrete {

    private static final double TAXA_IMPORTACAO = 25.0;
    private static final double POR_KG = 12.0;

    @Override
    public double calcularFrete(double peso) {
        return TAXA_IMPORTACAO + POR_KG * peso;
    }
}
