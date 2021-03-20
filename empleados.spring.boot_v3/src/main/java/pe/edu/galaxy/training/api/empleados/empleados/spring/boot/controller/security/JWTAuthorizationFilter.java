package pe.edu.galaxy.training.api.empleados.empleados.spring.boot.controller.security;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  public JWTAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
	  
   System.out.println("doFilterInternal...x");
   
    String header = req.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
    
    if (header == null || !header.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }
    
    System.out.println("doFilterInternal...y");
    
    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    chain.doFilter(req, res);
  }
  

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	  
    String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
    
    if (token != null) {
      byte signingKey[] = Constants.SUPER_SECRET_KEY.getBytes();
      // Se procesa el token y se recupera el usuario.
      String user = Jwts.parser()
            .setSigningKey(signingKey)
            .parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
            .getBody()
            .getSubject();

      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
      }
      return null;
    }
    return null;
  }
}