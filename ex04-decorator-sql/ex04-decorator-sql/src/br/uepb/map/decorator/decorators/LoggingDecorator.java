package br.uepb.map.decorator.decorators;

import br.uepb.map.decorator.executor.QueryExecutor;

/** Registra toda tentativa de execução ANTES de delegar (inclusive as que serão bloqueadas). */
public class LoggingDecorator extends QueryExecutorDecorator {

    public LoggingDecorator(QueryExecutor proximo) {
        super(proximo);
    }

    @Override
    public void execute(String sql) {
        System.out.println("[LOG] Tentando executar: " + sql);
        super.execute(sql);
    }
}
