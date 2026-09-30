package br.uepb.map.frete.pedido;

import br.uepb.map.frete.estrategia.EstrategiaDeFrete;

/**
 * Papel no padrão: CONTEXT.
 *
 * O pedido guarda uma referência para a interface EstrategiaDeFrete e delega
 * a ela o cálculo. Não existe nenhum if/else sobre o tipo de entrega aqui,
 * e esta classe não importa nenhuma estratégia concreta.
 */
public class Pedido {

    private final String codigo;
    private final double peso;
    private final double valorDosProdutos;
    private EstrategiaDeFrete estrategia;

    public Pedido(String codigo, double peso, double valorDosProdutos, EstrategiaDeFrete estrategia) {
        if (peso <= 0) {
            throw new IllegalArgumentException("O peso do pedido deve ser positivo");
        }
        if (valorDosProdutos < 0) {
            throw new IllegalArgumentException("O valor dos produtos não pode ser negativo");
        }
        this.codigo = codigo;
        this.peso = peso;
        this.valorDosProdutos = valorDosProdutos;
        definirEstrategia(estrategia);
    }

    /** Permite trocar a forma de entrega com o programa em execução. */
    public void definirEstrategia(EstrategiaDeFrete novaEstrategia) {
        if (novaEstrategia == null) {
            throw new IllegalArgumentException("Informe uma estratégia de frete");
        }
        this.estrategia = novaEstrategia;
    }

    public double calcularFrete() {
        return estrategia.calcularFrete(peso);
    }

    public double calcularTotal() {
        return valorDosProdutos + calcularFrete();
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPeso() {
        return peso;
    }

    public double getValorDosProdutos() {
        return valorDosProdutos;
    }
}
