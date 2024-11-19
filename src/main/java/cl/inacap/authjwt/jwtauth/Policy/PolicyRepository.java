package cl.inacap.authjwt.jwtauth.Policy;

import cl.inacap.authjwt.jwtauth.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Anotación para indicar que esta clase es un repositorio
public interface PolicyRepository extends JpaRepository<Policy, Integer> {
    List<Policy> findAllByUser(User user);
}
