package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.common.ObjectResponse;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.Empleado;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.EmpleadoService;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@RestController
@RequestMapping("/empleados")
public class EmpleadoController extends GenericController {

	@Autowired
	public EmpleadoService empleadoService;

	@GetMapping("/listar-id")
	public ResponseEntity<ObjectResponse> getAll() {
		try {
			List<Empleado> lst = this.getEmpleadoService().getAll();

			if (lst == null || lst.size() == 0) {
				return super.getNotFound();
			}
			return super.getOK(lst);

		} catch (Exception e) {
			log.error(e.getMessage());
			return super.getError();
		}

	}
	
	//@SuppressWarnings("deprecation")
	//LISTA DE EMPLEADOS CON HATEOAS
	//FALTA DARLE DETALLE CON OBJECT RESPONSE
	@GetMapping
	public ResponseEntity<CollectionModel<EmpleadoResource>> listar() {
		
		try {
			@SuppressWarnings("unchecked")
			List<Empleado> empleados = (List<Empleado>) empleadoService.getAll();

			final List<EmpleadoResource> collection = empleados.stream().map(EmpleadoResource::new).collect(Collectors.toList());

			final CollectionModel<EmpleadoResource> resources = new CollectionModel<>(collection);

			final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
			
			System.out.println("uriString "+uriString);

			resources.add(new Link(uriString, "root"));

			return ResponseEntity.ok(resources);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	//busca por ID y devuelve un solo empleado
	@GetMapping("/listar-id/{id}")
	public ResponseEntity<ObjectResponse> findById(@PathVariable Long id){
		if (id<=0) {
			return ResponseEntity.badRequest().build();
		}
		try {
			Empleado empleado = this.getEmpleadoService().findById(id).orElse(null);
			if (empleado == null) {
				return super.getNotFound();
			}
			return super.getOK(empleado);
		} catch (Exception e) {
			log.error(e.getMessage());
			return super.getError();
		}
	}
	
	//LISTA DE UN SOLO EMPLEADO CON HATEOS
	//FALTA DARLE DETALLES Y VALIDACIONES CON OBJECT RESPONSE
	@GetMapping("/{id}")
	public ResponseEntity<EmpleadoResource> buscarXId(@PathVariable Long id) {
		
		
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}
		try {
			Empleado empleado = new Empleado();
			empleado.setId(id);
			Object obj = this.getEmpleadoService().findById(id).orElse(null);
			if (obj != null) {
				
				return ResponseEntity.ok(new EmpleadoResource((Empleado)obj));
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	//busca por coincidencia de nombres y devuelve una lista
	//FALTA VALIDAR CUANDO DEVUELVE UNA LISTA
	@GetMapping("/by-nombres")
	public ResponseEntity<List<Empleado>> findByLikeRazonSocial(@RequestParam String nombres){
		try {
			log.info("nombres -> " + nombres);
			
			List<Empleado> lst = this.getEmpleadoService().findByLikeNombres(nombres);
			
			if(lst == null || lst.size()==0) {
				return ResponseEntity.noContent().build();
			}
			
			return ResponseEntity.ok(lst);
			 
		} catch (Exception e) {
			log.error(e.getMessage());
			//return super.getError();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping
	public ResponseEntity<ObjectResponse> insert(@RequestBody @Validated Empleado empleado, BindingResult result) {
		try {
			Empleado oEmpleado = this.getEmpleadoService().insert(empleado);
			
			return super.insertOK(oEmpleado);
		} catch (Exception e) {
			log.error(e.getMessage());
			return super.insertError();
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<ObjectResponse> update(@PathVariable Long id, @RequestBody Empleado empleado) {
		try {
			empleado.setId(id);
			Empleado oEmpleado = this.getEmpleadoService().update(empleado);
			if (oEmpleado == null) {
				return super.updateAlert(empleado.getNombres());
			}
			return super.updateOK(oEmpleado);
		} catch (Exception e) {
			log.error(e.getMessage());
			return super.updateError();
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ObjectResponse> delete(@PathVariable Long id) {
		try {
			Empleado empleado = new Empleado();
			empleado.setId(id);
			Empleado oEmpleado = this.getEmpleadoService().delete(empleado);
			if (oEmpleado == null) {
				return super.deleteAlert();
			}
			return super.deleteOK(oEmpleado);
		} catch (Exception e) {
			log.error(e.getMessage());
			return super.deleteError();
		}

	}
	
	//PAGINADOS
	@GetMapping("/pagin-activos")
	public ResponseEntity<ObjectResponse> findAllActivos(@RequestParam int limit, @RequestParam int page) {
		try {
			Object lst = empleadoService.listPagingActivos(limit, page);
			if (lst != null) {
				return super.getOK(lst);
			}
			return super.getNotFound();
		} catch (Exception e) {
			log.error(e.getMessage());
			return super.getError();
		}
	}
	
	//PAGINADO Y CAMPOS
	@GetMapping("/pagin-campo-orden")
	public ResponseEntity<ObjectResponse> findAllCampo(	@RequestParam int limit, 
												@RequestParam int page,
												@RequestParam String orden,
												@RequestParam String campo) {
		try {
			Object lst = empleadoService.listPagingActivosOrdenados(limit, page,orden.toUpperCase(),campo);
			if (lst != null) {
				return super.getOK(lst);
			}
			return super.getNotFound();
		} catch (Exception e) {
			log.error(e.getMessage());
			return super.getError();
		}
	}

}
