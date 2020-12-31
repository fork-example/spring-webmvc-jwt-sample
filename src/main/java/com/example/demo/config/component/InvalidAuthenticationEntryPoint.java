//package com.example.demo.config.component;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//public class InvalidAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException authException) throws IOException, ServletException {
//		log.debug("Jwt authentication failed:" + authException);
//		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//		response.setContentType("application/json");
////		response.getWriter().print(authException.getMessage());
//		response.getOutputStream().print("{ \"error\": \"" + authException.getMessage() + "\" }");
////		response.sendError(
////				HttpServletResponse.SC_UNAUTHORIZED,
////				authException.getMessage()
////		);
//	}
//
//}
