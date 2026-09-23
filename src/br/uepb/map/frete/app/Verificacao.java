package br.uepb.map.frete.app;

import br.uepb.map.frete.estrategia.*;
import br.uepb.map.frete.estrategia.internacional.FreteMercosul;
import br.uepb.map.frete.pedido.Pedido;
import br.uepb.map.frete.promocao.CupomDeFrete;
import br.uepb.map.frete.promocao.FreteGratisAPartirDe;

/**
 * Conferência automática dos resultados, sem bibliotecas externas.
 * Os quatro primeiros valores esperados são os do código ORIGINAL do enunciado
 * (com if/else): a refatoração não pode mudar o comportamento.
 */
public class Verificacao {

    private static int falhas = 0;

    public static void main(String[] args) {
        Pedido p = new Pedido("TESTE", 10, 250.00, new Sedex());

        conferir("Sedex 10 kg", p.calcularFrete(), 70.0);
        p.definirEstrategia(new PAC());
        conferir("PAC 10 kg", p.calcularFrete(), 30.0);
        p.definirEstrategia(new RetiradaNaLoja());
        conferir("Retirada 10 kg", p.calcularFrete(), 0.0);
        p.definirEstrategia(new TransportadoraExpressa());
        conferir("Transportadora 10 kg", p.calcularFrete(), 120.0);
        p.definirEstrategia(new FreteInternacional());
        conferir("Internacional 10 kg", p.calcularFrete(), 145.0);

        conferir("Mercosul 4 kg (isento)", new FreteMercosul().calcularFrete(4), 32.0);
        conferir("Mercosul 10 kg", new FreteMercosul().calcularFrete(10), 95.0);

        conferir("Grátis: compra abaixo do mínimo", new FreteGratisAPartirDe(new Sedex(), 399, 398.99).calcularFrete(10), 70.0);
        conferir("Grátis: compra igual ao mínimo", new FreteGratisAPartirDe(new Sedex(), 399, 399.00).calcularFrete(10), 0.0);
        conferir("Cupom não deixa frete negativo", new CupomDeFrete(new PAC(), 100).calcularFrete(10), 0.0);

        conferirErro("Estratégia nula é recusada", () -> p.definirEstrategia(null));
        conferirErro("Peso zero é recusado", () -> new Pedido("X", 0, 10, new PAC()));

        System.out.println(falhas == 0 ? "\nTodas as verificações passaram." : "\n" + falhas + " verificação(ões) falharam.");
        if (falhas > 0) {
            System.exit(1);
        }
    }

    private static void conferir(String caso, double obtido, double esperado) {
        boolean ok = Math.abs(obtido - esperado) < 0.001;
        if (!ok) {
            falhas++;
        }
        System.out.printf("[%s] %-36s esperado %7.2f | obtido %7.2f%n", ok ? " OK " : "FALHOU", caso, esperado, obtido);
    }

    private static void conferirErro(String caso, Runnable acao) {
        try {
            acao.run();
            falhas++;
            System.out.printf("[FALHOU] %s%n", caso);
        } catch (IllegalArgumentException esperado) {
            System.out.printf("[ OK ] %s%n", caso);
        }
    }
}
