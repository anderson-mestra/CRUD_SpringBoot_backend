package am.rh.controlador;

import am.rh.excepcion.RecursoNoEncontrado;
import am.rh.modelo.Empleado;
import am.rh.repositorio.servicio.IEmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("rh-app")
@RestController
@CrossOrigin(origins = "*")
public class EmpleadoControlador {

    @Autowired

    private IEmpleadoServicio empleadoServicio;

    //Funcion para traer la lista de empleados en la DB
    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados(){
        var empleados = empleadoServicio.listarEmpleados();
        return empleados;
    }

    //Funcion para guardar los registros en la BD
    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado){
        return empleadoServicio.guardarEmpleado(empleado);
    }

    //Funcion para buscar un registro por el ID
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado>
        obtenerEmpleadoPorId(@PathVariable Integer id){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        //En caso de que no se obtenga ningun empleado se enviara una respuesta
        if (empleado == null)
            throw new RecursoNoEncontrado("No se encontro el empleado con el id: " + id);
        return ResponseEntity.ok(empleado);
    }

    //Funcion para editar un registro empleado en la BD
    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado>
    actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado datosEmpleado){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
         if (empleado == null)
             throw new RecursoNoEncontrado("No se encontro el empleado con el id: " + id);
         empleado.setNombre(datosEmpleado.getNombre());
         empleado.setDepartamento(datosEmpleado.getDepartamento());
         empleado.setSueldo(datosEmpleado.getSueldo());
         empleadoServicio.guardarEmpleado(empleado);
         return ResponseEntity.ok(empleado);
    }

    //Funcion para eliminar un registro
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarEmpleado(@PathVariable Integer id){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontrado("No se encontro el empleado con el id: " + id);
        empleadoServicio.eliminarEmpleado(empleado);

        //Cuando se elimina devolveremos un mensaje para indicar que se hizo la eliminacion del registro
        Map<String, Boolean> respuesta= new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
} 
