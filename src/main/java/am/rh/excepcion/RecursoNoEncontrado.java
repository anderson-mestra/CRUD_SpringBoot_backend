package am.rh.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecursoNoEncontrado extends RuntimeException{

    //Constructor para manejar la excepcion en caso de que no se encuentre algun recurso

    public RecursoNoEncontrado(String mensaje){
        super(mensaje);
    }
}
