package com.allangroisman.Interface;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allangroisman.Aplicacao.Historico.HistoricoApostas_UC;
import com.allangroisman.Aplicacao.Historico.HistoricoSorteios_UC;

@Controller
@RequestMapping("/")
public class ControllerHistorico {

    HistoricoSorteios_UC buscarSorteios_UC;
    HistoricoApostas_UC buscarApostas_UC;

    public ControllerHistorico(HistoricoSorteios_UC buscarSorteios_UC, HistoricoApostas_UC buscarApostas_UC) {
        this.buscarSorteios_UC = buscarSorteios_UC;
        this.buscarApostas_UC = buscarApostas_UC;

        System.out.println("\n\nCriado Controller Historico\n\n");

    }

    @GetMapping("/historico")
    public String menuHistorico(Model model) {
        return "historico/historico";
    }

    @GetMapping("/historicosorteios")
    public String historicoSorteios(Model model) {
        model.addAttribute("sorteios", buscarSorteios_UC.run());
        return "historico/hisoricoSorteios";
    }

    @GetMapping("/historicoapostas")
    public String historicoApostas(Model model) {
        model.addAttribute("apostas", buscarApostas_UC.run());
        return "historico/historicoApostas";
    }

}
