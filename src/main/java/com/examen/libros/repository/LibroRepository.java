package com.examen.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examen.libros.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
