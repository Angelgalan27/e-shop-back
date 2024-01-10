package es.eshop.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @Builder.Default
    private List<UserAddressDTO> addresses = new ArrayList<>();
    @Builder.Default
    private List<RolDTO> roles = new ArrayList<>();
}
