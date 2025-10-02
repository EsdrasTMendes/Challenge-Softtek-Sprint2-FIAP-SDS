package br.com.softtek.apichallengersds;

import br.com.softtek.apichallengersds.model.CategoriaQuestionario;
import br.com.softtek.apichallengersds.model.Pergunta;
import br.com.softtek.apichallengersds.model.QuestionarioModelo;
import br.com.softtek.apichallengersds.model.UserAuth;
import br.com.softtek.apichallengersds.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import br.com.softtek.apichallengersds.repository.QuestionarioModeloRepository;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private QuestionarioModeloRepository questionarioModeloRepository;

    @Autowired
    private UserAuthRepository UserAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(questionarioModeloRepository.count() == 0){
            QuestionarioModelo questionario = new QuestionarioModelo();
            questionario.setVersao( "ISO45003-v1.0" );
            questionario.setNome("Questionário Semanal de Riscos Psicossociais");
            questionario.setAtivo(true);

            CategoriaQuestionario carga = new CategoriaQuestionario();
            carga.setNome("Fatores de Carga de Trabalho");
            carga.setPerguntas(List.of(
                    new Pergunta("carga_1", "Sua carga de trabalho foi excessiva?", "FREQUENCIA"),
                    new Pergunta("carga_2", "Você trabalha além do seu horário regular?", "FREQUENCIA")
            ));

            questionario.setCategorias(List.of(carga));
            questionarioModeloRepository.save(questionario);
            System.out.println(">> Modelo de Questionário Semanal criado com sucesso!");
        }

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
