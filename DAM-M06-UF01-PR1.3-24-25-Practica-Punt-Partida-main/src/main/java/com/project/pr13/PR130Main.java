package com.project.pr13;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe principal que gestiona la lectura i el processament de fitxers XML per obtenir dades de persones.
 * 
 * Aquesta classe s'encarrega de llegir un fitxer XML que conté informació de persones,
 * processar-lo i mostrar les dades formatades per consola.
 */
public class PR130Main {

    private final File dataDir;

    /**
     * Constructor de la classe PR130Main.
     * 
     * @param dataDir Directori on es troben els fitxers de dades.
     */
    public PR130Main(File dataDir) {
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

        PR130Main app = new PR130Main(dataDir);
        app.processarFitxerXML("persones.xml");
    }

    /**
     * Processa un fitxer XML per obtenir la informació de les persones i imprimir-la.
     * 
     * @param filename Nom del fitxer XML a processar.
     */
    public void processarFitxerXML(String filename) {
        File inputFile = new File(dataDir, filename);
        Document doc = parseXML(inputFile);
        if (doc != null) {
            NodeList persones = doc.getElementsByTagName("persona");
            imprimirCapçaleres();
            imprimirDadesPersones(persones);
        }
    }

    /**
     * Llegeix un fitxer XML i el converteix en un objecte Document.
     * 
     * @param inputFile Fitxer XML a llegir.
     * @return Document XML carregat o null si hi ha hagut un error en la lectura.
     */
    public static Document parseXML(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Imprimeix les capçaleres de la taula.
     */
    private void imprimirCapçaleres() {
        System.out.printf("%-10s %-12s %-5s %-10s\n", "Nom", "Cognom", "Edat", "Ciutat");
        System.out.println("-------- ------------ ----- ----------");
    }

    /**
     * Imprimeix les dades de les persones formatades en columnes.
     * 
     * @param persones 
     */
    private void imprimirDadesPersones(NodeList persones) {
        for (int i = 0; i < persones.getLength(); i++) {
            Node personaNode = persones.item(i);
            if (personaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element persona = (Element) personaNode;

                String nom = persona.getElementsByTagName("nom").item(0).getTextContent();
                String cognom = persona.getElementsByTagName("cognom").item(0).getTextContent();
                String edat = persona.getElementsByTagName("edat").item(0).getTextContent();
                String ciutat = persona.getElementsByTagName("ciutat").item(0).getTextContent();

                System.out.printf("%-10s %-12s %-5s %-10s\n", nom, cognom, edat, ciutat);
            }
        }
    }
}
