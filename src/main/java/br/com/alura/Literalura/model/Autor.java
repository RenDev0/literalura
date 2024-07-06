package br.com.alura.Literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;

    @Column(unique = true)
    private String nome;

    private int anoNascimento;
    private int anoFalecimento;

    public Autor(){}

    public Autor(DadosAutor data) {
        this.nome = data.nome();
        this.anoNascimento = data.anoNascimento();
        this.anoFalecimento = data.anoFalecimento();
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(int anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    @Override
    public String toString() {
        return "\nNome: " + nome +
                "\nAno de nascimento: " + anoNascimento +
                "\nAno de falecimento: " + anoFalecimento;
    }
}