package br.uepb.map.frete.promocao;

import br.uepb.map.frete.estrategia.EstrategiaDeFrete;

/**
 * Frete grátis quando o valor dos PRODUTOS alcança o mínimo (ex.: R$ 399,00).
 *
 * Como a interface do enunciado só recebe o peso, o valor dos produtos é
 * informado na criação da promoção.
 */
public class FreteGratisAPartirDe extends FreteComPromocao {

    private final double valorMinimo;
    private final double valorDosProdutos;

    public FreteGratisAPartirDe(EstrategiaDeFrete modalidade, double valorMinimo, double valorDosProdutos) {
        super(modalidade);
        this.valorMinimo = valorMinimo;
        this.valorDosProdutos = valorDosProdutos;
    }

    @Override
    protected double aplicarPromocao(double valorSemPromocao) {
        return valorDosProdutos >= valorMinimo ? 0.0 : valorSemPromocao;
    }
}
