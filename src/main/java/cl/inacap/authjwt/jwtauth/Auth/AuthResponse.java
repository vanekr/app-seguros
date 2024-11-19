package cl.inacap.authjwt.jwtauth.Auth; // Declaración del paquete

import lombok.AllArgsConstructor; // Importando AllArgsConstructor para generar un constructor con todos los argumentos
import lombok.Builder; // Importando Builder para el patrón de diseño Builder
import lombok.Data; // Importando Data para generar getters, setters y otros métodos útiles
import lombok.NoArgsConstructor; // Importando NoArgsConstructor para generar un constructor sin argumentos

@Data // Anotación para generar getters, setters, toString, equals y hashCode
@Builder // Anotación para implementar el patrón de diseño Builder
@NoArgsConstructor // Anotación para generar un constructor sin argumentos
@AllArgsConstructor // Anotación para generar un constructor con todos los argumentos
public class AuthResponse { // Declaración de la clase AuthResponse
    String token; // Campo para almacenar el token de autenticación
}