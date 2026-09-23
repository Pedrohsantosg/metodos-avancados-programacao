package br.uepb.map.frete.promocao;

import br.uepb.map.frete.estrategia.EstrategiaDeFrete;

/**
 * Desafio extra: padrão DECORATOR aplicado sobre as estratégias.
 *
 * Uma promoção É uma EstrategiaDeFrete (por isso o Pedido a aceita) e TEM uma
 * EstrategiaDeFrete (a modalidade real de entrega). Ela pede o valor à
 * modalidade embrulhada e depois aplica o seu ajuste. Nenhuma modalidade
 * existente precisa saber que promoções existem.
 */
public abstract class FreteComPromocao implements EstrategiaDeFrete {

    private final EstrategiaDeFrete modalidade;

    protected FreteComPromocao(EstrategiaDeFrete modalidade) {
        if (modalidade == null) {
            throw new IllegalArgumentException("A promoção precisa de uma modalidade de frete");
        }
        this.modalidade = modalidade;
    }

    @Override
    public final double calcularFrete(double peso) {
        double valorSemPromocao = modalidade.calcularFrete(peso);
        return aplicarPromocao(valorSemPromocao);
    }

    /** Cada promoção define apenas como ajustar o valor já calculado. */
    protected abstract double aplicarPromocao(double valorSemPromocao);
}
