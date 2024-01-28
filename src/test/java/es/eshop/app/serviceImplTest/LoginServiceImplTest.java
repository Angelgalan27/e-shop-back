package es.eshop.app.serviceImplTest;

import es.eshop.app.BaseConfigTests;
import es.eshop.app.model.LoginRequestDTO;
import es.eshop.app.service.ILoginService;
import es.eshop.app.service.IUserService;
import es.eshop.app.service.impl.LoginServiceImpl;
import es.eshop.app.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceImplTest extends BaseConfigTests {

    private final String EMAIL = "EMAIL";

    private final String PASS = "123456";

    @InjectMocks
    private ILoginService loginService = new LoginServiceImpl(null, null);

    @Mock
    private IUserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void init() {
        loginService = new LoginServiceImpl(userService, jwtTokenUtil);
    }

    @Test
    void loginTest() {
        Mockito.when(userService.authenticate(Mockito.any(Authentication.class))).thenReturn(getAuthentication());
        assertTrue(Objects.nonNull(loginService.login(getLoginRequestDTO())));
    }


    private Authentication getAuthentication() {
        return new UsernamePasswordAuthenticationToken(EMAIL, PASS, Collections.emptyList());
    }

    private LoginRequestDTO getLoginRequestDTO() {
        return LoginRequestDTO.builder()
                .email(EMAIL)
                .password(PASS)
                .build();
    }
}
