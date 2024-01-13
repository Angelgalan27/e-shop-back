package es.eshop.app.controller;

import es.eshop.app.model.LoginRequestDTO;
import es.eshop.app.model.LoginResponseDTO;
import es.eshop.app.service.ILoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final ILoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return new ResponseEntity<>(loginService.login(loginRequestDTO), HttpStatus.OK);
    }
}
