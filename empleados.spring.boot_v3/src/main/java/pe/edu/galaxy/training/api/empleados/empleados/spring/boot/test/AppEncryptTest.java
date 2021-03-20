package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppEncryptTest {

	public static void main(String[] args) {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		System.out.println(bCryptPasswordEncoder.encode("123"));
	}
}
