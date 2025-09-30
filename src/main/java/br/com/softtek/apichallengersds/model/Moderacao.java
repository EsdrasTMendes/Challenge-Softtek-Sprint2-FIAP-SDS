package br.com.softtek.apichallengersds.model;

import lombok.Data;
import java.util.List;

@Data
public class Moderacao {
    private StatusModeracao status;
    private List<String> detecaoAutomatica;
}