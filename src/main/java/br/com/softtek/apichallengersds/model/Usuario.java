package br.com.softtek.apichallengersds.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="usuarios")
public class Usuario {
    @Id
    private String id;

    private String empresaId;
    private String usuarioAnonimoId;
    private Avatar avatar;
    private Meta meta;
}
