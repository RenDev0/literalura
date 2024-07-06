package br.com.alura.Literalura.repository;

import br.com.alura.Literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTitulo(String titulo);

    Livro findByTitulo(String titulo);

    @Query("SELECT DISTINCT b.idioma FROM Livro b ORDER BY b.idioma")
    List<String> buscarIdiomas();

    @Query("SELECT b FROM Livro b WHERE idioma = :idiomaSelecionado")
    List<Livro> buscarPorIdioma(String idiomaSelecionado);
}
