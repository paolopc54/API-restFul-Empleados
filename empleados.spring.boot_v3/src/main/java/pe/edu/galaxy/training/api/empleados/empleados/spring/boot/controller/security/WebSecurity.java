package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import static java.util.Arrays.asList;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {


  private UserDetailsService userDetailsService;

  public WebSecurity(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    /*
     * 1. Se desactiva el uso de cookies
     * 2. Se activa la configuración CORS con los valores por defecto
     * 3. Se desactiva el filtro CSRF
     * 4. Se indica que el login no requiere autenticación
     * 5. Se indica que el resto de URLs esten securizadas
     */
    httpSecurity
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
       .and()
       .cors()
       .and()
       .csrf()
       .disable()
       .authorizeRequests().antMatchers(HttpMethod.POST, Constants.LOGIN_URL).permitAll()
       .anyRequest()
       .authenticated()
       .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager())) // Login
        .addFilter(new JWTAuthorizationFilter(authenticationManager())); // Recurso/Api/Metodos
    
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    // Se define la clase que recupera los usuarios y el algoritmo para procesar las passwords
    System.out.println("configure...");
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
	  
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //para la parte FRONT
    /*
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(asList("*"));
    configuration.setAllowedMethods(asList("HEAD","GET", "POST", "PUT", "DELETE"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(asList("Authorization", "Cache-Control", "Content-Type"));
    source.registerCorsConfiguration("/**", configuration);
    */
    
    //para la parte postman
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}