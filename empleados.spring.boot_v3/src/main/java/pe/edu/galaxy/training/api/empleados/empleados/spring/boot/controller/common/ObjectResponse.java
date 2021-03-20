package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.enums.ResponseEnum;


@Data
@Builder
//anotacion q ignora los campos q contienen null
@JsonInclude(Include.NON_NULL)
public class ObjectResponse {

	private ResponseEnum codigo;
	private String mensaje;
	
	@Builder.Default
	private Date fecha = new Date();
	
	private Object data;
}
