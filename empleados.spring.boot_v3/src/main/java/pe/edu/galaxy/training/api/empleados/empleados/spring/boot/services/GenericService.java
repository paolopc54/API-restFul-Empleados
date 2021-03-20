package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services;

import java.util.List;
import java.util.Optional;

import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.exception.ServiceException;

public interface GenericService<T> {

	List<T> getAll() throws ServiceException;
	
	List<T> findByLike(T t) throws ServiceException;
	
	Optional<T> findById(Long id) throws ServiceException;
	
	T insert(T t) throws ServiceException;
	
	T update(T t) throws ServiceException;
	
	T delete(T t) throws ServiceException;
}
