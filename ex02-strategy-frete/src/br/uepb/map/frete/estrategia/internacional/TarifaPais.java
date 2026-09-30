package br.uepb.map.frete.estrategia.internacional;

/**
 * Dados de tarifação de um país de destino.
 * Quando os países diferem apenas nos valores, a diferença é DADO, não código.
 */
public record TarifaPais(String pais, double taxaImportacao, double valorPorKg) {
}
