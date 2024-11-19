package cl.inacap.authjwt.jwtauth.Demo; // Declaración del paquete

import cl.inacap.authjwt.jwtauth.Policy.Policy;
import cl.inacap.authjwt.jwtauth.Policy.PolicyRequest;
import cl.inacap.authjwt.jwtauth.Policy.PolicyService;
import cl.inacap.authjwt.jwtauth.User.User; // Importando la clase User
import cl.inacap.authjwt.jwtauth.User.UserRepository; // Importando la interfaz UserRepository
import org.springframework.beans.factory.annotation.Autowired; // Importando Autowired para la inyección de dependencias
import org.springframework.security.core.Authentication; // Importando Authentication para la autenticación
import org.springframework.security.core.context.SecurityContextHolder; // Importando SecurityContextHolder para obtener el contexto de seguridad
import org.springframework.web.bind.annotation.*; // Importando anotaciones de Spring para controladores web

import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/user") // Mapea las solicitudes HTTP a /user a este controlador
public class DemoController {
    @Autowired // Inyección de dependencia de UserRepository
    private UserRepository userRepository;

    @Autowired
    private PolicyService policyService;

    @GetMapping(value="demo") // Mapea las solicitudes HTTP GET a /user/demo a este método
    public String welcome(){
        return "Bienvenido al endpoint de usuario"; // Devuelve un mensaje de bienvenida
    }

    @GetMapping("/perfil") // Mapea las solicitudes HTTP GET a /user/perfil a este método
    public User getUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Obtiene la autenticación actual
        String username = auth.getName(); // Obtiene el nombre de usuario del contexto de seguridad
        return userRepository.findByUsername(username) // Busca el usuario por nombre de usuario
                .orElseThrow(() -> new RuntimeException("User not found")); // Lanza una excepción si el usuario no se encuentra
    }

    @PutMapping("/perfil") // Mapea las solicitudes HTTP PUT a /user/perfil a este método
    public String updateUserProfile(@RequestBody User updatedUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Obtiene la autenticación actual
        String username = auth.getName(); // Obtiene el nombre de usuario del contexto de seguridad

        User existingUser = userRepository.findByUsername(username) // Busca el usuario por nombre de usuario
                .orElseThrow(() -> new RuntimeException("User not found")); // Lanza una excepción si el usuario no se encuentra

        existingUser.setPassword(updatedUser.getPassword()); // Actualiza la contraseña del usuario
        userRepository.save(existingUser); // Guarda el usuario actualizado en el repositorio
        return "Profile updated successfully!"; // Devuelve un mensaje de éxito
    }

    @PostMapping("/perfil/policy")
    public String assignPolicyToUser(@RequestBody PolicyRequest newPolicyRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Obtener el usuario autenticado
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Crear y asignar la póliza al usuario autenticado
        Policy policy = policyService.createPolicy(
                newPolicyRequest.getType(),
                newPolicyRequest.getCoverage(),
                newPolicyRequest.getTerms(),
                newPolicyRequest.getExpirationDate(),
                newPolicyRequest.getStatus(),
                user // Pasar el objeto `User` obtenido
        );

        return "Policy assigned successfully!";
    }

    @PutMapping("/perfil/policy/{id}")
    public String updatePolicy(@PathVariable Integer id, @RequestBody PolicyRequest updatedPolicyRequest) {
        // Extraer información del usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Buscar el usuario autenticado
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Actualizar la póliza
        Policy updatedPolicy = policyService.updatePolicy(id, updatedPolicyRequest, user);

        return "Policy updated successfully!";
    }

    @GetMapping("/perfil/policy/{id}")
    public Policy getPolicyById(@PathVariable Integer id) {
        // Extraer información del usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Buscar el usuario autenticado
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Obtener la póliza por ID y verificar que pertenece al usuario autenticado
        return policyService.getPolicyByIdAndUser(id, user);
    }

    @GetMapping("/perfil/policies")
    public List<Policy> getAllPoliciesByUser() {
        // Extraer información del usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Buscar el usuario autenticado
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Obtener todas las pólizas asociadas al usuario autenticado
        return policyService.getAllPoliciesByUser(user);
    }

    @DeleteMapping("/perfil/policy/{id}")
    public String deletePolicy(@PathVariable Integer id) {
        // Extraer información del usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Buscar el usuario autenticado
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Eliminar la póliza
        policyService.deletePolicy(id, user);

        return "Policy deleted successfully!";
    }


}