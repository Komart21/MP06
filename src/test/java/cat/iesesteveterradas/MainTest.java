package cat.iesesteveterradas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MainTest {
    @Test
    public void testReadFileContent() throws IOException {
        // Probar la lectura del archivo usando el método static de la clase Main
        List<String> content = PR210Honor.readFileContent(PR210Honor.obtenirPathFitxer());

        // Verificar que el contenido leído es el esperado
        assertEquals("1. Ser Clar amb els Requeriments: Crear un document de especificació de requisits de software que detalli els requeriments i les especificacions de disseny.", content.get(0));
        assertEquals("2. Procediment de Desenvolupament Apropiat: Utilitzar cicles de vida de desenvolupament de software (SDLC) com Agile o Waterfall per gestionar el flux de treball.", content.get(1));
        // Otras aserciones para cada línea...

        // Verificar el tamaño de la lista (número de líneas en el archivo)
        assertEquals(7, content.size());
    }
}
