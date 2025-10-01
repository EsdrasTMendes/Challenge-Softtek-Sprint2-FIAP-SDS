package br.com.softtek.apichallengersds.service;

import br.com.softtek.apichallengersds.model.LogAuditoria;
import br.com.softtek.apichallengersds.repository.LogAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuditLogService {

    @Autowired
    private LogAuditoriaRepository repository;

    public LogAuditoria log(String empresaId, String acao, Map<String, Object> detalhes) {
        LogAuditoria log = new LogAuditoria();
        log.setEmpresaId(empresaId);
        log.setAcao(acao);
        log.setDetalhes(detalhes);
        log.setCriadoEm(new Date());
        return repository.save(log);
    }

    public List<LogAuditoria> listar(String empresaId, String acao) {

        List<LogAuditoria> todos = repository.findAll();
        return todos.stream()
                .filter(l -> empresaId == null || empresaId.equals(l.getEmpresaId()))
                .filter(l -> acao == null || acao.equalsIgnoreCase(l.getAcao()))
                .toList();
    }
}
