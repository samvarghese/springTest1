package com.springboot.study.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


@Component
public class JWTFilter implements Filter {
  public JWTFilter() {

  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	  	
	final HttpServletRequest request = (HttpServletRequest) req;
	final HttpServletResponse response = (HttpServletResponse) res;
	final String authHeader = request.getHeader("Authorization");
	
	String url = ((HttpServletRequest)request).getRequestURI().toString();
	String queryString = ((HttpServletRequest)request).getQueryString();

	if((url.equals("/users/login")) || (url.equals("/users/sign-up"))){
		
		chain.doFilter(req, res);
	}else{
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {

			response.sendError(403, "Missing or invalid Authorization header");
			return;
		}
		
		final String token = authHeader.substring(7);

		try {
			final Claims claims = Jwts.parser()
					.setSigningKey("my super secret key".getBytes("UTF-8"))
					.parseClaimsJws(token).getBody();

			request.setAttribute("claims", claims);
			chain.doFilter(req, res);

		} catch (final JwtException e) {

			response.sendError(403, "Invalid Token");
		}
	}

  }

  public void init(FilterConfig filterConfig) {
  }

  public void destroy() {
  }

}




	