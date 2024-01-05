package es.eshop.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String token;

    @JsonIgnoreProperties("password")
    private UserDTO user;
}
