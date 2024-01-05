package es.eshop.app.service;

import es.eshop.app.model.LoginRequestDTO;
import es.eshop.app.model.LoginResponseDTO;

public interface ILoginService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
