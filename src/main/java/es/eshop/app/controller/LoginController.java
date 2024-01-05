package es.eshop.app.controller;

import es.eshop.app.model.LoginRequestDTO;
import es.eshop.app.model.LoginResponseDTO;
import es.eshop.app.service.ILoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return new ResponseEntity<>(loginService.login(loginRequestDTO), HttpStatus.OK);
    }
}
