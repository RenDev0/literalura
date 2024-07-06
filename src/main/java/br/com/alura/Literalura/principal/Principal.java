package br.com.alura.Literalura.principal;

import br.com.alura.Literalura.model.Autor;
import br.com.alura.Literalura.model.DadosAutor;
import br.com.alura.Literalura.model.DadosLivro;
import br.com.alura.Literalura.model.Livro;
import br.com.alura.Literalura.repository.AutorRepository;
import br.com.alura.Literalura.repository.LivroRepository;
import br.com.alura.Literalura.service.ConsumoApi;
import br.com.alura.Literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    private Scanner input = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    ConverteDados converteDados = new ConverteDados();
    private final String ENDERECO = "http://gutendex.com/books/?search=";

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    @Autowired
    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        int opcao = -1;

        while (opcao != 0) {
            String menu = """
                    \n-----------------Literalura-----------------
                    
                    1 - Buscar por título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em determinado idioma
                    
                    0 - Sair""";

            System.out.println(menu);
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresPorAno();
                    break;
                case 5:
                    listarPorIdioma();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivro() {
        System.out.println("Nome do livro: ");
        String nomeLivro = input.nextLine();
        String json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        salvarNoDb(json);
    }

    private void salvarNoDb(String dados) {
        try {
            Autor autorDb = null;
            Livro livroDb = null;

            Livro livro = new Livro(converteDados.obterDados(dados, DadosLivro.class));
            Autor autor = new Autor(converteDados.obterDados(dados, DadosAutor.class));

            if (!autorRepository.existsByNome(autor.getNome())) {
                autorRepository.save(autor);
                autorDb = autor;
            } else {
                autorDb = autorRepository.findByNome(autor.getNome());
            }
            if (!livroRepository.existsByTitulo(livro.getTitulo())) {
                livro.setAutor(autorDb);
                livroRepository.save(livro);
                livroDb = livro;
            } else {
                livroDb = livroRepository.findByTitulo(livro.getTitulo());
            }
            System.out.println(livroDb);
        } catch (NullPointerException e) {
            System.out.println("Livro não encontrado!");
        }
    }

    private void listarLivros() {
        livroRepository.findAll().forEach(System.out::println);
    }

    private void listarAutores() {
        var buscaDb = autorRepository.findAll();
        buscaDb.forEach(System.out::println);
    }

    private void listarAutoresPorAno() {
        System.out.println("Digite o ano: ");
        var anoSelecionado = input.nextInt();
        input.nextLine();

        var buscaAutoresNoDb = autorRepository.buscarPorAnoFalecimento(anoSelecionado);

        if (!buscaAutoresNoDb.isEmpty()) {
            buscaAutoresNoDb.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor encontrado.");
        }
    }

    private void listarPorIdioma() {
        var idiomasCadastrados = livroRepository.buscarIdiomas();
        System.out.println("Idiomas cadastrados: ");
        idiomasCadastrados.forEach(System.out::println);

        System.out.println("Escolha um dos idiomas:");
        var idiomaSelecionado = input.nextLine().trim();

        livroRepository.buscarPorIdioma(idiomaSelecionado).forEach(System.out::println);
    }

}
