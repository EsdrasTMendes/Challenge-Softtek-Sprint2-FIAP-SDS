package br.com.softtek.apichallengersds.service;

import br.com.softtek.apichallengersds.model.QuestionarioSemanal;
import br.com.softtek.apichallengersds.model.RelatorioRisco;
import br.com.softtek.apichallengersds.model.RespostasQuestionarios;
import br.com.softtek.apichallengersds.repository.RelatorioRiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnaliseDeRiscoService {

    @Autowired
    private RelatorioRiscoRepository relatorioRiscoRepository;

    public void gerarAnaliseIndividual(QuestionarioSemanal questionario) {
        RespostasQuestionarios respostas = questionario.getRespostas();
        Map<String, String> diagnosticos = new HashMap<>();

        double mediaRelacionamento = (respostas.getRelacionamentos() + respostas.getApoioGestor()) / 2.0;
        diagnosticos.put("DiagnosticoClimaRelacionamento", classificarClima(mediaRelacionamento));

        diagnosticos.put("NivelCargaTrabalho", classificarCargaTrabalho(respostas.getCargaTrabalho()));

        RelatorioRisco relatorio = new RelatorioRisco();
        relatorio.setEmpresaId(questionario.getEmpresaId());
        relatorio.setUsuarioAnonimoId(questionario.getUsuarioAnonimoId());
        relatorio.setSemanaReferencia(questionario.getSemanaInicio());
        relatorio.setDataAnalise(new Date());
        relatorio.setDiagnosticos(diagnosticos);

        relatorioRiscoRepository.save(relatorio);
        System.out.println(">>> Relatório de risco gerado para o usuário " + questionario.getUsuarioAnonimoId());
    }

    private String classificarClima(double media) {
        if (media >= 3.5) return "Ambiente Saudável";
        if (media >= 2.5) return "Zona de Alerta";
        return "Atenção";
    }

    private String classificarCargaTrabalho(int score) {
        return switch (score) {
            case 1 -> "Muito Leve";
            case 2 -> "Leve";
            case 3 -> "Média";
            case 4 -> "Alta";
            case 5 -> "Muito Alta";
            default -> "Indefinido";
        };
    }
}