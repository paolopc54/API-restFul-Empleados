package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class GenericEntity {

	@Column(name = "ESTADO")
	protected String estado = "1";	
}
