package br.uepb.map.decorator.app;

import br.uepb.map.decorator.decorators.AuditDecorator;
import br.uepb.map.decorator.decorators.LoggingDecorator;
import br.uepb.map.decorator.decorators.MetricsDecorator;
import br.uepb.map.decorator.decorators.SecurityValidationDecorator;
import br.uepb.map.decorator.executor.QueryExecutor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Conferência automática, sem bibliotecas externas. Usa um executor "espião"
 * no lugar do BasicQueryExecutor para saber se a query chegou ao banco.
 */
public class Verificacao {

    private static int falhas = 0;

    /** Executor falso: só anota o que recebeu. */
    static class ExecutorEspiao implements QueryExecutor {
        final List<String> recebidas = new ArrayList<>();

        @Override
        public void execute(String sql) {
            recebidas.add(sql);
        }
    }

    public static void main(String[] args) {
        // 1. Segurança: o que passa e o que é bloqueado
        String[][] casos = {
                {"SELECT * FROM users", "passa"},
                {"DROP TABLE users; --", "bloqueia"},
                {"SELECT * FROM users WHERE name = 'admin' OR '1'='1'", "bloqueia"},
                {"select * from users where name = '' or '1' = '1'", "bloqueia"},
                {"drop table users", "bloqueia"},
                {"DELETE FROM users", "bloqueia"},
                {"TRUNCATE users", "bloqueia"},
                {"ALTER TABLE users ADD x INT", "bloqueia"},
                {"SELECT * FROM users -- comentário", "bloqueia"},
                {"SELECT 1; SELECT 2", "bloqueia"},
                {"SELECT dropdown, altered_at FROM ui", "passa"},
        };
        for (String[] caso : casos) {
            ExecutorEspiao espiao = new ExecutorEspiao();
            capturar(() -> new SecurityValidationDecorator(espiao).execute(caso[0]));
            boolean chegou = !espiao.recebidas.isEmpty();
            boolean ok = caso[1].equals("passa") == chegou;
            conferir(caso[1] + ": " + caso[0], ok);
        }

        // 2. Cadeia completa: ordem das mensagens
        ExecutorEspiao espiao = new ExecutorEspiao();
        QueryExecutor cadeia = new LoggingDecorator(new AuditDecorator(
                new MetricsDecorator(new SecurityValidationDecorator(espiao)), "luciana"));
        List<String> linhas = capturar(() -> cadeia.execute("DROP TABLE users; --"));
        conferir("Cadeia: LOG vem primeiro", linhas.get(0).startsWith("[LOG]"));
        conferir("Cadeia: AUDIT vem em seguida, com o usuário", linhas.get(1).equals("[AUDIT] Usuário responsável: luciana"));
        conferir("Cadeia: SECURITY bloqueia", linhas.get(2).startsWith("[SECURITY]"));
        conferir("Cadeia: METRICS mede mesmo quando bloqueia", linhas.get(3).startsWith("[METRICS]"));
        conferir("Cadeia: executor básico não foi chamado", espiao.recebidas.isEmpty());

        // 3. Construção inválida
        conferirErro("Decorator sem executor é recusado", () -> new LoggingDecorator(null));
        conferirErro("Auditoria sem usuário é recusada", () -> new AuditDecorator(new ExecutorEspiao(), " "));

        System.out.println(falhas == 0 ? "\nTodas as verificações passaram." : "\n" + falhas + " verificação(ões) falharam.");
        if (falhas > 0) {
            System.exit(1);
        }
    }

    private static List<String> capturar(Runnable acao) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            acao.run();
        } finally {
            System.setOut(original);
        }
        String texto = buffer.toString(StandardCharsets.UTF_8).strip();
        return texto.isEmpty() ? List.of() : List.of(texto.split("\\R"));
    }

    private static void conferir(String caso, boolean ok) {
        if (!ok) {
            falhas++;
        }
        System.out.printf("[%s] %s%n", ok ? " OK " : "FALHOU", caso);
    }

    private static void conferirErro(String caso, Runnable acao) {
        try {
            acao.run();
            conferir(caso, false);
        } catch (IllegalArgumentException e) {
            conferir(caso, true);
        }
    }
}
