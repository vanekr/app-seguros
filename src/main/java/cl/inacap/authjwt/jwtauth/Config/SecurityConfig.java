package cl.inacap.authjwt.jwtauth.Config; // Declaración del paquete

import jakarta.servlet.Filter; // Importando Filter para manejar filtros de servlets
import lombok.RequiredArgsConstructor; // Importando RequiredArgsConstructor para generar un constructor con argumentos requeridos
import org.springframework.context.annotation.Bean; // Importando Bean para definir beans de Spring
import org.springframework.context.annotation.Configuration; // Importando Configuration para marcar esta clase como una configuración de Spring

import org.springframework.security.authentication.AuthenticationProvider; // Importando AuthenticationProvider para manejar proveedores de autenticación
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Importando HttpSecurity para configurar la seguridad web
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Importando EnableWebSecurity para habilitar la seguridad web
import org.springframework.security.config.http.SessionCreationPolicy; // Importando SessionCreationPolicy para manejar la política de creación de sesiones

import org.springframework.security.web.SecurityFilterChain; // Importando SecurityFilterChain para manejar la cadena de filtros de seguridad
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Importando UsernamePasswordAuthenticationFilter para manejar la autenticación de usuario y contraseña

@Configuration // Anotación para marcar esta clase como una configuración de Spring
@EnableWebSecurity // Anotación para habilitar la seguridad web
@RequiredArgsConstructor // Genera un constructor con argumentos requeridos (campos finales)
public class SecurityConfig {
    private final Filter jwtAuthenticationFilter; // Filtro de autenticación JWT
    private final AuthenticationProvider authProvider; // Proveedor de autenticación

    @Bean // Anotación para definir un bean de Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF
                .authorizeHttpRequests(authRequest -> // Configura las autorizaciones de solicitudes HTTP
                        authRequest
                                .requestMatchers("/auth/**").permitAll() // Permite todas las solicitudes a /auth/**
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Requiere el rol ADMIN para las solicitudes a /admin/**
                                .requestMatchers("/user/**").hasRole("USER") // Requiere el rol USER para las solicitudes a /user/**
                                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .sessionManagement(sessionManager-> // Configura la gestión de sesiones
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Establece la política de creación de sesiones como sin estado
                .authenticationProvider(authProvider) // Establece el proveedor de autenticación
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añade el filtro de autenticación JWT antes del filtro de autenticación de usuario y contraseña
                .build(); // Construye y devuelve la cadena de filtros de seguridad
    }
}