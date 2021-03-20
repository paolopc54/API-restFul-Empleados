package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.Empleado;

//EN JAVA SI SOPORTA HERENCIA(EXTENDS) MÚLTIPLE EN INTERFACES, MAS NÓ A NIVEL DE CLASES
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>,
											PagingAndSortingRepository<Empleado, Long> {
	
	//JPA REPOSITORY
	@Query("Select e from Empleado e where e.estado='1'")
	public List<Empleado> getAllCustom();
	
	//devuelve una lista de empleados
	@Query("Select e from Empleado e where upper(e.nombres) like :nombres and e.estado='1'")  
	public List<Empleado> findByLikeNombres(@Param("nombres") String nombres);
	
	//devuelve un solo empleado
	@Query("Select e from Empleado e where e.nombres=:nombres")  //JPQL <> SQL
	public Optional<Empleado> findByNombres(@Param("nombres") String nombres);
	
	//PagingAndSortingRepository
	//devuelve una pagina de empleados activos
	@Query("SELECT e FROM Empleado e where e.estado='1'")
	public Page<Empleado> findAllActivos(Pageable pageable);
	
	//devuelve una pagina de empleados activos segun el valor buscado por nombre
	//FALTA USAR EN EMPLEADO SERVICE Y EMPLEADO SERVICE IMPLEMENTE
	@Query("SELECT e FROM Empleado e where upper(e.nombres) like :nombres and e.estado='1'")
	public Page<Empleado> findAllActivosXNombres(@Param("nombres") String nombres, Pageable pageable);
	 
}
