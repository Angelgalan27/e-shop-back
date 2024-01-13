package es.eshop.app.service.impl;

import es.eshop.app.model.LoginRequestDTO;
import es.eshop.app.model.LoginResponseDTO;
import es.eshop.app.service.ILoginService;
import es.eshop.app.service.IUserService;
import es.eshop.app.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService {

    private final IUserService userService;

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication auth = userService.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        return LoginResponseDTO.builder()
                .token(jwtTokenUtil.createToken(auth))
                .user(userService.getByEmail(loginRequestDTO.getEmail()))
                .build();
    }
}
