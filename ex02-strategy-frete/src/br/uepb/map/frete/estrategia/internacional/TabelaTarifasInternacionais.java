package br.uepb.map.frete.estrategia.internacional;

import java.util.Map;

/**
 * Tabela de tarifas por país (valores ilustrativos).
 * Em um sistema real estes dados viriam de um banco ou arquivo de configuração;
 * incluir um país novo seria incluir uma linha, sem criar classe alguma.
 */
public final class TabelaTarifasInternacionais {

    private static final Map<String, TarifaPais> TARIFAS = Map.of(
            "EUA",      new TarifaPais("EUA", 25.0, 12.0),
            "PORTUGAL", new TarifaPais("PORTUGAL", 40.0, 15.0),
            "JAPAO",    new TarifaPais("JAPAO", 60.0, 18.0)
    );

    private TabelaTarifasInternacionais() {
    }

    public static TarifaPais buscar(String pais) {
        TarifaPais tarifa = TARIFAS.get(pais.toUpperCase());
        if (tarifa == null) {
            throw new IllegalArgumentException("Não há tarifa cadastrada para: " + pais);
        }
        return tarifa;
    }
}
