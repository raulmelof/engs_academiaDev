package br.com.academiaDev.infrastructure.utils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

class GenericCsvExporterTest {

    @SuppressWarnings("unused")
    static class ItemTeste {
        private final String nome;
        private final int valor;

        public ItemTeste(String nome, int valor) {
            this.nome = nome;
            this.valor = valor;
        }
    }

    @Test
    void deveExportarCsvCorretamente() {
        List<ItemTeste> lista = List.of(
            new ItemTeste("Teste A", 10),
            new ItemTeste("Teste B", 20)
        );

        assertDoesNotThrow(() -> GenericCsvExporter.exportToCsv(lista));
    }
}