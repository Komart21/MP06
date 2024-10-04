package com.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.objectes.PR121hashmap;

public class PR121mainTest {

    @Test
    public void testEscribirYLeer() {
        // Aquí podrías llamar a los métodos de escribir y leer
        // Asegúrate de que el archivo se crea y se lee correctamente
        PR121mainEscriu.main(null); // Ejecuta el método de escritura
        PR121hashmap result = null;
        try {
            result = PR121mainLlegeix.deserialitzarHashMap();
        } catch (IOFitxerExcepcio e) {
            fail("Error al llegir l'arxiu: " + e.getMessage());
        }
        
        // Realiza afirmaciones sobre el contenido
        assertNotNull(result);
        assertEquals(3, result.getPersones().size());
        assertEquals(Integer.valueOf(25), result.getPersones().get("Anna"));
        assertEquals(Integer.valueOf(30), result.getPersones().get("Bernat"));
        assertEquals(Integer.valueOf(22), result.getPersones().get("Carla"));
    }
}
