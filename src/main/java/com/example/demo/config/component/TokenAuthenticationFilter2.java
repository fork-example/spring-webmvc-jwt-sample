//package com.example.demo.config.component;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//public class TokenAuthenticationFilter2 extends GenericFilterBean {
//
//    private final TokenProvider tokenProvider;
//
//    public TokenAuthenticationFilter2(TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
//        throws IOException, ServletException {
//
//        String token = tokenProvider.resolveToken((HttpServletRequest) req);
//        if (token != null && tokenProvider.validateToken(token)) {
//            Authentication auth = tokenProvider.getAuthentication(token);
//
//            if (auth != null) {
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//        filterChain.doFilter(req, res);
//    }
//
//}
