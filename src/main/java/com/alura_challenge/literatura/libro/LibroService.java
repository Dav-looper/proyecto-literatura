package com.alura_challenge.literatura.libro;

import com.alura_challenge.literatura.autor.Autor;
import com.alura_challenge.literatura.autor.AutorRepository;
import com.alura_challenge.literatura.services.ConsumoApi;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoApi consumoApi;

    @Autowired
    public LibroService(LibroRepository libroRepository,
                        AutorRepository autorRepository,
                        ConsumoApi consumoApi) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.consumoApi = consumoApi;
    }

    @Transactional
    public Libro buscarYGuardarLibro(String titulo) {
        List<DatosLibro> librosApi = consumoApi.buscarPorNombre(titulo);

        if (librosApi.isEmpty()) {
            throw new IllegalArgumentException("Libro no encontrado en la API");
        }

        DatosLibro datosLibro = librosApi.get(0);

        if (datosLibro.autores() == null || datosLibro.autores().isEmpty()) {
            throw new IllegalStateException("El libro no tiene autores válidos");
        }

        return libroRepository.save(convertirALibro(datosLibro));
    }

    private Libro convertirALibro(DatosLibro datosLibro) {
        // Convertir lista de DatosAutor a lista de Autor
        List<Autor> autores = datosLibro.autores().stream()
                .map(da -> {
                    Optional<Autor> autorExistente = autorRepository.findByNombre(da.nombre());
                    return autorExistente.orElseGet(() -> {
                        Autor nuevoAutor = new Autor(
                                da.nombre(),
                                da.fechaNacimiento(),
                                da.fechaFallecimiento()
                        );
                        return autorRepository.save(nuevoAutor);
                    });
                })
                .collect(Collectors.toList());

        String idioma = datosLibro.idioma().isEmpty() ? null : datosLibro.idioma().get(0);

        return new Libro(
                datosLibro.id(),
                datosLibro.titulo(),
                idioma,
                autores
        );
    }

    @Transactional
    public List<Libro> listarTodosLosLibros() {
        return libroRepository.findAllWithAutores(); // Usa el nuevo método
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Libro> listarPorIdioma(String idioma) {
        return libroRepository.findByIdiomaIgnoreCase(idioma);
    }

    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }
}



