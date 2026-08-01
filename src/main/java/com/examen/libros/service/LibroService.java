package com.examen.libros.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.examen.libros.dto.EstadisticasDTO;
import com.examen.libros.exception.LibroNoEncontradoException;
import com.examen.libros.exception.ValidacionException;
import com.examen.libros.model.Libro;
import com.examen.libros.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public Libro obtenerPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new LibroNoEncontradoException(id));
    }

    public Libro crear(Libro libro) {
        validarDatos(libro);
        return libroRepository.save(libro);
    }

    public Libro actualizar(Long id, Libro datos) {
        validarDatos(datos);
        Libro existente = obtenerPorId(id);
        existente.setTitulo(datos.getTitulo());
        existente.setAutor(datos.getAutor());
        existente.setCantidadInventario(datos.getCantidadInventario());
        existente.setPrecioUnitario(datos.getPrecioUnitario());
        return libroRepository.save(existente);
    }

    public void eliminar(Long id) {
        Libro existente = obtenerPorId(id);
        libroRepository.delete(existente);
    }

    public EstadisticasDTO calcularEstadisticas() {
        List<Libro> libros = libroRepository.findAll();
        long total = libros.size();
        BigDecimal sumaPrecios = BigDecimal.ZERO;
        long disponibles = 0;
        long agotados = 0;

        for (Libro libro : libros) {
            sumaPrecios = sumaPrecios.add(libro.getPrecioUnitario());
            if (libro.getCantidadInventario() > 0) {
                disponibles++;
            } else {
                agotados++;
            }
        }

        BigDecimal promedio = total > 0
                ? sumaPrecios.divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return new EstadisticasDTO(total, promedio, disponibles, agotados);
    }

    private void validarDatos(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new ValidacionException("El título es obligatorio");
        }
        if (libro.getAutor() == null || libro.getAutor().isBlank()) {
            throw new ValidacionException("El autor es obligatorio");
        }
        if (libro.getPrecioUnitario() == null) {
            throw new ValidacionException("El precio unitario es obligatorio");
        } else if (libro.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("El precio unitario debe ser mayor a 0");
        }
        if (libro.getCantidadInventario() < 0) {
            throw new ValidacionException("La cantidad en inventario no puede ser negativa");
        }
    }
}
