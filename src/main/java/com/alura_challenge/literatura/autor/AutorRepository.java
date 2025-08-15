package com.alura_challenge.literatura.autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE (a.fechaNacimiento <= :anio AND (a.fechaFallecimiento >= :anio OR a.fechaFallecimiento IS NULL))")
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqualOrFechaFallecimientoIsNull(@Param("anio") int anio);

    Optional<Autor> findByNombre(String nombre);
}