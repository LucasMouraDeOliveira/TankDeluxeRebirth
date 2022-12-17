package com.luma.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.luma.service.user.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

	@Value("${apiPrefix}")
	private String apiPrefix;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthTokenFilter tokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// URL access
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers(HttpMethod.POST, String.format("%s/users/**", apiPrefix)).permitAll()
					.requestMatchers(HttpMethod.POST, String.format("%s/auth/**", apiPrefix)).permitAll()
					.anyRequest().authenticated();
		});

		// Disable CSRF
		http.csrf().disable();

		// Disable CORS
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

		http.headers().frameOptions().sameOrigin();

		// Authentication provider

		http.authenticationProvider(authenticationProvider());

		// JWT Token filter
		http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
