package com.mymediacatalog;

import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyMediaCatalog {
    private static List<Media> favoritos = new ArrayList();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        OmdbService omdbService = new OmdbService();

        boolean executando = true;

        try {
            carregarFavoritos();
        } catch (IOException e) {
            System.out.println("Nenhum favorito salvo anteriormente.");
        }

        System.out.println("Bem-Vindo ao My com.mymediacatalog.Media Catalog!");

        while (executando) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("[1] - Buscar Filme/Serie");
            System.out.println("[2] - Ver Fvoritos");
            System.out.println("[3] - Sair");
            System.out.print("\n>> ");
            String opcao = scanner.nextLine();

            if (opcao.equals("1")) {
                System.out.println("Digite o titulo do filme/serie:");
                String tituloBusca = scanner.nextLine();
                try {
                    String json = omdbService.buscarDadosApi(tituloBusca);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Media media = gson.fromJson(json, Media.class); // Desserializa JSON para record com.mymediacatalog.Media
                    if (media.titulo() == null) {
                        System.out.println("Midia não encontrada.");
                        continue;
                    }
                    media.exibirDetalhes();
                    System.out.println("Adicionar aos favoritos? (s/n)");
                    if (scanner.nextLine().equalsIgnoreCase("s")) {
                        favoritos.add(media);
                        salvarFavoritos();
                        System.out.println("Adicionado aos favoritos!");
                    }
                } catch (IOException | InterruptedException | RuntimeException e) {
                    System.out.println("ERRO ao buscar: " + e.getMessage());
                }
            } else if (opcao.equals("2")) {
                if (favoritos.isEmpty()) {
                    System.out.println("Nenhum favorito salvo.");
                } else {
                    System.out.println("Favoritos:");
                    for (Media m : favoritos) {
                        m.exibirDetalhes();
                        System.out.println("---");
                    }
                }
            } else if (opcao.equals("3")) {
                executando = false;
                System.out.println("Saindo..");
            } else {
                System.out.println("Opção invalida.. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void salvarFavoritos() throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Media m : favoritos) {
            linhas.add(m.titulo() + ";" + m.ano() + ";" + m.notaImdb() + ";" + m.sinopse()); // Serializa simples
        }
        Files.write(Paths.get("favoritos.txt"), linhas); // Grava no arquivo de texto
    }

    private static void carregarFavoritos() throws  IOException {
        List<String> linhas = Files.readAllLines(Paths.get("favoritos.txt"));
        for (String linha : linhas) {
            String[] partes = linha.split(";", 4);
            if (partes.length == 4) {
                Media m = new Media(partes[0], partes[1], partes[2], partes[3]);
                favoritos.add(m);
            }
        }
    }
}
