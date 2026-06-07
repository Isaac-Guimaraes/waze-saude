package com.example.waze_saude.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class UnidadeSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUnidade;

    private String nome;
    private String endereco;
    private Double latitude;
    private Double longitude;
    private Integer capacidadeMaxima;
    private Integer ocupacaoAtual = 0;


    @Transient
    private Double distanciaAtual;

    public UnidadeSaude() {}

    public UnidadeSaude(String nome, String endereco, Double latitude, Double longitude, Integer capacidadeMaxima, Integer ocupacaoAtual) {
        this.nome = nome;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacidadeMaxima = capacidadeMaxima;
        this.ocupacaoAtual = ocupacaoAtual;
    }

    // --- GETTERS E SETTERS ---
    public Long getIdUnidade() { return idUnidade; }
    public void setIdUnidade(Long idUnidade) { this.idUnidade = idUnidade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getCapacidadeMaxima() { return capacidadeMaxima; }
    public void setCapacidadeMaxima(Integer capacidadeMaxima) { this.capacidadeMaxima = capacidadeMaxima; }

    public Integer getOcupacaoAtual() { return ocupacaoAtual; }
    public void setOcupacaoAtual(Integer ocupacaoAtual) { this.ocupacaoAtual = ocupacaoAtual; }

    public Double getDistanciaAtual() { return distanciaAtual; }
    public void setDistanciaAtual(Double distanciaAtual) { this.distanciaAtual = distanciaAtual; }
}