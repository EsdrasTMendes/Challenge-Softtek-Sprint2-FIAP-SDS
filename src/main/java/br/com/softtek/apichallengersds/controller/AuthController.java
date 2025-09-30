package br.com.softtek.apichallengersds.controller;

import br.com.softtek.apichallengersds.dto.LoginDTO;
import br.com.softtek.apichallengersds.model.UserAuth;
import br.com.softtek.apichallengersds.service.TokenService;
import br.com.softtek.apichallengersds.service.UserAuthService;
import br.com.softtek.apichallengersds.dto.RegistroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (UserAuth) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register/{empresaId}/user")
    public ResponseEntity<String> register(@RequestBody RegistroDTO registroDTO, @PathVariable String empresaId) {
        try {
            userAuthService.registerNewUser(registroDTO, empresaId);
            return ResponseEntity.ok("Registro adicionado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
