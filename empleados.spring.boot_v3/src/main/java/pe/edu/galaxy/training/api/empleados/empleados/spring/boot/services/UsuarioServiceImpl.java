package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.security.Usuario;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.repository.security.UsuarioRepository;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.exception.ServiceException;

@Slf4j
@Data
@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Usuario> getAll() throws ServiceException {
		try {
			return this.getUsuarioRepository().findAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Usuario insert(Usuario usuario) throws ServiceException{
		try {
			return this.getUsuarioRepository().save(usuario);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Usuario update(Usuario usuario) throws ServiceException{
		try {
			Usuario oUsuario= this.getUsuarioRepository().findById(usuario.getId()).orElse(null);
			if (oUsuario==null) {
				return null;
			}
			
			BeanUtils.copyProperties(usuario, oUsuario);
			
			return this.getUsuarioRepository().save(oUsuario);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Usuario delete(Usuario usuario) throws ServiceException{
		try {
			Usuario oUsuario= this.getUsuarioRepository().findById(usuario.getId()).orElse(null);
			if (oUsuario==null) {
				return null;
			}
			oUsuario.setEstado("0");
			return this.getUsuarioRepository().save(oUsuario);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<Usuario> findById(Long id) throws ServiceException{
		try {
			return this.getUsuarioRepository().findById(id);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Usuario> findByLike(Usuario t) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
