package br.com.fiap.mottu.motoqrcode.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank
    @Pattern(regexp = "ADMIN|USER", message = "role deve ser ADMIN ou USER")
    @Column(nullable = false, length = 20)
    private String role;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nome;
}
