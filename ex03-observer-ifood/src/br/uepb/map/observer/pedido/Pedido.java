package br.uepb.map.observer.pedido;

import br.uepb.map.observer.contrato.Observer;
import br.uepb.map.observer.contrato.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Papel no padrão: SUBJECT CONCRETO.
 *
 * O pedido não sabe quem são o cliente, o restaurante ou o entregador:
 * conhece apenas a interface Observer. Por isso é possível incluir um novo
 * interessado (ex.: um painel de suporte) sem alterar esta classe.
 */
public class Pedido implements Subject {

    private final List<Observer> observadores = new ArrayList<>();
    private StatusPedido status = StatusPedido.RECEBIDO;

    @Override
    public void registerObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observador não pode ser nulo");
        }
        if (!observadores.contains(observer)) { // evita notificação em dobro
            observadores.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observadores.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // Percorre uma cópia: se um observador se remover durante o aviso,
        // a iteração não quebra (evita ConcurrentModificationException).
        for (Observer observer : List.copyOf(observadores)) {
            observer.update(this);
        }
    }

    /** 1) atualiza o status; 2) avisa todos os observadores. */
    public void setStatus(StatusPedido status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        this.status = status;
        notifyObservers();
    }

    public StatusPedido getStatus() {
        return status;
    }

    public int quantidadeDeObservadores() {
        return observadores.size();
    }
}
