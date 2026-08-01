package com.examen.libros.exception;

public class LibroNoEncontradoException extends RuntimeException {

    public LibroNoEncontradoException(Long id) {
        super("No existe un libro con el id " + id);
    }
}
