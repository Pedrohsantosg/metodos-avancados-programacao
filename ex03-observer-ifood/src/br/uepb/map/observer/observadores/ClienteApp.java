package br.uepb.map.observer.observadores;

import br.uepb.map.observer.contrato.Observer;
import br.uepb.map.observer.pedido.Pedido;

/** Observador concreto. Simula o aplicativo do cliente, que acompanha o pedido. */
public class ClienteApp implements Observer {

    @Override
    public void update(Pedido pedido) {
        System.out.println("Cliente recebeu notificação: Pedido está " + pedido.getStatus());
    }
}
