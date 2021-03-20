package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pe.edu.galaxy.training.api.empleados.empleados.spring.boot.entity.security.Usuario;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	    this.authenticationManager = authenticationManager;
	  }

  /*login*/
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
	  
    try {
    	
      System.out.println("usuario json "+request.getInputStream());
     	
      Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
      
      System.out.println("usuario bean "+usuario);
      
      UsernamePasswordAuthenticationToken upat= new UsernamePasswordAuthenticationToken(
    		  usuario.getUsuario(),
    		  usuario.getClave(),
          new ArrayList<>());
      
      
      System.out.println("upat " +upat);
      
      Authentication aut=authenticationManager.authenticate(upat);
      
      //System.out.println("aut "+aut);
      
      return aut;
      
    } catch (IOException e) {
      System.out.println("attemptAuthentication "+e.getMessage());
      throw new RuntimeException(e);
    }
  }
  

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication auth) throws IOException, ServletException {
	  
    byte signingKey[] = Constants.SUPER_SECRET_KEY.getBytes();
    
    String token = Jwts.builder()
        .setIssuedAt(new Date())
        .setIssuer(Constants.ISSUER_INFO)
        .setSubject(((User)auth.getPrincipal()).getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512,signingKey)
        .compact();
    
    System.out.println("token "+token);
    
    response.addHeader("Access-Control-Expose-Headers", "Authorization");
    
    response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);
    
  }
  
  
}



