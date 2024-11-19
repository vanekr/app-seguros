package cl.inacap.authjwt.jwtauth.Policy;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PolicyRequest {
    private String type;
    private String coverage;
    private String terms;
    private LocalDate expirationDate;
    private String status;
}
