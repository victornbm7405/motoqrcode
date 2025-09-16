package br.com.fiap.mottu.motoqrcode.dto;

public class MotoDTO {

    private String placa;
    private String modelo;
    private Long idArea; // Usado apenas no cadastro padrÃ£o, NÃƒO no QR Code

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

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }
}
