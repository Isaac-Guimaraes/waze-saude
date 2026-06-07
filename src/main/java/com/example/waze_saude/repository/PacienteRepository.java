package com.example.waze_saude.repository;

import com.example.waze_saude.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // busca automaticamente um paciente pelo CPF
    Optional<Paciente> findByCpf(String cpf);
}