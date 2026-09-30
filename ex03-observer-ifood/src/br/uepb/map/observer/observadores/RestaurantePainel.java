package br.uepb.map.observer.observadores;

import br.uepb.map.observer.contrato.Observer;
import br.uepb.map.observer.pedido.Pedido;

/** Observador concreto. Simula o painel da cozinha do restaurante. */
public class RestaurantePainel implements Observer {

    @Override
    public void update(Pedido pedido) {
        System.out.println("Restaurante recebeu atualização: Pedido está " + pedido.getStatus());
    }
}
