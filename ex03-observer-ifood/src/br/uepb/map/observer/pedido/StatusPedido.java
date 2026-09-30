package br.uepb.map.observer.pedido;

/** Etapas pelas quais um pedido de delivery passa, na ordem em que acontecem. */
public enum StatusPedido {
    RECEBIDO,
    PREPARANDO,
    SAIU_PARA_ENTREGA,
    ENTREGUE
}
