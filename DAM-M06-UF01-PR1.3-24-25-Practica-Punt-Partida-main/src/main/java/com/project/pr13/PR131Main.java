package com.project.pr13;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Classe principal que crea un document XML amb informació de llibres i el guarda en un fitxer.
 * 
 * Aquesta classe permet construir un document XML, afegir elements i guardar-lo en un directori
 * especificat per l'usuari.
 */
public class PR131Main {

    private File dataDir;

    /**
     * Constructor de la classe PR131Main.
     * 
     * @param dataDir Directori on es guardaran els fitxers de sortida.
     */
    public PR131Main(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Retorna el directori de dades actual.
     * 
     * @return Directori de dades.
     */
    public File getDataDir() {
        return dataDir;
    }

    /**
     * Actualitza el directori de dades.
     * 
     * @param dataDir Nou directori de dades.
     */
    public void setDataDir(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Mètode principal que inicia l'execució del programa.
     * 
     * @param args Arguments passats a la línia de comandament (no s'utilitzen en aquest programa).
     */
    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");
        File dataDir = new File(userDir, "data" + File.separator + "pr13");

        PR131Main app = new PR131Main(dataDir);
        app.processarFitxerXML("biblioteca.xml");
    }

    /**
     * Processa el document XML creant-lo, guardant-lo en un fitxer i comprovant el directori de sortida.
     * 
     * @param filename Nom del fitxer XML a guardar.
     */
    public void processarFitxerXML(String filename) {
        if (comprovarIDirCrearDirectori(dataDir)) {
            Document doc = construirDocument();
            File fitxerSortida = new File(dataDir, filename);
            guardarDocument(doc, fitxerSortida);
        }
    }

    /**
     * Comprova si el directori existeix i, si no és així, el crea.
     * 
     * @param directori Directori a comprovar o crear.
     * @return True si el directori ja existeix o s'ha creat amb èxit, false en cas contrari.
     */
    private boolean comprovarIDirCrearDirectori(File directori) {
        if (!directori.exists()) {
            return directori.mkdirs();
        }
        return true;
    }

    /**
     * Crea un document XML amb l'estructura d'una biblioteca i afegeix un llibre amb els seus detalls.
     * 
     * @return Document XML creat o null en cas d'error.
     */
    private static Document construirDocument() {
        try {
            // Crear el DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Crear l'element arrel "biblioteca"
            Element arrel = doc.createElement("biblioteca");
            doc.appendChild(arrel);

            // Crear l'element "llibre" amb atribut id="001"
            Element llibre = doc.createElement("llibre");
            llibre.setAttribute("id", "001");
            arrel.appendChild(llibre);

            // Afegir els elements del llibre
            afegirElement(doc, llibre, "titol", "El viatge dels venturons");
            afegirElement(doc, llibre, "autor", "Joan Pla");
            afegirElement(doc, llibre, "anyPublicacio", "1998");
            afegirElement(doc, llibre, "editorial", "Edicions Mar");
            afegirElement(doc, llibre, "genere", "Aventura");
            afegirElement(doc, llibre, "pagines", "320");
            afegirElement(doc, llibre, "disponible", "true");

            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Afegeix un nou element a un element pare dins del document XML.
     * 
     * @param doc Document on afegirem l'element.
     * @param pare Element pare al qual s'afegirà el nou element.
     * @param nom Nom del nou element.
     * @param valor Valor del nou element.
     */
    private static void afegirElement(Document doc, Element pare, String nom, String valor) {
        Element nouElement = doc.createElement(nom);
        nouElement.appendChild(doc.createTextNode(valor));
        pare.appendChild(nouElement);
    }

    /**
     * Guarda el document XML proporcionat en el fitxer especificat.
     * 
     * @param doc Document XML a guardar.
     * @param fitxerSortida Fitxer de sortida on es guardarà el document.
     */
    private static void guardarDocument(Document doc, File fitxerSortida) {
        try {
            // Crear TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Indentar l'XML

            // Crear el fitxer de sortida
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fitxerSortida);

            // Guardar el document
            transformer.transform(source, result);

            System.out.println("Document guardat a: " + fitxerSortida.getAbsolutePath());

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
