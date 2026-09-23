package br.uepb.map.frete.promocao;

import br.uepb.map.frete.estrategia.EstrategiaDeFrete;

/** Cupom que abate um valor fixo do frete, sem deixar o frete negativo. */
public class CupomDeFrete extends FreteComPromocao {

    private final double desconto;

    public CupomDeFrete(EstrategiaDeFrete modalidade, double desconto) {
        super(modalidade);
        this.desconto = desconto;
    }

    @Override
    protected double aplicarPromocao(double valorSemPromocao) {
        return Math.max(0.0, valorSemPromocao - desconto);
    }
}
