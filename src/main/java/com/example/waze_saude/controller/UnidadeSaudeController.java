package com.example.waze_saude.controller;

import com.example.waze_saude.model.FichaAtendimento;
import com.example.waze_saude.model.Paciente;
import com.example.waze_saude.model.UnidadeSaude;
import com.example.waze_saude.repository.FichaAtendimentoRepository;
import com.example.waze_saude.repository.UnidadeSaudeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class UnidadeSaudeController {

    @Autowired
    private UnidadeSaudeRepository unidadeRepository;

    @Autowired
    private FichaAtendimentoRepository fichaRepository;

    // Coordenadas simuladas do utilizador (Centro de Salvador mokado)
    private final double LAT_USUARIO = -12.9714;
    private final double LON_USUARIO = -38.5104;

    @GetMapping("/mapa-saude")
    public String exibirMapa(Model model, HttpSession session) {
        Paciente usuarioLogado = (Paciente) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) return "redirect:/";

        List<UnidadeSaude> unidadesProximas = unidadeRepository.findAll();

        // 1. Calcula a distância de cada UBS até o utilizador
        for (UnidadeSaude ubs : unidadesProximas) {
            double distancia = calcularDistanciaHaversine(LAT_USUARIO, LON_USUARIO, ubs.getLatitude(), ubs.getLongitude());
            ubs.setDistanciaAtual(distancia);
        }

        // 2. ORDENAÇÃO (RN02)
        unidadesProximas.sort(
                Comparator.comparingDouble((UnidadeSaude u) -> (double) u.getOcupacaoAtual() / u.getCapacidadeMaxima())
                        .thenComparingDouble(UnidadeSaude::getDistanciaAtual)
        );

        model.addAttribute("unidades", unidadesProximas);
        model.addAttribute("usuario", usuarioLogado);

        Optional<FichaAtendimento> fichaAberta = fichaRepository.findByPacienteAndDataHoraCheckoutIsNull(usuarioLogado);
        model.addAttribute("temFichaAberta", fichaAberta.isPresent());
        if(fichaAberta.isPresent()){
            model.addAttribute("unidadeAtualId", fichaAberta.get().getUnidade().getIdUnidade());
        }

        return "mapa";
    }

    @PostMapping("/checkin/{id}")
    public String fazerCheckin(@PathVariable Long id, HttpSession session) {
        Paciente usuarioLogado = (Paciente) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) return "redirect:/";
        if (fichaRepository.findByPacienteAndDataHoraCheckoutIsNull(usuarioLogado).isPresent()) return "redirect:/mapa-saude";

        UnidadeSaude unidade = unidadeRepository.findById(id).orElse(null);
        if (unidade != null && unidade.getOcupacaoAtual() < unidade.getCapacidadeMaxima()) {
            unidade.setOcupacaoAtual(unidade.getOcupacaoAtual() + 1);
            unidadeRepository.save(unidade);
            fichaRepository.save(new FichaAtendimento(usuarioLogado, unidade, LocalDateTime.now()));
        }
        return "redirect:/mapa-saude";
    }

    @PostMapping("/checkout/{id}")
    public String fazerCheckout(@PathVariable Long id, HttpSession session) {
        Paciente usuarioLogado = (Paciente) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) return "redirect:/";

        Optional<FichaAtendimento> fichaAberta = fichaRepository.findByPacienteAndDataHoraCheckoutIsNull(usuarioLogado);
        if (fichaAberta.isPresent() && fichaAberta.get().getUnidade().getIdUnidade().equals(id)) {
            FichaAtendimento ficha = fichaAberta.get();
            ficha.setDataHoraCheckout(LocalDateTime.now());
            fichaRepository.save(ficha);

            UnidadeSaude unidade = ficha.getUnidade();
            if (unidade.getOcupacaoAtual() > 0) {
                unidade.setOcupacaoAtual(unidade.getOcupacaoAtual() - 1);
                unidadeRepository.save(unidade);
            }
        }
        return "redirect:/mapa-saude";
    }

    // --- FÓRMULA DE HAVERSINE ---
    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raio da Terra em Km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Devolve a distância em Quilometros
    }
}