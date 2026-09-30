package br.uepb.map.observer.app;

import br.uepb.map.observer.observadores.ClienteApp;
import br.uepb.map.observer.pedido.Pedido;
import br.uepb.map.observer.pedido.StatusPedido;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Conferência automática: executa o Main capturando o console e compara,
 * linha a linha, com a saída esperada do enunciado. Sem bibliotecas externas.
 */
public class Verificacao {

    private static int falhas = 0;

    public static void main(String[] args) {
        List<String> esperado = List.of(
                "Cliente recebeu notificação: Pedido está PREPARANDO",
                "Restaurante recebeu atualização: Pedido está PREPARANDO",
                "Entregador recebeu atualização: Pedido está PREPARANDO",
                "Cliente recebeu notificação: Pedido está SAIU_PARA_ENTREGA",
                "Restaurante recebeu atualização: Pedido está SAIU_PARA_ENTREGA",
                "Entregador recebeu atualização: Pedido está SAIU_PARA_ENTREGA",
                "Cliente recebeu notificação: Pedido está ENTREGUE",
                "Restaurante recebeu atualização: Pedido está ENTREGUE",
                "Entregador recebeu atualização: Pedido está ENTREGUE");

        List<String> obtido = capturar(() -> Main.main(new String[0]));
        conferir("Saída do Main igual à do enunciado", obtido.equals(esperado));

        Pedido pedido = new Pedido();
        conferir("Pedido começa como RECEBIDO", pedido.getStatus() == StatusPedido.RECEBIDO);

        ClienteApp cliente = new ClienteApp();
        pedido.registerObserver(cliente);
        pedido.registerObserver(cliente);
        conferir("Mesmo observador não é registrado duas vezes", pedido.quantidadeDeObservadores() == 1);

        pedido.removeObserver(cliente);
        List<String> semNinguem = capturar(() -> pedido.setStatus(StatusPedido.PREPARANDO));
        conferir("Após remover, ninguém é notificado", semNinguem.isEmpty());
        conferir("Status muda mesmo sem observadores", pedido.getStatus() == StatusPedido.PREPARANDO);

        try {
            pedido.registerObserver(null);
            conferir("Observador nulo é recusado", false);
        } catch (IllegalArgumentException e) {
            conferir("Observador nulo é recusado", true);
        }

        System.out.println(falhas == 0 ? "\nTodas as verificações passaram." : "\n" + falhas + " verificação(ões) falharam.");
        if (falhas > 0) {
            System.exit(1);
        }
    }

    private static List<String> capturar(Runnable acao) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            acao.run();
        } finally {
            System.setOut(original);
        }
        String texto = buffer.toString(StandardCharsets.UTF_8).strip();
        return texto.isEmpty() ? List.of() : List.of(texto.split("\\R"));
    }

    private static void conferir(String caso, boolean ok) {
        if (!ok) {
            falhas++;
        }
        System.out.printf("[%s] %s%n", ok ? " OK " : "FALHOU", caso);
    }
}
