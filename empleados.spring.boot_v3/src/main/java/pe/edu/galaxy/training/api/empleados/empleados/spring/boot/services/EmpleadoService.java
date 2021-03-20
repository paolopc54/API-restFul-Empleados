package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services;

import java.util.List;
import java.util.Optional;

import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.Empleado;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.exception.ServiceException;

public interface EmpleadoService extends GenericService<Empleado> {

	List<Empleado> findByLikeNombres(String nombres) throws ServiceException;
	
	Optional<Empleado> findByNombres(String nombres) throws ServiceException;
	
	//PAGINADO DE TODOS LOS EMPLEADOS ACTIVOS Y NO ACTIVOS
	//PAGINADO TRAE TODOS LOS EMPLEADOS ACTIVOS
	public List<Empleado> listPagingActivos(int limit, int page) throws ServiceException;
	
	//public List<TallerBean> listPaging(int limit, int page,String orden, String campo) throws ServiceException;

	//PAGINADO TRAE LOS EMPLEADOS ACTIVOS y ORDENADOS
	public List<Empleado> listPagingActivosOrdenados(int limit, int page,String orden, String campo) throws ServiceException;
}
