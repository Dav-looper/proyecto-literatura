package com.alura_challenge.literatura.services;

import com.alura_challenge.literatura.libro.DatosLibro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
public class ConsumoApi {

    public List<DatosLibro> consumir() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://gutendex.com/books/"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode resultsNode = root.get("results");
            DatosLibro[] libros = mapper.readValue(resultsNode.toString(), DatosLibro[].class);
            return Arrays.asList(libros);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public List<DatosLibro> buscarPorNombre(String nombre) {
        try {
            String url = "https://gutendex.com/books/?search=" + URLEncoder.encode(nombre, StandardCharsets.UTF_8);
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode results = root.get("results");

            return Arrays.asList(mapper.treeToValue(results, DatosLibro[].class));
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar libros: " + e.getMessage());
        }
    }
}
