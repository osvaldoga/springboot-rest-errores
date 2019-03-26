* Compilar
```
$ mvn compile package
```

* Ejecutar
```
$ mvn spring-boot:run
```


* UI Swagger
```
http://127.0.0.1:8080/demo/api/swagger-ui.html
```


* Estructura de errores (Error simple)
```json
{
  "timestamp": "2019-03-26 18:38:02",
  "status": 404,
  "message": "Not Found",
  "detail": "No se encuentra el vehículo con patente [12312]",
  "path": "/demo/api/vehiculos/12312",
  "trace": null,
  "errores": null
}
```

* Estructura de errores (Error Detalle)
```json
{
  "timestamp": "2019-03-26 18:47:01",
  "status": 400,
  "message": "Bad Request",
  "detail": "Validation failed for argument [0] in org.springframework.http.ResponseEntity<java.lang.String> com.osvaldoga.errorhandler.controller.VehiculoController.ingresarVehiculo(com.osvaldoga.errorhandler.dto.Vehiculo) throws com.osvaldoga.errorhandler.ex.ServerGenericException: [Field error in object 'vehiculo' on field 'patente': rejected value [null]; codes [NotNull.vehiculo.patente,NotNull.patente,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [vehiculo.patente,patente]; arguments []; default message [patente]]; default message [Patente es requerido]] ",
  "path": "/demo/api/vehiculos",
  "trace": null,
  "errores": [
    {
      "object": "vehiculo",
      "field": "patente",
      "rejectedValue": null,
      "message": "Patente es requerido"
    }
  ]
}
```



