package es.eshop.app.service;

import es.eshop.app.entity.User;
import es.eshop.app.model.UserDTO;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends IBaseService<UserDTO, Long>, UserDetailsService, AuthenticationProvider {

    List<UserDTO> getAll();
    UserDTO getByEmail(String email);

}
