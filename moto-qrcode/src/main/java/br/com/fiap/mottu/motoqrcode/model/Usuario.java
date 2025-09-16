package br.com.fiap.mottu.motoqrcode.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "USUARIO")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(nullable = false, unique = true)
    private String username;

    @NotBlank @Column(nullable = false)
    private String password;

    @NotBlank @Column(nullable = false)
    private String role; // ADMIN ou USER
}
