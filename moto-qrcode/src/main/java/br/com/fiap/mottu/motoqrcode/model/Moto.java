package br.com.fiap.mottu.motoqrcode.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "T_MOTTU_MOTO")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOTO")
    private Long id;

    @NotBlank
    @Column(name = "DS_PLACA", nullable = false, unique = true)
    private String placa;

    @NotBlank
    @Column(name = "NM_MODELO", nullable = false)
    private String modelo;

    @ManyToOne
    @JoinColumn(name = "ID_AREA", nullable = false)
    @NotNull
    private Area area;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
