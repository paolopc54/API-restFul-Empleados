package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Empleado")
@Table(name = "EMPLEADO")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Empleado extends GenericEntity {

	@Id
	@Column(name = "ID_EMPLEADO")
	//para generar la secuencia del ID xq este dato lo traigo de la base de datos
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEmpleado")
	@SequenceGenerator(name = "seqEmpleado", allocationSize = 1, sequenceName = "SEQ_EMPLEADO")
	private Long id;
	
	@NotNull(message = "Nombres no puede estar en vacío")
	@Column(name = "NOMBRES")
	private String nombres;
	
	@NotNull(message = "Rol no puede estar en vacío")
	@Column(name = "ROL")
	private String rol;
}
