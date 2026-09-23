package br.uepb.map.frete.estrategia.internacional;

import br.uepb.map.frete.estrategia.EstrategiaDeFrete;

/**
 * Questão 4, caso 1: países com a MESMA fórmula e valores diferentes.
 * Uma única estratégia, configurada com a tarifa do país de destino.
 */
public class FreteInternacionalPorPais implements EstrategiaDeFrete {

    private final TarifaPais tarifa;

    public FreteInternacionalPorPais(TarifaPais tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public double calcularFrete(double peso) {
        return tarifa.taxaImportacao() + tarifa.valorPorKg() * peso;
    }

    public String getPais() {
        return tarifa.pais();
    }
}
