package com.alura_challenge.literatura.libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByIdioma(String idioma);

    List<Libro> findByIdiomaIgnoreCase(String idioma);
    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.autores")
    List<Libro> findAllWithAutores();
}
