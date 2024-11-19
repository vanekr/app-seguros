package cl.inacap.authjwt.jwtauth.Policy;

import cl.inacap.authjwt.jwtauth.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // Anotación para generar getters, setters, toString, equals y hashCode
@Builder // Anotación para implementar el patrón de diseño Builder
@NoArgsConstructor // Anotación para generar un constructor sin argumentos
@AllArgsConstructor // Anotación para generar un constructor con todos los argumentos
@Entity // Anotación para marcar esta clase como una entidad de JPA
@Table(name="policies", uniqueConstraints = {@UniqueConstraint(columnNames = {"type"})}) // Anotación para definir la tabla y las restricciones únicas
public class Policy {
    @Id // Anotación para marcar este campo como el identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Anotación para generar automáticamente el valor del identificador
    private Integer id; // Campo para almacenar el identificador del usuario

    @Column(nullable = false) // Anotación para definir la columna y establecer que no puede ser nula
    String type; // Tipo de póliza (vida, hogar, etc.)

    @Column(nullable = false) // Cobertura de la póliza
    private String coverage;

    @Column(nullable = false) // Términos y condiciones de la póliza
    private String terms;

    @Column(nullable = false) // Fecha de vencimiento de la póliza
    private LocalDate expirationDate;

    @Column(nullable = false) // Estado de la póliza (activa, cancelada, etc.)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Define la columna user_id como clave foránea
    @JsonBackReference
    private User user; // Relación Many-to-One con User
}
