package cl.inacap.authjwt.jwtauth.Demo; // Declaración del paquete

import cl.inacap.authjwt.jwtauth.User.User; // Importando la clase User
import cl.inacap.authjwt.jwtauth.User.UserRepository; // Importando la interfaz UserRepository
import org.springframework.beans.factory.annotation.Autowired; // Importando Autowired para la inyección de dependencias
import org.springframework.security.access.prepost.PreAuthorize; // Importando PreAuthorize para la autorización previa
import org.springframework.web.bind.annotation.*; // Importando anotaciones de Spring para controladores web

import java.util.List; // Importando la clase List
import java.util.Optional; // Importando la clase Optional

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/admin") // Mapea las solicitudes HTTP a /admin a este controlador
@PreAuthorize("hasRole('ADMIN')") // Requiere que el usuario tenga el rol de ADMIN para acceder a los endpoints
public class AdminController {

    @Autowired // Inyección de dependencia de UserRepository
    private UserRepository userRepository;

    @GetMapping(value="demo") // Mapea las solicitudes HTTP GET a /admin/demo a este método
    public String welcome() {
        return "Bienvenido al endpoint administrador"; // Devuelve un mensaje de bienvenida
    }

    @GetMapping("/users") // Mapea las solicitudes HTTP GET a /admin/users a este método
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Devuelve una lista de todos los usuarios
    }

    // Endpoint para eliminar un usuario por ID
    @DeleteMapping("/users/{id}") // Mapea las solicitudes HTTP DELETE a /admin/users/{id} a este método
    public String deleteUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id); // Busca el usuario por ID
        if (user.isPresent()) { // Si el usuario está presente
            userRepository.delete(user.get()); // Elimina el usuario
            return "El usuario " + id + " fue eliminado"; // Devuelve un mensaje de éxito
        } else {
            return "El usuario con el ID " + id + " no existe en el sistema"; // Devuelve un mensaje de error
        }
    }
}