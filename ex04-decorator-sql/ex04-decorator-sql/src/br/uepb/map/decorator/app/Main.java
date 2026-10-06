package br.uepb.map.decorator.app;

import br.uepb.map.decorator.decorators.AuditDecorator;
import br.uepb.map.decorator.decorators.LoggingDecorator;
import br.uepb.map.decorator.decorators.MetricsDecorator;
import br.uepb.map.decorator.decorators.SecurityValidationDecorator;
import br.uepb.map.decorator.executor.BasicQueryExecutor;
import br.uepb.map.decorator.executor.QueryExecutor;

/** Cliente: monta a cadeia exatamente como pede o enunciado e executa os três testes. */
public class Main {

    public static void main(String[] args) {
        QueryExecutor executor =
                new LoggingDecorator(
                        new AuditDecorator(
                                new MetricsDecorator(
                                        new SecurityValidationDecorator(
                                                new BasicQueryExecutor()
                                        )
                                ),
                                "luciana"
                        )
                );

        executor.execute("SELECT * FROM users");
        System.out.println();
        executor.execute("DROP TABLE users; --");
        System.out.println();
        executor.execute("SELECT * FROM users WHERE name = 'admin' OR '1'='1'");
    }
}
