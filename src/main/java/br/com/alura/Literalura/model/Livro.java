package br.com.alura.Literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;

    private String titulo;

    @ManyToOne
    private Autor autor;

    private String idioma;
    private Long downloads;

    public Livro() {
    }

    public Livro(DadosLivro data) {
        this.titulo = data.titulo();
        this.idioma = String.join(",", data.idioma());
        this.downloads = data.downloads();
    }

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "\n------- LIVRO -------" +
                "\nTítulo: " + titulo +
                " \nAutor: " + autor +
                " \nIdioma: " + idioma +
                " \nDownloads: " + downloads;
    }
}