package com.project.pr14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.project.objectes.Llibre;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.JsonWriter;

public class PR14GestioLlibreriaJakartaMain {

    private final File dataFile;

    public PR14GestioLlibreriaJakartaMain(File dataFile) {
        this.dataFile = dataFile;
    }

    public static void main(String[] args) {
        File dataFile = new File(System.getProperty("user.dir"), "data/pr14" + File.separator + "llibres_input.json");
        PR14GestioLlibreriaJakartaMain app = new PR14GestioLlibreriaJakartaMain(dataFile);
        app.processarFitxer();
    }

    public void processarFitxer() {
        List<Llibre> llibres = carregarLlibres();
        if (llibres != null) {
            modificarAnyPublicacio(llibres, 1, 1995);
            afegirNouLlibre(llibres, new Llibre(4, "Històries de la ciutat", "Miquel Soler", 2022));
            esborrarLlibre(llibres, 2);
            guardarLlibres(llibres);
        }
    }

    public List<Llibre> carregarLlibres() {
        try (InputStream is = new FileInputStream(dataFile);
             JsonReader reader = Json.createReader(is)) {
            JsonArray jsonArray = reader.readArray();
            List<Llibre> llibres = new ArrayList<>();
            for (JsonValue value : jsonArray) {
                JsonObject jsonObject = (JsonObject) value;
                int id = jsonObject.getInt("id");
                String titol = jsonObject.getString("titol");
                String autor = jsonObject.getString("autor");
                int any = jsonObject.getInt("any");
                llibres.add(new Llibre(id, titol, autor, any));
            }
            return llibres;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void modificarAnyPublicacio(List<Llibre> llibres, int id, int nouAny) {
        for (Llibre llibre : llibres) {
            if (llibre.getId() == id) {
                llibre.setAny(nouAny);
                break;
            }
        }
    }

    public void afegirNouLlibre(List<Llibre> llibres, Llibre nouLlibre) {
        llibres.add(nouLlibre);
    }

    public void esborrarLlibre(List<Llibre> llibres, int id) {
        llibres.removeIf(llibre -> llibre.getId() == id);
    }

    public void guardarLlibres(List<Llibre> llibres) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Llibre llibre : llibres) {
            JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", llibre.getId())
                .add("titol", llibre.getTitol())
                .add("autor", llibre.getAutor())
                .add("any", llibre.getAny())
                .build();
            jsonArrayBuilder.add(jsonObject);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        
        try (Writer writer = new FileWriter("data/pr14" + File.separator + "llibres_output_jakarta.json")) {
            JsonWriter jsonWriter = Json.createWriter(writer);
            jsonWriter.writeArray(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
