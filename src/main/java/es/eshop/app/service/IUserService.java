package es.eshop.app.service;

import es.eshop.app.model.UserDTO;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IBaseService<UserDTO, Long>, UserDetailsService, AuthenticationProvider {

    UserDTO getByEmail(String email);
}
