package cl.inacap.authjwt.jwtauth.Policy;

import cl.inacap.authjwt.jwtauth.User.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service // Anotación para indicar que esta clase es un servicio
@RequiredArgsConstructor // Genera un constructor con argumentos requeridos (campos finales)
public class PolicyService {

    private final PolicyRepository policyRepository; // Inyección de PolicyRepository para acceso a la base de datos

    @Transactional // Maneja la transacción para guardar datos en la base de datos
    public Policy createPolicy(String type, String coverage, String terms, LocalDate expirationDate, String status, User user) {
        // Crear una nueva instancia de Policy con los datos proporcionados
        Policy policy = Policy.builder()
                .type(type)
                .coverage(coverage)
                .terms(terms)
                .expirationDate(expirationDate)
                .status(status)
                .user(user)
                .build();

        // Guardar la póliza en la base de datos
        return policyRepository.save(policy);
    }

    public Policy updatePolicy(Integer id, PolicyRequest updatedPolicyRequest, User user) {
        // Buscar la póliza por ID
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        // Verificar que la póliza pertenece al usuario autenticado
        if (!policy.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this policy");
        }

        // Actualizar los campos de la póliza
        policy.setType(updatedPolicyRequest.getType());
        policy.setCoverage(updatedPolicyRequest.getCoverage());
        policy.setTerms(updatedPolicyRequest.getTerms());
        policy.setExpirationDate(updatedPolicyRequest.getExpirationDate());
        policy.setStatus(updatedPolicyRequest.getStatus());

        // Guardar la póliza actualizada en la base de datos
        return policyRepository.save(policy);
    }

    public Policy getPolicyByIdAndUser(Integer id, User user) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        // Verificar que la póliza pertenece al usuario autenticado
        if (!policy.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to access this policy");
        }

        return policy;
    }

    public List<Policy> getAllPoliciesByUser(User user) {
        return policyRepository.findAllByUser(user);
    }

    @Transactional
    public void deletePolicy(Integer id, User user) {
        // Buscar la póliza por ID
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        // Verificar que la póliza pertenece al usuario autenticado
        if (!policy.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this policy");
        }

        // Eliminar la póliza
        policyRepository.delete(policy);
    }

}
