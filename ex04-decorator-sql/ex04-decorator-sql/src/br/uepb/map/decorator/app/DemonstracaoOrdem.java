package br.uepb.map.decorator.app;

import br.uepb.map.decorator.decorators.AuditDecorator;
import br.uepb.map.decorator.decorators.LoggingDecorator;
import br.uepb.map.decorator.decorators.MetricsDecorator;
import br.uepb.map.decorator.decorators.SecurityValidationDecorator;
import br.uepb.map.decorator.executor.BasicQueryExecutor;
import br.uepb.map.decorator.executor.QueryExecutor;

/**
 * Extra: a ORDEM das camadas muda o comportamento.
 * Mesmas classes, mesma query perigosa, duas montagens diferentes.
 */
public class DemonstracaoOrdem {

    public static void main(String[] args) {
        String ataque = "SELECT * FROM users WHERE name = 'admin' OR '1'='1'";

        System.out.println("== Segurança por DENTRO (montagem do enunciado) ==");
        QueryExecutor segurancaDentro =
                new LoggingDecorator(new AuditDecorator(
                        new SecurityValidationDecorator(new BasicQueryExecutor()), "luciana"));
        segurancaDentro.execute(ataque);
        System.out.println("-> a tentativa ficou registrada no log e na auditoria.");

        System.out.println();
        System.out.println("== Segurança por FORA ==");
        QueryExecutor segurancaFora =
                new SecurityValidationDecorator(new LoggingDecorator(
                        new AuditDecorator(new BasicQueryExecutor(), "luciana")));
        segurancaFora.execute(ataque);
        System.out.println("-> bloqueou, mas não sobrou rastro de QUEM tentou o ataque.");

        System.out.println();
        System.out.println("== Só métricas, sem log nem auditoria ==");
        new MetricsDecorator(new BasicQueryExecutor()).execute("SELECT 1");
        System.out.println("-> cada comportamento pode ser usado sozinho ou combinado.");
    }
}
