package cl.inacap.authjwt.jwtauth.Config; // Declaración del paquete

import cl.inacap.authjwt.jwtauth.User.UserRepository; // Importando UserRepository
import org.springframework.context.annotation.Bean; // Importando Bean para definir beans de Spring
import org.springframework.context.annotation.Configuration; // Importando Configuration para marcar esta clase como una configuración de Spring
import org.springframework.security.authentication.AuthenticationManager; // Importando AuthenticationManager para manejar la autenticación
import org.springframework.security.authentication.AuthenticationProvider; // Importando AuthenticationProvider para manejar proveedores de autenticación
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Importando DaoAuthenticationProvider para manejar la autenticación basada en DAO
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // Importando AuthenticationConfiguration para manejar la configuración de autenticación
import org.springframework.security.core.userdetails.UserDetailsService; // Importando UserDetailsService para cargar detalles del usuario
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Importando UsernameNotFoundException para manejar excepciones de nombre de usuario no encontrado
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importando BCryptPasswordEncoder para codificar contraseñas
import org.springframework.security.crypto.password.PasswordEncoder; // Importando PasswordEncoder para manejar la codificación de contraseñas

import lombok.RequiredArgsConstructor; // Importando RequiredArgsConstructor para generar un constructor con argumentos requeridos

@Configuration // Anotación para marcar esta clase como una configuración de Spring
@RequiredArgsConstructor // Genera un constructor con argumentos requeridos (campos finales)
public class ApplicationConfig {

    private final UserRepository userRepository; // Inyección de dependencia de UserRepository

    @Bean // Anotación para definir un bean de Spring
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Devuelve el AuthenticationManager de la configuración
    }

    @Bean // Anotación para definir un bean de Spring
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); // Crea un nuevo DaoAuthenticationProvider
        authenticationProvider.setUserDetailsService(userDetailService()); // Establece el UserDetailsService
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Establece el PasswordEncoder
        return authenticationProvider; // Devuelve el AuthenticationProvider
    }

    @Bean // Anotación para definir un bean de Spring
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Devuelve un nuevo BCryptPasswordEncoder
    }

    @Bean // Anotación para definir un bean de Spring
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username) // Busca el usuario por nombre de usuario
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")); // Lanza una excepción si el usuario no se encuentra
    }

}