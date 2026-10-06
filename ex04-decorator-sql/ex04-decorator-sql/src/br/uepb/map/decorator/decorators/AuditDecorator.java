package br.uepb.map.decorator.decorators;

import br.uepb.map.decorator.executor.QueryExecutor;

/** Registra o usuário responsável pela tentativa. O usuário é informado no construtor. */
public class AuditDecorator extends QueryExecutorDecorator {

    private final String usuario;

    public AuditDecorator(QueryExecutor proximo, String usuario) {
        super(proximo);
        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("Informe o usuário responsável");
        }
        this.usuario = usuario;
    }

    @Override
    public void execute(String sql) {
        System.out.println("[AUDIT] Usuário responsável: " + usuario);
        super.execute(sql);
    }
}
