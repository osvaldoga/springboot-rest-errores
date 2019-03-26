package com.osvaldoga.errorhandler.services;

import com.osvaldoga.errorhandler.dto.Vehiculo;
import com.osvaldoga.errorhandler.ex.ServerGenericException;
import com.osvaldoga.errorhandler.ex.GenericNotFoundException;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class VehiculoService {
    private List<Vehiculo> vehiculos;

    public VehiculoService() {
        vehiculos = new LinkedList<>();
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void ingresarVehiculo(Vehiculo vehiculo) throws ServerGenericException {

        for (Vehiculo ve : vehiculos) {
            if (ve.getPatente().equalsIgnoreCase(vehiculo.getPatente())) {
                throw new ServerGenericException("Vehiculo ya se encuenta ingresado ["+ vehiculo.getPatente() +"]");
            }
        }

        vehiculos.add(vehiculo);
    }

    public Vehiculo getVehiculo(String patente) throws GenericNotFoundException {
        Vehiculo vehiculo = null;

        for (Vehiculo ve : vehiculos) {
            if (ve.getPatente().equalsIgnoreCase(patente)) {
                vehiculo = ve;
                break;
            }
        }

        if (vehiculo == null) {
            throw new GenericNotFoundException("No se encuentra el vehículo con patente ["+ patente +"]");
        }

        return vehiculo;
    }

    public Vehiculo actualizarVehiculo(Vehiculo vehiculo, String patente) throws GenericNotFoundException {
        Vehiculo v = null;

        for (Vehiculo ve : vehiculos) {
            if (ve.getPatente().equalsIgnoreCase(patente)) {
                ve.setColor(vehiculo.getColor());
                ve.setMarca(vehiculo.getMarca());
                ve.setModelo(vehiculo.getModelo());
                ve.setPatente(vehiculo.getPatente());
                v = ve;
                break;
            }
        }

        if (v == null) {
            throw new GenericNotFoundException("No existe vehiculo ["+ patente +"] para actualizar");
        }

        return v;
    }

    public Vehiculo eliminarVehiculo(String patente) throws GenericNotFoundException {
        Vehiculo v = null;

        for (Vehiculo ve : vehiculos) {
            if (ve.getPatente().equalsIgnoreCase(patente)) {
                v = ve;
                break;
            }
        }



        if (v == null) {
            throw new GenericNotFoundException("No existe vehiculo ["+ patente +"] para eliminar");
        }

        vehiculos.remove(v);

        return v;
    }


}
