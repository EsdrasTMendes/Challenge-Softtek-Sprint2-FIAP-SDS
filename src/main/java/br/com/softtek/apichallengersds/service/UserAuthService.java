package br.com.softtek.apichallengersds.service;

import br.com.softtek.apichallengersds.dto.AdminCreateDTO;
import br.com.softtek.apichallengersds.model.UserAuth;
import br.com.softtek.apichallengersds.repository.EmpresaRepository;
import br.com.softtek.apichallengersds.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.softtek.apichallengersds.dto.RegistroDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.softtek.apichallengersds.repository.UsuarioRepository;

import java.util.List;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    private UserAuthRepository UserAuthRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserAuthRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado " + username));
    }

    public UserAuth createUser(String username, String password, String empresaId, List<String> roles) {
        if(userAuthRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username já existe.");
        }

        UserAuth newUser = new UserAuth();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmpresaId(empresaId);
        newUser.setRoles(roles);
        return userAuthRepository.save(newUser);
    }

    public void registerNewUser(RegistroDTO registroDTO, String empresaId) {
        empresaRepository.findByEmpresaId(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        createUser(registroDTO.username(), registroDTO.password(), empresaId, List.of("ROLE_EMPLOYEE"));
    }

    public void createCompanyAdmin(AdminCreateDTO adminCreateDTO, String targetEmpresaId, Authentication authentication) {
        UserAuth requesterAdmin = (UserAuth) authentication.getPrincipal();

        if(!requesterAdmin.getEmpresaId().equals(targetEmpresaId)) {
            throw new SecurityException("Acesso negado: sem permissão");
        }

        createUser(
                adminCreateDTO.username(),
                adminCreateDTO.password(),
                targetEmpresaId,
                List.of("ROLE_COMPANY_ADMIN")
        );
    }

    public void resetUserPassword(String targetUsername, String newPassword, Authentication currentUserAuth) {
        UserAuth currentUser = (UserAuth) currentUserAuth.getPrincipal();
        UserAuth targetUser = userAuthRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        boolean hasPermission = false;

        if(currentUser.getRoles().contains("ROLE_PLATFORM_ADMIN")) {
            hasPermission = true;
        }

        if(currentUser.getRoles().contains("ROLE_COMPANY_ADMIN") && currentUser.getEmpresaId().equals(targetUser.getEmpresaId())) {
            hasPermission = true;
        }

        if(!hasPermission) {
            throw new SecurityException("Acesso negado: Você não tem permissão para executar a ação!");
        }

        targetUser.setPassword(passwordEncoder.encode(newPassword));
        userAuthRepository.save(targetUser);
    }

}
