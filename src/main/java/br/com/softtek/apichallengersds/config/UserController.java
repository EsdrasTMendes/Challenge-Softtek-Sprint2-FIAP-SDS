package br.com.softtek.apichallengersds.config;


import br.com.softtek.apichallengersds.dto.PasswordResetDTO;
import br.com.softtek.apichallengersds.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("{username}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable String username, @RequestBody PasswordResetDTO passwordResetDTO, Authentication authentication) {
      userAuthService.resetUserPassword(username, passwordResetDTO.newPassword(), authentication);
      return ResponseEntity.ok("Senha resetada para o usuário" + username + "!");
    }
}
