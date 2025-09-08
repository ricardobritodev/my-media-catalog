package com.mymediacatalog;

import java.io.InputStream;
import java.net.http.*;
import  java.net.URI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class OmdbService {
    private static final String API_URL = "https://www.omdbapi.com/?t=%s&apikey=%s";
    private static final String CONFIG_FILE = "config.properties";
    private final String apiKey;

    // Contrutor que carrega a apikey
    public OmdbService() throws IOException {
        this.apiKey = carregarApiKey();
    }

    private String carregarApiKey() throws IOException {
        Properties props = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get(CONFIG_FILE))) {
            props.load(input);
            String apiKey = props.getProperty("omdb.api.key");
            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new IOException("Chave da API não encontrada em " + CONFIG_FILE);
            }
            return apiKey;
        }
    }

    // Consulta API
    public String buscarDadosApi(String tituloBusca) throws IOException, InterruptedException {
        String url = String.format(API_URL, tituloBusca.replace(" ", "+"), apiKey);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na API: Código " + response.statusCode());
        }
        if (response.body().contains("\"Response\":\"False\"")) {
            throw new RuntimeException("Mídia não encontrada na API.");
        }

        return response.body(); // Retorna JSON como string
    }
}
