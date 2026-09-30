package br.uepb.map.observer.app;

import br.uepb.map.observer.observadores.ClienteApp;
import br.uepb.map.observer.observadores.EntregadorApp;
import br.uepb.map.observer.observadores.RestaurantePainel;
import br.uepb.map.observer.pedido.Pedido;
import br.uepb.map.observer.pedido.StatusPedido;

/**
 * Extra: mostra o removeObserver em uso e o desacoplamento em tempo de execução.
 * Depois que o pedido sai da cozinha, o restaurante deixa de acompanhar;
 * a entrada e a saída de interessados não exigem mudança em nenhuma classe.
 */
public class DemonstracaoRemocao {

    public static void main(String[] args) {
        Pedido pedido = new Pedido();
        RestaurantePainel restaurante = new RestaurantePainel();

        pedido.registerObserver(new ClienteApp());
        pedido.registerObserver(restaurante);
        pedido.registerObserver(new EntregadorApp());

        pedido.setStatus(StatusPedido.PREPARANDO);
        pedido.setStatus(StatusPedido.SAIU_PARA_ENTREGA);

        System.out.println("-- restaurante deixa de acompanhar o pedido --");
        pedido.removeObserver(restaurante);

        pedido.setStatus(StatusPedido.ENTREGUE);
        System.out.println("Observadores ativos: " + pedido.quantidadeDeObservadores());

        // Um interessado novo, criado na hora com lambda (Observer tem um único método)
        pedido.registerObserver(p -> System.out.println("Suporte registrou: Pedido está " + p.getStatus()));
        pedido.setStatus(StatusPedido.ENTREGUE);
    }
}
