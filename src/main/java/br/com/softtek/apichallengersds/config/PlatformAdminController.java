package br.com.softtek.apichallengersds.config;

import br.com.softtek.apichallengersds.dto.AdminCreateDTO;
import br.com.softtek.apichallengersds.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/platform-admin")
@PreAuthorize("hasRole('PLATFORM_ADMIN')")
public class PlatformAdminController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/users")
    public ResponseEntity<String> createPlatformAdmin(@RequestBody AdminCreateDTO adminCreateDTO) {
        userAuthService.createUser(
                adminCreateDTO.username(),
                adminCreateDTO.password(),
                null,
                List.of("ROLE_PLATFORM_ADMIN")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("Platform Admin criado com sucesso");
    }
}
