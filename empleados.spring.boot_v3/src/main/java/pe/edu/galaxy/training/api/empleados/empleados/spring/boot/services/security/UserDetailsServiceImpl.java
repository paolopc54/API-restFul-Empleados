package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static java.util.Collections.emptyList;

import lombok.Data;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.security.Usuario;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.repository.security.UsuarioRepository;

//Implementamos la inteface de spring security -> UserDetailsService
@Data
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario=this.getUsuarioRepository().loadUserByUsername(username).orElse(null);
		if (usuario==null) {
			throw new UsernameNotFoundException("Usuario no existe");
		}
		User user = new User(usuario.getUsuario(), usuario.getClave(), emptyList());

		return user;
	}
}
