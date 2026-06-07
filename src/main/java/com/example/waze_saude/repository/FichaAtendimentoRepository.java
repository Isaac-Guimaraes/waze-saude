package com.example.waze_saude.repository;

import com.example.waze_saude.model.FichaAtendimento;
import com.example.waze_saude.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FichaAtendimentoRepository extends JpaRepository<FichaAtendimento, Long> {


    Optional<FichaAtendimento> findByPacienteAndDataHoraCheckoutIsNull(Paciente paciente);
}