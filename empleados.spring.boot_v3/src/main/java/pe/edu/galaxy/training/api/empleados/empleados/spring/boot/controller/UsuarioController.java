package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.common.ObjectResponse;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.security.Usuario;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.UsuarioService;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends GenericController{

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<ObjectResponse> getAll() {
		
		try {
			List<Usuario> lst=this.getUsuarioService().getAll();
			if (lst==null || lst.size()==0) {
				return super.getNotFound();
			}			
			return super.getOK(lst);
		} catch (Exception e) {
			log.error(e.getMessage());
			return super.getError();
		}	
	}
}
