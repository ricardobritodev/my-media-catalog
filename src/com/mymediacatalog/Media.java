package com.mymediacatalog;

import com.google.gson.annotations.SerializedName;

record Media(
        @SerializedName("Title") String titulo,
        @SerializedName("Year") String ano,  // Mudado para String, pois pode ser range como "2010–"
        @SerializedName("imdbRating") String notaImdb,  // String para tratar "N/A"
        @SerializedName("Plot") String sinopse
) {
    public void exibirDetalhes() {
        System.out.println("Título: " + titulo);
        System.out.println("Ano: " + ano);
        System.out.println("Nota IMDb: " + (notaImdb.equals("N/A") ? "Não disponível" : notaImdb));
        System.out.println("Sinopse: " + sinopse);
    }

    @Override
    public String toString() {
        return "Titulo=" + titulo + "\n" + "Ano de lançamento=" + ano + "\n" + "Nota=" + notaImdb + "\n" + "Sinopse=" + sinopse;
    }
}


