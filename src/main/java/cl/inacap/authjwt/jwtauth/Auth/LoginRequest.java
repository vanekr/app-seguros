package cl.inacap.authjwt.jwtauth.Auth; // Declaración del paquete

import lombok.AllArgsConstructor; // Importando AllArgsConstructor para generar un constructor con todos los argumentos
import lombok.Builder; // Importando Builder para el patrón de diseño Builder
import lombok.Data; // Importando Data para generar getters, setters y otros métodos útiles
import lombok.NoArgsConstructor; // Importando NoArgsConstructor para generar un constructor sin argumentos

@Data // Anotación para generar getters, setters, toString, equals y hashCode
@Builder // Anotación para implementar el patrón de diseño Builder
@AllArgsConstructor // Anotación para generar un constructor con todos los argumentos
@NoArgsConstructor // Anotación para generar un constructor sin argumentos
public class LoginRequest { // Declaración de la clase LoginRequest
    String username; // Campo para almacenar el nombre de usuario
    String password; // Campo para almacenar la contraseña
}