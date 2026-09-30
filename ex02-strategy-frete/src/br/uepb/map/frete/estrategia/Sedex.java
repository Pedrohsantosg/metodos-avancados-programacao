package br.uepb.map.frete.estrategia;

/** Entrega rápida. Tarifa: R$ 20,00 fixos + R$ 5,00 por kg. */
public class Sedex implements EstrategiaDeFrete {

    private static final double TARIFA_FIXA = 20.0;
    private static final double POR_KG = 5.0;

    @Override
    public double calcularFrete(double peso) {
        return TARIFA_FIXA + POR_KG * peso;
    }
}
