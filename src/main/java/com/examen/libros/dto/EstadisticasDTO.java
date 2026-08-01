package com.examen.libros.dto;

import java.math.BigDecimal;

public class EstadisticasDTO {

    private long cantidadTotalLibros;
    private BigDecimal promedioPrecios;
    private long librosDisponibles;
    private long librosAgotados;

    public EstadisticasDTO() {
    }

    public EstadisticasDTO(long cantidadTotalLibros, BigDecimal promedioPrecios,
                           long librosDisponibles, long librosAgotados) {
        this.cantidadTotalLibros = cantidadTotalLibros;
        this.promedioPrecios = promedioPrecios;
        this.librosDisponibles = librosDisponibles;
        this.librosAgotados = librosAgotados;
    }

    public long getCantidadTotalLibros() {
        return cantidadTotalLibros;
    }

    public void setCantidadTotalLibros(long cantidadTotalLibros) {
        this.cantidadTotalLibros = cantidadTotalLibros;
    }

    public BigDecimal getPromedioPrecios() {
        return promedioPrecios;
    }

    public void setPromedioPrecios(BigDecimal promedioPrecios) {
        this.promedioPrecios = promedioPrecios;
    }

    public long getLibrosDisponibles() {
        return librosDisponibles;
    }

    public void setLibrosDisponibles(long librosDisponibles) {
        this.librosDisponibles = librosDisponibles;
    }

    public long getLibrosAgotados() {
        return librosAgotados;
    }

    public void setLibrosAgotados(long librosAgotados) {
        this.librosAgotados = librosAgotados;
    }
}
