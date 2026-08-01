package com.examen.libros.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.examen.libros.model.Libro;
import com.examen.libros.repository.LibroRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final LibroRepository libroRepository;

    public DataSeeder(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public void run(String... args) {
        if (libroRepository.count() > 0) {
            return;
        }
        libroRepository.save(new Libro("Cien anos de soledad", "Gabriel Garcia Marquez", 10, new BigDecimal("25.50")));
        libroRepository.save(new Libro("El Quijote", "Miguel de Cervantes", 0, new BigDecimal("18.00")));
        libroRepository.save(new Libro("Rayuela", "Julio Cortazar", 3, new BigDecimal("22.00")));
    }
}
