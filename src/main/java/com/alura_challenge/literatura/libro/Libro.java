package com.alura_challenge.literatura.libro;

import com.alura_challenge.literatura.autor.Autor;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @Column(name = "libro_id")
    private Long id;
    @Column(name = "libro_titulo", nullable = false)
    private String titulo;
    @Column(name = "idioma", length = 2, nullable = true)
    private String idioma;
    @ManyToMany(cascade = CascadeType.PERSIST) // ¡Esto es crucial!
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    public Libro() {}

    public Libro(Long id, String titulo, String idioma, List<Autor> autores) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
    }


    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autores=" + autores +
                '}';

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
}
