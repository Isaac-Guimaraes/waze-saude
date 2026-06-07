package com.example.waze_saude.config;

import com.example.waze_saude.model.Paciente;
import com.example.waze_saude.model.UnidadeSaude;
import com.example.waze_saude.repository.PacienteRepository;
import com.example.waze_saude.repository.UnidadeSaudeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner carregarDados(UnidadeSaudeRepository unidadeRepository, PacienteRepository pacienteRepository) {
        return args -> {
            // Inicializa as UBSs
            if (unidadeRepository.count() == 0) {
                UnidadeSaude ubs1 = new UnidadeSaude("UPA 24h - Barris", "R. Direita dos Barris, 205", -12.9829, -38.5144, 50, 45);
                UnidadeSaude ubs2 = new UnidadeSaude("Clínica da Família", "Av. Aliomar Baleeiro", -12.9238, -38.4239, 30, 10);
                UnidadeSaude ubs3 = new UnidadeSaude("Hospital Geral do Estado", "Av. Vasco da Gama", -13.0039, -38.4950, 150, 148);

                unidadeRepository.saveAll(Arrays.asList(ubs1, ubs2, ubs3));
            }

            // Inicializa o Usuário Administrador
            if (pacienteRepository.count() == 0) {

                Paciente admin = new Paciente("Administrador", "123.456.789-10", "1990-01-01", "40000-000");
                pacienteRepository.save(admin);
                System.out.println("Usuário Administrador carregado com sucesso no banco H2!");
            }
        };
    }
}