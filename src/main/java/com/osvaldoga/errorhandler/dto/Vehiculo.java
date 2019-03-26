package com.osvaldoga.errorhandler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@ToString
public class Vehiculo {
    @NotNull(message="Patente es requerido")
    @Size(min = 6, max = 6, message = "Patente debe ser de largo 6 caracteres")
    private String patente;
    @NotNull(message="Marca es requerido")
    private String marca;
    @NotNull(message="Modelo es requerido")
    private String modelo;
    @NotNull(message="Color es requerido")
    private String color;
}
