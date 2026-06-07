package com.example.waze_saude.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class FichaAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFicha;

    // Relacionamento com o Paciente
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    // Relacionamento com a Unidade de Saúde
    @ManyToOne
    @JoinColumn(name = "id_unidade")
    private UnidadeSaude unidade;


    private LocalDateTime dataHoraCheckin;
    private LocalDateTime dataHoraCheckout;

    public FichaAtendimento() {}

    public FichaAtendimento(Paciente paciente, UnidadeSaude unidade, LocalDateTime dataHoraCheckin) {
        this.paciente = paciente;
        this.unidade = unidade;
        this.dataHoraCheckin = dataHoraCheckin;
    }

    // --- GETTERS E SETTERS ---
    public Long getIdFicha() { return idFicha; }
    public void setIdFicha(Long idFicha) { this.idFicha = idFicha; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public UnidadeSaude getUnidade() { return unidade; }
    public void setUnidade(UnidadeSaude unidade) { this.unidade = unidade; }

    public LocalDateTime getDataHoraCheckin() { return dataHoraCheckin; }
    public void setDataHoraCheckin(LocalDateTime dataHoraCheckin) { this.dataHoraCheckin = dataHoraCheckin; }

    public LocalDateTime getDataHoraCheckout() { return dataHoraCheckout; }
    public void setDataHoraCheckout(LocalDateTime dataHoraCheckout) { this.dataHoraCheckout = dataHoraCheckout; }
}