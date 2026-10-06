package br.uepb.map.decorator.executor;

/**
 * Papel no padrão: COMPONENT.
 * Contrato comum ao executor real e a todos os decorators. Como todos
 * "são" QueryExecutor, o cliente não percebe quantas camadas existem.
 */
public interface QueryExecutor {

    void execute(String sql);
}
