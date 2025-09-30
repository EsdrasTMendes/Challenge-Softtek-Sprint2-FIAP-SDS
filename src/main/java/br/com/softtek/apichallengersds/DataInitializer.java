package br.com.softtek.apichallengersds;

import br.com.softtek.apichallengersds.model.UserAuth;
import br.com.softtek.apichallengersds.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserAuthRepository UserAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        var adminUsername = "platform.admin@softtex.com";
        if(UserAuthRepository.findByUsername(adminUsername).isEmpty()){
            UserAuth platformAdmin = new UserAuth();
            platformAdmin.setUsername(adminUsername);
            platformAdmin.setPassword(passwordEncoder.encode("SofttekAdminSenhaForte123!"));

            platformAdmin.setRoles(List.of("ROLE_PLATFORM_ADMIN"));


            UserAuthRepository.save(platformAdmin);
            System.out.println(">>> Usuário PLATFORM_ADMIN' criado com sucesso!");
        }
    }
}
