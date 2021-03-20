package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller;

import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_CONSULTAR_ALERTA;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_CONSULTAR_EXITO;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_CONSULTAR_ERROR;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_INSERTAR_ERROR;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_INSERTAR_OK;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_INSERTAR_ALERT;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_UPDATE_OK;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_UPDATE_ALERT;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_UPDATE_ERROR;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_DELETE_OK;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_DELETE_ALERT;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.constantes.ResponseConst.MSG_DELETE_ERROR;

import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.enums.ResponseEnum.ALERTA;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.enums.ResponseEnum.EXITO;
import static pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.enums.ResponseEnum.ERROR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import lombok.Data;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.common.ObjectResponse;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.UsuarioService;


@Controller
@Data
public class GenericController {

	@Autowired
	private UsuarioService usuarioService;
	
	// tratamiendo personalizado para mostrar mensajes d error en el postman
	protected String formatMapMessage(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}

		).collect(Collectors.toList());
		return errors.toString();
	}

	// response empleado
	protected ResponseEntity<ObjectResponse> getNotFound() {

		ObjectResponse objResponse = ObjectResponse.builder().codigo(ALERTA).mensaje(MSG_CONSULTAR_ALERTA).data(null)
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objResponse);
	}

	protected ResponseEntity<ObjectResponse> getOK(Object obj) {

		ObjectResponse objResponse = ObjectResponse.builder().codigo(EXITO).mensaje(MSG_CONSULTAR_EXITO).data(obj)
				.build();
		return ResponseEntity.ok(objResponse);
	}

	protected ResponseEntity<ObjectResponse> getError() {
		ObjectResponse objResponse = ObjectResponse.builder().codigo(ERROR).mensaje(MSG_CONSULTAR_ERROR).data(null)
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objResponse);
	}
	
	protected ResponseEntity<ObjectResponse> getError(String msg) {
		ObjectResponse objResponse = ObjectResponse.builder().codigo(ERROR).mensaje(msg).data(null).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objResponse);
	}
	
	//***********************************************INSERTAR
	protected ResponseEntity<ObjectResponse> insertError() {
		ObjectResponse objResponse = ObjectResponse.builder().codigo(ERROR).mensaje(MSG_INSERTAR_ERROR).data(null)
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objResponse);
	}
	
	protected ResponseEntity<ObjectResponse> insertAlert(Object obj) {
		ObjectResponse objResponse = ObjectResponse.builder().codigo(ALERTA).mensaje(MSG_INSERTAR_ALERT).data(obj).build();
		//ObjectResponse objResponse = ObjectResponse.builder().codigo(ALERTA).mensaje(MSG_INSERTAR_ALERT).data(null).build();
		return ResponseEntity.ok(objResponse);
	}
	
	protected ResponseEntity<ObjectResponse> insertOK(Object obj) {

		ObjectResponse objResponse = ObjectResponse.builder().codigo(EXITO).mensaje(MSG_INSERTAR_OK).data(obj)
				.build();
		return ResponseEntity.ok(objResponse);
	}
	
	//*******************************************UPDATE
	protected ResponseEntity<ObjectResponse> updateOK(Object obj) {

		ObjectResponse objResponse = ObjectResponse.builder().codigo(EXITO).mensaje(MSG_UPDATE_OK).data(obj)
				.build();
		return ResponseEntity.ok(objResponse);
	}
	
	protected ResponseEntity<ObjectResponse> updateAlert(Object obj) {
		ObjectResponse objResponse = ObjectResponse.builder().codigo(ALERTA).mensaje(MSG_UPDATE_ALERT).data(obj).build();
		//ObjectResponse objResponse = ObjectResponse.builder().codigo(ALERTA).mensaje(MSG_UPDATE_ALERT).data(null).build();
		return ResponseEntity.ok(objResponse);
	}
	
	protected ResponseEntity<ObjectResponse> updateError() {
		ObjectResponse objResponse = ObjectResponse.builder().codigo(ERROR).mensaje(MSG_UPDATE_ERROR).data(null)
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objResponse);
	}
	
	//********************************************DELETE
	protected ResponseEntity<ObjectResponse> deleteOK(Object obj) {

		ObjectResponse objResponse = ObjectResponse.builder().codigo(EXITO).mensaje(MSG_DELETE_OK).data(obj)
				.build();
		return ResponseEntity.ok(objResponse);
	}
	
	protected ResponseEntity<ObjectResponse> deleteAlert() {
		//ObjectResponse objResponse = ObjectResponse.builder().codigo(ALERTA).mensaje(MSG_INSERTAR_ALERT).data(obj).build();
		ObjectResponse objResponse = ObjectResponse.builder().codigo(ALERTA).mensaje(MSG_DELETE_ALERT).data(null).build();
		return ResponseEntity.ok(objResponse);
	}
	
	protected ResponseEntity<ObjectResponse> deleteError() {
		ObjectResponse objResponse = ObjectResponse.builder().codigo(ERROR).mensaje(MSG_DELETE_ERROR).data(null)
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objResponse);
	}
}
