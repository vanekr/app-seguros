package cl.inacap.authjwt.jwtauth; // Declaración del paquete

import org.springframework.boot.SpringApplication; // Importando SpringApplication para manejar la aplicación Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Importando SpringBootApplication para configurar la aplicación Spring Boot

@SpringBootApplication // Anotación para marcar esta clase como una aplicación Spring Boot
public class JwtauthApplication {

    public static void main(String[] args) { // Método principal para ejecutar la aplicación
        SpringApplication.run(JwtauthApplication.class, args); // Ejecuta la aplicación Spring Boot
    }

}