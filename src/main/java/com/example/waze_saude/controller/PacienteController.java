package com.example.waze_saude.controller;

import com.example.waze_saude.model.Paciente;
import com.example.waze_saude.repository.PacienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @GetMapping("/")
    public String telaLogin(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String cpf, Model model, HttpSession session) {
        Optional<Paciente> paciente = repository.findByCpf(cpf);

        if (paciente.isPresent()) {
            // Guarda a info de login
            session.setAttribute("usuarioLogado", paciente.get());
            return "redirect:/mapa-saude";
        } else {
            model.addAttribute("erroLogin", "CPF não encontrado.");
            model.addAttribute("paciente", new Paciente());
            return "login";
        }
    }

    @PostMapping("/cadastro")
    public String cadastrarPaciente(Paciente paciente, Model model, HttpSession session) {
        if (repository.findByCpf(paciente.getCpf()).isPresent()) {
            model.addAttribute("erroCadastro", "Este CPF já está cadastrado.");
            return "login";
        }

        repository.save(paciente);
        session.setAttribute("usuarioLogado", paciente); // Já faz o login automático após cadastro
        return "redirect:/mapa-saude";
    }

    // LOGOUT
    @PostMapping("/logout")
    public String fazerLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}