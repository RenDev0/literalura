package br.com.alura.Literalura.repository;

import br.com.alura.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Boolean existsByNome(String nome);

    Autor findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoFalecimento >= :anoSelecionado AND :anoSelecionado >= a.anoNascimento")
    List<Autor> buscarPorAnoFalecimento(@Param("anoSelecionado") int anoSelecionado);
}
