package cl.inacap.authjwt.jwtauth.User; // Declaración del paquete

import java.util.Optional; // Importando Optional para manejar valores opcionales

import org.springframework.data.jpa.repository.JpaRepository; // Importando JpaRepository para manejar repositorios JPA
import org.springframework.stereotype.Repository;

@Repository // Anotación para marcar esta interfaz como un repositorio de Spring
public interface UserRepository extends JpaRepository<User, Integer> { // Declaración de la interfaz UserRepository que extiende JpaRepository
    Optional<User> findByUsername(String username); // Método para buscar un usuario por nombre de usuario
}