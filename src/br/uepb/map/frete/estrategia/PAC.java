package br.uepb.map.frete.estrategia;

/** Entrega econômica. Tarifa: R$ 10,00 fixos + R$ 2,00 por kg. */
public class PAC implements EstrategiaDeFrete {

    private static final double TARIFA_FIXA = 10.0;
    private static final double POR_KG = 2.0;

    @Override
    public double calcularFrete(double peso) {
        return TARIFA_FIXA + POR_KG * peso;
    }
}
