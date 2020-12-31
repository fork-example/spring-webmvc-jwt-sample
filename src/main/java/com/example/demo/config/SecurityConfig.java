package com.example.demo.config;

import com.example.demo.config.component.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/public/**")
//            new AntPathRequestMatcher("/vehicles/**"),
//            new AntPathRequestMatcher("/v1/vehicles/**")
    );
    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);


    private final TokenProvider tokenProvider;
    private final TokenAuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;

//    public SecurityConfig(TokenProvider tokenProvider, TokenAuthenticationProvider authenticationProvider) {
//        this.tokenProvider = tokenProvider;
//        this.authenticationProvider = authenticationProvider;
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().requestMatchers(PUBLIC_URLS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/public/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/vehicles/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/vehicles/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/v1/vehicles/**").permitAll()
                .requestMatchers(PROTECTED_URLS).authenticated()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(forbiddenEntryPoint())
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class);
        //@formatter:on
    }

    @Bean
    public TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
        final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS, tokenProvider, userDetailsService);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return filter;
    }
    @Bean
    public FilterRegistrationBean<TokenAuthenticationFilter> disableAutoRegistration(final TokenAuthenticationFilter filter) {
        final FilterRegistrationBean<TokenAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint forbiddenEntryPoint() {
//        return new InvalidAuthenticationEntryPoint();
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }
}

