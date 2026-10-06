package br.uepb.map.decorator.decorators;

import br.uepb.map.decorator.executor.QueryExecutor;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Valida a query antes de deixá-la seguir. Se for bloqueada, o próximo
 * executor NÃO é chamado: a cadeia é interrompida aqui.
 *
 * Duas categorias de bloqueio:
 *  - comandos destrutivos: DROP, DELETE, TRUNCATE, ALTER (como palavras inteiras,
 *    sem diferenciar maiúsculas/minúsculas);
 *  - padrões típicos de SQL Injection: ' OR '1'='1 (ignorando espaços e caixa), -- e ;
 *
 * Atenção: lista de bloqueio é uma camada extra, não a proteção principal.
 * A defesa de verdade contra SQL Injection é usar consultas parametrizadas
 * (PreparedStatement). Ver README.
 */
public class SecurityValidationDecorator extends QueryExecutorDecorator {

    private static final Pattern COMANDO_PERIGOSO =
            Pattern.compile("\\b(DROP|DELETE|TRUNCATE|ALTER)\\b", Pattern.CASE_INSENSITIVE);

    /** Padrão já compactado (sem espaços, minúsculo) e como ele é exibido na mensagem. */
    private record PadraoInjecao(String compacto, String exibicao) {
    }

    private static final List<PadraoInjecao> PADROES_DE_INJECAO = List.of(
            new PadraoInjecao("'or'1'='1", "' OR '1'='1"),
            new PadraoInjecao("--", "--"),
            new PadraoInjecao(";", ";"));

    public SecurityValidationDecorator(QueryExecutor proximo) {
        super(proximo);
    }

    @Override
    public void execute(String sql) {
        String motivo = motivoDoBloqueio(sql);
        if (motivo != null) {
            System.out.println("[SECURITY] Query bloqueada por validação de segurança. Motivo: " + motivo + ".");
            return; // não delega: o executor básico nunca recebe esta query
        }
        super.execute(sql);
    }

    /** Devolve o motivo do bloqueio, ou null se a query for considerada segura. */
    static String motivoDoBloqueio(String sql) {
        if (sql == null || sql.isBlank()) {
            return "query vazia";
        }
        var comando = COMANDO_PERIGOSO.matcher(sql);
        if (comando.find()) {
            return "palavra perigosa detectada (" + comando.group(1).toUpperCase(Locale.ROOT) + ")";
        }
        // remove espaços e ignora caixa: "' OR '1' = '1" e "'or'1'='1" são o mesmo ataque
        String compacta = sql.replaceAll("\\s+", "").toLowerCase(Locale.ROOT);
        for (PadraoInjecao padrao : PADROES_DE_INJECAO) {
            if (compacta.contains(padrao.compacto())) {
                return "possível SQL Injection detectado (" + padrao.exibicao() + ")";
            }
        }
        return null;
    }
}
