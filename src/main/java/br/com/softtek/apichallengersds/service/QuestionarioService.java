package br.com.softtek.apichallengersds.service;

import br.com.softtek.apichallengersds.model.QuestionarioModelo;
import br.com.softtek.apichallengersds.model.QuestionarioSemanal;
import br.com.softtek.apichallengersds.model.UserAuth;
import br.com.softtek.apichallengersds.model.Usuario;
import br.com.softtek.apichallengersds.repository.QuestionarioModeloRepository;
import br.com.softtek.apichallengersds.repository.QuestionarioSemanalRepository;
import br.com.softtek.apichallengersds.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

@Service
public class QuestionarioService {

    @Autowired
    private QuestionarioModeloRepository questionarioModeloRepository;

    @Autowired
    private QuestionarioSemanalRepository questionarioSemanalRepository;

    @Autowired
    private AnaliseDeRiscoService analiseDeRiscoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public QuestionarioModelo findActiveModelo() {
        return questionarioModeloRepository.findByAtivo(true)
                .orElseThrow(() -> new RuntimeException("Nenhum modelo de questionário ativo encontrado."));
    }

    public void saveRespostas(QuestionarioSemanal respostas, Authentication authentication) {
        UserAuth userAuth = (UserAuth) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByUserAuthId(userAuth.getId())
                .orElseThrow(() -> new RuntimeException("Perfil de usuário de dados não encontrado."));

        respostas.setEmpresaId(usuario.getEmpresaId());
        respostas.setUsuarioAnonimoId(usuario.getUsuarioAnonimoId());
        respostas.setCriadoEm(new Date());

        QuestionarioModelo modeloAtivo = findActiveModelo();
        respostas.setVersao(modeloAtivo.getVersao());

        TemporalField fieldISO = WeekFields.of(Locale.getDefault()).dayOfWeek();
        LocalDate inicioDaSemana = LocalDate.now().with(fieldISO, 1);
        respostas.setSemanaInicio(inicioDaSemana.format(DateTimeFormatter.ISO_LOCAL_DATE));

        QuestionarioSemanal respostasSalvas = questionarioSemanalRepository.save(respostas);
        analiseDeRiscoService.gerarAnaliseIndividual(respostasSalvas);
    }
}