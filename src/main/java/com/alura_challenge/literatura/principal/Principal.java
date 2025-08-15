package com.alura_challenge.literatura.principal;

import com.alura_challenge.literatura.autor.AutorService;
import com.alura_challenge.literatura.libro.Libro;
import com.alura_challenge.literatura.libro.LibroService;
import com.alura_challenge.literatura.services.ConsumoApi;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    private final LibroService libroService;
    private final AutorService autorService; // Nuevo servicio para autores
    private final Scanner scanner = new Scanner(System.in);

    public Principal(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void mostrarMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nElija la opción a través de su número:");
            System.out.println("1- Buscar libro por título");
            System.out.println("2- Listar libros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos en un determinado año");
            System.out.println("5- Listar libros por idioma");
            System.out.println("6- Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosEnAnio();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> {
                    continuar = false;
                    System.out.println("¡Hasta luego!");
                }
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Ingrese título: ");
        String titulo = scanner.nextLine();

        try {
            Libro libro = libroService.buscarYGuardarLibro(titulo);
            System.out.println("✅ Libro registrado: " + libro.getTitulo());
        } catch (IllegalStateException e) {
            System.err.println("⚠️  Error en datos de la API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
        }
    }

    private void listarLibrosRegistrados() {
        libroService.listarTodosLosLibros().forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autorService.listarTodos().forEach(System.out::println);
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("Ingrese el año: ");
        int anio = scanner.nextInt();
        autorService.listarAutoresVivosEnAnio(anio).forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Ingrese el idioma (ej: es, en, fr): ");
        String idioma = scanner.nextLine();
        libroService.listarPorIdioma(idioma).forEach(System.out::println);
    }
}

