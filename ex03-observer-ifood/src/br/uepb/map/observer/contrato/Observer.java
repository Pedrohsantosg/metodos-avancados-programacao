package br.uepb.map.observer.contrato;

import br.uepb.map.observer.pedido.Pedido;

/**
 * Papel no padrão: OBSERVER (o "interessado").
 * Recebe o próprio pedido na notificação e consulta nele o que precisar
 * (modelo "pull": o observador puxa a informação do Subject).
 */
public interface Observer {

    void update(Pedido pedido);
}
