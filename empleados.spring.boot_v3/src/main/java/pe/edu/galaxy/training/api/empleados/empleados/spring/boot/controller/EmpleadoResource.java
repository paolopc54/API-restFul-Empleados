package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.Empleado;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Data
@EqualsAndHashCode(callSuper=false)
@Relation(collectionRelation = "empleados", itemRelation = "empleado")
public class EmpleadoResource extends RepresentationModel<EmpleadoResource>{

	private final Empleado empleado;

	public EmpleadoResource(final Empleado xEmpleado) {
		this.empleado = xEmpleado;
		add(linkTo(EmpleadoController.class).withRel("empleados"));
		add(linkTo(methodOn(EmpleadoController.class).buscarXId(xEmpleado.getId())).withSelfRel());
		
	}
}
