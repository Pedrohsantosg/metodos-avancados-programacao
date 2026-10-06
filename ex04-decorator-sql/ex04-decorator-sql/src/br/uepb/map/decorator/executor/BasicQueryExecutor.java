package br.uepb.map.decorator.executor;

/**
 * Papel no padrão: CONCRETE COMPONENT.
 * O executor original, mantido exatamente como no enunciado: nenhum dos
 * novos requisitos (log, auditoria, métricas, segurança) exigiu alterá-lo.
 */
public class BasicQueryExecutor implements QueryExecutor {

    @Override
    public void execute(String sql) {
        System.out.println("Query executada no banco: " + sql);
    }
}
