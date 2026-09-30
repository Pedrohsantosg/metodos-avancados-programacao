package br.uepb.map.frete.estrategia.internacional;

import br.uepb.map.frete.estrategia.EstrategiaDeFrete;

/**
 * Questão 4, caso 2: um destino com FÓRMULA diferente (regra fictícia).
 * Acordo regional: isento da taxa de importação até 5 kg; acima disso
 * cobra R$ 15,00 de taxa. Valor por kg: R$ 8,00.
 *
 * Como a lógica muda, este destino ganha sua própria estratégia.
 */
public class FreteMercosul implements EstrategiaDeFrete {

    private static final double LIMITE_ISENCAO_KG = 5.0;
    private static final double TAXA_ACIMA_DO_LIMITE = 15.0;
    private static final double POR_KG = 8.0;

    @Override
    public double calcularFrete(double peso) {
        double taxa = peso <= LIMITE_ISENCAO_KG ? 0.0 : TAXA_ACIMA_DO_LIMITE;
        return taxa + POR_KG * peso;
    }
}
