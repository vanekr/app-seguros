package cl.inacap.authjwt.jwtauth.Auth; // Declaración del paquete

import cl.inacap.authjwt.jwtauth.Jwt.JwtService; // Importando JwtService
import cl.inacap.authjwt.jwtauth.User.Role; // Importando Role
import cl.inacap.authjwt.jwtauth.User.User; // Importando User
import cl.inacap.authjwt.jwtauth.User.UserRepository; // Importando UserRepository
import org.springframework.security.authentication.AuthenticationManager; // Importando AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Importando UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails; // Importando UserDetails
import org.springframework.security.crypto.password.PasswordEncoder; // Importando PasswordEncoder
import org.springframework.stereotype.Service; // Importando Service

import lombok.RequiredArgsConstructor; // Importando RequiredArgsConstructor

@Service // Anotación para indicar que esta clase es un servicio
@RequiredArgsConstructor // Genera un constructor con argumentos requeridos (campos finales)
public class AuthService {

    private final UserRepository userRepository; // Inyección de dependencia de UserRepository
    private final JwtService jwtService; // Inyección de dependencia de JwtService
    private final PasswordEncoder passwordEncoder; // Inyección de dependencia de PasswordEncoder
    private final AuthenticationManager authenticationManager; // Inyección de dependencia de AuthenticationManager

    public AuthResponse login(LoginRequest request) {
        // Autenticar al usuario con el AuthenticationManager
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // Obtener los detalles del usuario desde el UserRepository
        UserDetails user = (UserDetails) userRepository.findByUsername(request.getUsername()).orElseThrow();
        // Generar un token JWT para el usuario
        String token = jwtService.getToken(user);
        // Construir y devolver la respuesta de autenticación
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // Crear un nuevo usuario con los datos de la solicitud de registro
        User user = User.builder()
                .username(request.getUsername()) // Establecer el nombre de usuario
                .password(passwordEncoder.encode(request.getPassword())) // Codificar la contraseña
                .firstname(request.getFirstname()) // Establecer el primer nombre
                .lastname(request.lastname) // Establecer el apellido
                .country(request.getCountry()) // Establecer el país
                .role(Role.valueOf(request.role)) // Establecer el rol
                .build();

        // Guardar el usuario en el repositorio
        userRepository.save(user);
        // Devolver la respuesta de autenticación con el token JWT
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}