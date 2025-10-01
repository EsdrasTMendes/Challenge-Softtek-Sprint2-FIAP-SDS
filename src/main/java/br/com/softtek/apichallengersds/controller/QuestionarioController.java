package br.com.softtek.apichallengersds.controller;

import br.com.softtek.apichallengersds.model.QuestionarioModelo;
import br.com.softtek.apichallengersds.model.QuestionarioSemanal;
import br.com.softtek.apichallengersds.service.QuestionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questionarios")
public class QuestionarioController {

    @Autowired
    private QuestionarioService questionarioService;

    /**
     * Endpoint para o frontend buscar a estrutura do questionário atual.
     * Qualquer usuário logado pode acessar.
     */
    @GetMapping("/modelo/ativo")
    public ResponseEntity<QuestionarioModelo> getActiveQuestionarioModelo() {
        QuestionarioModelo modelo = questionarioService.findActiveModelo();
        return ResponseEntity.ok(modelo);
    }

    /**
     * Endpoint para o colaborador enviar suas respostas do questionário semanal.
     */
    @PostMapping("/respostas")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> submitRespostas(@RequestBody QuestionarioSemanal respostas, Authentication authentication) {
        questionarioService.saveRespostas(respostas, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body("Respostas salvas com sucesso.");
    }
}