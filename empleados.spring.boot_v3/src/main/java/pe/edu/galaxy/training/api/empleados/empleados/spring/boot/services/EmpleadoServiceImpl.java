package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.Empleado;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.repository.EmpleadoRepository;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.exception.ServiceException;


@Slf4j
@Data
@Service
public class EmpleadoServiceImpl implements EmpleadoService{
	
	public EmpleadoServiceImpl(){}
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Override
	public List<Empleado> getAll() throws ServiceException {
		try {
			return this.getEmpleadoRepository().getAllCustom();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<Empleado> findById(Long id) throws ServiceException {
		try {
			return this.getEmpleadoRepository().findById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Empleado insert(Empleado empleado) throws ServiceException {
		try {
			//busca al empleado por id si lo encuentre, me muestra un mensaje de encontrado
			Empleado oEmpleado = this.findByNombres(empleado.getNombres()).orElse(null);
			if (oEmpleado!=null) {
				throw new ServiceException("ya hiciste empleado " + oEmpleado.getNombres());
			}
			return  this.getEmpleadoRepository().save(empleado);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Empleado update(Empleado empleado) throws ServiceException {
		try {
			Empleado oEmpleado =  this.getEmpleadoRepository().findById(empleado.getId()).orElse(null);
			
			if (oEmpleado == null) {
				return null;
			}
			
			BeanUtils.copyProperties(empleado, oEmpleado);
			return this.getEmpleadoRepository().save(oEmpleado);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Empleado delete(Empleado empleado) throws ServiceException {
		try {
			Empleado oEmpleado =  this.getEmpleadoRepository().findById(empleado.getId()).orElse(null);
			
			if (oEmpleado == null) {
				return null;
			}
			oEmpleado.setEstado("0");
			return this.getEmpleadoRepository().save(oEmpleado);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Empleado> findByLikeNombres(String nombres) throws ServiceException {
		try {
			nombres = (nombres==null)?"":nombres.toUpperCase();
			return this.getEmpleadoRepository().findByLikeNombres("%"+nombres+"%");
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<Empleado> findByNombres(String nombres) throws ServiceException {
		try {
			return this.getEmpleadoRepository().findByNombres(nombres);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Empleado> findByLike(Empleado t) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	//********************************************************************************************************************************
	//PAGINADOS
	@Override
	public List<Empleado> listPagingActivos(int limit, int page) throws ServiceException {
		
		Pageable pageableRequest = PageRequest.of(page, limit);
	    Page<Empleado> pagEmpleado = empleadoRepository.findAllActivos(pageableRequest);
	    List<Empleado> lstEmpleado = pagEmpleado.getContent();
	    
	    //return this.getAll();
		return this.getLst(lstEmpleado);
	}
	
	@Override
	public List<Empleado> listPagingActivosOrdenados(int limit, int page, String orden, String campo) throws ServiceException {
		
		Pageable pageableRequest = PageRequest.of(page, limit,Sort.Direction.valueOf(orden),campo );
	    Page<Empleado> pagEmpleado = empleadoRepository.findAllActivos(pageableRequest); 
	    List<Empleado> lstEmpleado = pagEmpleado.getContent();
	    
	    
		return this.getLst(lstEmpleado);
	}
	
	//RETORNA UNA LISTA
	private List<Empleado> getLst(List<Empleado> lstEmpleado){
		List<Empleado> olstEmpleado = new ArrayList<Empleado>();

		lstEmpleado.forEach(xEmpleado -> {
			Empleado empleado = new Empleado();
			BeanUtils.copyProperties(xEmpleado, empleado);
			olstEmpleado.add(empleado);
		});
		return olstEmpleado;
	}
	
}
