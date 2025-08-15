package com.alura_challenge.literatura.autor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaNacimiento,
        @JsonAlias("death_year") Integer fechaFallecimiento
) {
    public DatosAutor {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalStateException("El autor no tiene nombre en la API");
        }
    }
}
