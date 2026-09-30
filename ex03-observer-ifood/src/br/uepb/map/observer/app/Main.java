package br.uepb.map.observer.app;

import br.uepb.map.observer.observadores.ClienteApp;
import br.uepb.map.observer.observadores.EntregadorApp;
import br.uepb.map.observer.observadores.RestaurantePainel;
import br.uepb.map.observer.pedido.Pedido;
import br.uepb.map.observer.pedido.StatusPedido;

/** Roteiro exigido pelo enunciado (item 6). */
public class Main {

    public static void main(String[] args) {
        // 1. Instancia o pedido (Subject)
        Pedido pedido = new Pedido();

        // 2. Instancia os observadores
        ClienteApp cliente = new ClienteApp();
        RestaurantePainel restaurante = new RestaurantePainel();
        EntregadorApp entregador = new EntregadorApp();

        // 3. Registra todos no pedido
        pedido.registerObserver(cliente);
        pedido.registerObserver(restaurante);
        pedido.registerObserver(entregador);

        // 4. Cada mudança de status dispara as notificações automaticamente
        pedido.setStatus(StatusPedido.PREPARANDO);
        pedido.setStatus(StatusPedido.SAIU_PARA_ENTREGA);
        pedido.setStatus(StatusPedido.ENTREGUE);
    }
}
