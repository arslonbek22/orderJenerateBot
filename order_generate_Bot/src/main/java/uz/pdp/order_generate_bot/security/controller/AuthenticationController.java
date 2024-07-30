package uz.pdp.order_generate_bot.security.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.order_generate_bot.security.dto.LoginReq;
import uz.pdp.order_generate_bot.security.dto.LoginResponse;
import uz.pdp.order_generate_bot.security.entity.User;
import uz.pdp.order_generate_bot.security.service.AuthenticationService;
import uz.pdp.order_generate_bot.security.service.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
        User authenticatedUser = authenticationService.authenticate(loginReq.getUsername(), loginReq.getPassword());
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse("Bearer " + jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}
