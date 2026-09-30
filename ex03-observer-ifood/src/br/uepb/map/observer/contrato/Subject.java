package br.uepb.map.observer.contrato;

/**
 * Papel no padrão: SUBJECT (o "observado").
 * Quem implementa esta interface mantém uma lista de interessados
 * e avisa todos eles quando o seu estado muda.
 */
public interface Subject {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
