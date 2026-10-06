package br.uepb.map.decorator.decorators;

import br.uepb.map.decorator.executor.QueryExecutor;

/**
 * Papel no padrão: DECORATOR (base abstrata).
 *
 * - implementa QueryExecutor (por isso pode ocupar o lugar de qualquer executor);
 * - guarda uma referência para o próximo executor da cadeia;
 * - por padrão, apenas delega. Cada subclasse acrescenta o seu comportamento
 *   antes e/ou depois de chamar super.execute(sql).
 */
public abstract class QueryExecutorDecorator implements QueryExecutor {

    private final QueryExecutor proximo;

    protected QueryExecutorDecorator(QueryExecutor proximo) {
        if (proximo == null) {
            throw new IllegalArgumentException("O decorator precisa de um executor para encapsular");
        }
        this.proximo = proximo;
    }

    @Override
    public void execute(String sql) {
        proximo.execute(sql);
    }
}
