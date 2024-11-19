package cl.inacap.authjwt.jwtauth.Auth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // Importando RequestMapping para mapear solicitudes web
import org.springframework.web.bind.annotation.RestController; // Importando RestController para definir un servicio web RESTful

import lombok.RequiredArgsConstructor; // Importando RequiredArgsConstructor para generar un constructor con argumentos requeridos


@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/auth") // Mapea las solicitudes HTTP a /auth a este controlador
@RequiredArgsConstructor // Genera un constructor con argumentos requeridos (campos finales)
public class AuthController {

    private final AuthService authService; // Inyección de dependencia de AuthService

    @PostMapping(value = "login") // Mapea las solicitudes HTTP POST a /auth/login a este método
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) // Método para manejar solicitudes de inicio de sesión
    {
        return ResponseEntity.ok(authService.login(request)); // Llama al método login de AuthService y devuelve la respuesta
    }

    @PostMapping(value = "register") // Mapea las solicitudes HTTP POST a /auth/register a este método
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) // Método para manejar solicitudes de registro
    {
        return ResponseEntity.ok(authService.register(request)); // Llama al método register de AuthService y devuelve la respuesta
    }
}