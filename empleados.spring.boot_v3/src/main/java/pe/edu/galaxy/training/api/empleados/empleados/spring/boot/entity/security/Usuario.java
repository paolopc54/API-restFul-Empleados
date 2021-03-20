package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.GenericEntity;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Usuario")
@Table(name = "USUARIO")
@NoArgsConstructor
public class Usuario extends GenericEntity{

	@Positive
	@Id
	@Column(name = "ID_USUARIO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUsuario")
	@SequenceGenerator(name = "seqUsuario", allocationSize = 1, sequenceName = "SEQ_USUARIO")
	private Long id;
	
	@Column(name = "USUARIO")
	private String usuario;
	
	@Column(name = "CLAVE")
	private String clave;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	public Usuario(String usuario,String clave) {
		this.usuario=usuario;
		this.clave=clave;
				
	}
}
