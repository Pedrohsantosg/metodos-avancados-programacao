package br.uepb.map.frete.estrategia;

/** Transportadora terceirizada. Tarifa: R$ 50,00 fixos + R$ 7,00 por kg. */
public class TransportadoraExpressa implements EstrategiaDeFrete {

    private static final double TARIFA_FIXA = 50.0;
    private static final double POR_KG = 7.0;

    @Override
    public double calcularFrete(double peso) {
        return TARIFA_FIXA + POR_KG * peso;
    }
}
