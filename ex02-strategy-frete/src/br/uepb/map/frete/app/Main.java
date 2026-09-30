package br.uepb.map.frete.app;

import br.uepb.map.frete.estrategia.*;
import br.uepb.map.frete.estrategia.internacional.FreteInternacionalPorPais;
import br.uepb.map.frete.estrategia.internacional.FreteMercosul;
import br.uepb.map.frete.estrategia.internacional.TabelaTarifasInternacionais;
import br.uepb.map.frete.pedido.Pedido;
import br.uepb.map.frete.promocao.CupomDeFrete;
import br.uepb.map.frete.promocao.FreteGratisAPartirDe;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class Main {

    private static final NumberFormat REAL = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    public static void main(String[] args) {

        // Itens 4 e 5: um único pedido de 10 kg, trocando a estratégia em tempo de execução
        Pedido pedido = new Pedido("PED-001", 10, 250.00, new Sedex());

        Map<String, EstrategiaDeFrete> modalidades = new LinkedHashMap<>();
        modalidades.put("Sedex", new Sedex());
        modalidades.put("PAC", new PAC());
        modalidades.put("Retirada na loja", new RetiradaNaLoja());
        modalidades.put("Transportadora Expressa", new TransportadoraExpressa());
        modalidades.put("Internacional (regra nova)", new FreteInternacional());

        titulo("Pedido " + pedido.getCodigo() + " | 10 kg | produtos " + moeda(pedido.getValorDosProdutos()));
        for (Map.Entry<String, EstrategiaDeFrete> modalidade : modalidades.entrySet()) {
            pedido.definirEstrategia(modalidade.getValue());
            linha(modalidade.getKey(), pedido.calcularFrete());
        }

        // Questão 4: regras por país
        titulo("Internacional por destino (valores ilustrativos)");
        for (String pais : new String[] {"EUA", "PORTUGAL", "JAPAO"}) {
            pedido.definirEstrategia(new FreteInternacionalPorPais(TabelaTarifasInternacionais.buscar(pais)));
            linha(pais, pedido.calcularFrete());
        }
        pedido.definirEstrategia(new FreteMercosul());
        linha("MERCOSUL (fórmula própria)", pedido.calcularFrete());

        // Desafio extra: promoções como Decorator
        titulo("Promoções sobre as modalidades existentes");
        Pedido pedidoPequeno = new Pedido("PED-002", 10, 250.00, new Sedex());
        pedidoPequeno.definirEstrategia(new FreteGratisAPartirDe(new Sedex(), 399.00, pedidoPequeno.getValorDosProdutos()));
        linha("Sedex + grátis a partir de R$ 399 (compra R$ 250)", pedidoPequeno.calcularFrete());

        Pedido pedidoGrande = new Pedido("PED-003", 10, 520.00, new PAC());
        pedidoGrande.definirEstrategia(new FreteGratisAPartirDe(new PAC(), 399.00, pedidoGrande.getValorDosProdutos()));
        linha("PAC + grátis a partir de R$ 399 (compra R$ 520)", pedidoGrande.calcularFrete());

        pedidoPequeno.definirEstrategia(new CupomDeFrete(new TransportadoraExpressa(), 30.00));
        linha("Transportadora + cupom de R$ 30", pedidoPequeno.calcularFrete());

        pedidoPequeno.definirEstrategia(
                new CupomDeFrete(new FreteGratisAPartirDe(new FreteInternacional(), 399.00, 250.00), 45.00));
        linha("Internacional + grátis R$ 399 + cupom R$ 45", pedidoPequeno.calcularFrete());

        System.out.println();
        System.out.println("Total do PED-002 com a última estratégia: " + moeda(pedidoPequeno.calcularTotal()));
    }

    private static String moeda(double valor) {
        return REAL.format(valor).replace('\u00A0', ' ');
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println(texto);
        System.out.println("-".repeat(65));
    }

    private static void linha(String descricao, double valor) {
        System.out.printf("%-52s %12s%n", descricao, moeda(valor));
    }
}
