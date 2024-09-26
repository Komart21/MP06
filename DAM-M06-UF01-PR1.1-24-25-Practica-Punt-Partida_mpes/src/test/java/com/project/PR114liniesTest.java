package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PR114liniesTest {

    @TempDir
    Path directoriTemporal;

    @Test
    void testGenerarNumerosAleatoris() throws IOException {
        // Definir el camí del fitxer dins del directori temporal
        File fitxer = new File(directoriTemporal.toFile(), "numeros.txt");

        PR114linies.generarNumerosAleatoris(fitxer.getPath());

        // Comprovar que el fitxer existeix
        assertTrue(fitxer.exists(), "El fitxer hauria d'existir");

        try (BufferedReader reader = new BufferedReader(new FileReader(fitxer, StandardCharsets.UTF_8))) {
            List<String> contingut = reader.lines().toList();

            // Comprovem les 10 linies
            assertEquals(10, contingut.size(), "El fitxer hauria de tenir exactament 10 línies.");

            for (String linia : contingut) {
                try {
                    Integer.parseInt(linia);  
                } catch (NumberFormatException e) {
                    fail("Cada línia hauria de contenir un número enter.");
                }
            }
        }

        // Comprovar que l'última línia no acaba amb un salt de línia
        try (BufferedReader reader = new BufferedReader(new FileReader(fitxer, StandardCharsets.UTF_8))) {
            String ultimaLinia = null;
            String linia;
            while ((linia = reader.readLine()) != null) {
                ultimaLinia = linia;
            }
            assertNotNull(ultimaLinia, "L'última línia no hauria de ser nul·la.");
            assertFalse(ultimaLinia.endsWith("\n"), "L'última línia no hauria de tenir un salt de línia al final.");
        }
    }
}
