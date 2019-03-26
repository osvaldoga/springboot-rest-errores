package com.osvaldoga.errorhandler.controller;

import com.osvaldoga.errorhandler.dto.Vehiculo;
import com.osvaldoga.errorhandler.ex.ServerGenericException;
import com.osvaldoga.errorhandler.ex.GenericNotFoundException;
import com.osvaldoga.errorhandler.services.VehiculoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@Api(description = "Manejo de información de vehículo")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("")
    @ApiOperation("Retorna un listado de vehiculos.")
    ResponseEntity<List<Vehiculo>> listarVehiculos() {
        System.out.println("GET");
        List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    @GetMapping("/{patente}")
    @ApiOperation("Obtiene información de un vehículo como patente como parámetro.")
    ResponseEntity<Vehiculo> consultarVehiculo(@PathVariable("patente") String patente) throws GenericNotFoundException {
        System.out.println("GET Parámetro");
        Vehiculo vehiculo = vehiculoService.getVehiculo(patente);

        return new ResponseEntity<>(vehiculo, HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation("Ingresa un nuevo vehículo.")
    ResponseEntity<String> ingresarVehiculo(@Valid @RequestBody Vehiculo vehiculo) throws ServerGenericException {
        System.out.println("POST");

        vehiculoService.ingresarVehiculo(vehiculo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{patente}")
    @ApiOperation("Actualiza un vehículo.")
    ResponseEntity<Vehiculo> actualizarVehiculo(@Valid @RequestBody Vehiculo vehiculo, @PathVariable("patente") String patente) throws GenericNotFoundException {
        System.out.println("PUT");

        Vehiculo vehiculo1 = vehiculoService.actualizarVehiculo(vehiculo, patente);
        return new ResponseEntity<>(vehiculo1, HttpStatus.OK);
    }

    @DeleteMapping("/{patente}")
    @ApiOperation("Elimina un vehículo.")
    ResponseEntity<Vehiculo> eliminarVehiculo(@PathVariable("patente") String patente) throws GenericNotFoundException {
        System.out.println("DELETE");

        Vehiculo vehiculo = vehiculoService.eliminarVehiculo(patente);
        return new ResponseEntity<>(vehiculo, HttpStatus.OK);
    }

}
