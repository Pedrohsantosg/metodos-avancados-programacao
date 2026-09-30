package br.uepb.map.observer.observadores;

import br.uepb.map.observer.contrato.Observer;
import br.uepb.map.observer.pedido.Pedido;

/** Observador concreto. Simula o aplicativo do entregador. */
public class EntregadorApp implements Observer {

    @Override
    public void update(Pedido pedido) {
        System.out.println("Entregador recebeu atualização: Pedido está " + pedido.getStatus());
    }
}
