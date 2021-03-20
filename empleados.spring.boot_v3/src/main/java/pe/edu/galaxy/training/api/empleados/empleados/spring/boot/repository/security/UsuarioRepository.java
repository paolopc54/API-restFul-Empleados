package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.security.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("Select u from Usuario u where u.usuario=:usuario and u.estado='1'")
	public Optional<Usuario> loadUserByUsername(@Param("usuario") String usuario);
}
