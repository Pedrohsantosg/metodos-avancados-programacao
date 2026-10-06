package br.uepb.map.decorator.decorators;

import br.uepb.map.decorator.executor.QueryExecutor;

/**
 * Mede o tempo total da operação. O cronômetro ENVOLVE a chamada ao próximo
 * executor: começa antes, para depois. O finally garante a medição mesmo se
 * a camada interna lançar uma exceção.
 */
public class MetricsDecorator extends QueryExecutorDecorator {

    public MetricsDecorator(QueryExecutor proximo) {
        super(proximo);
    }

    @Override
    public void execute(String sql) {
        long inicio = System.nanoTime();
        try {
            super.execute(sql);
        } finally {
            long duracaoMs = (System.nanoTime() - inicio) / 1_000_000;
            System.out.println("[METRICS] Tempo de execução: " + duracaoMs + "ms");
        }
    }
}
