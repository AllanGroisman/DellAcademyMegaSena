package com.allangroisman.Interface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allangroisman.Aplicacao.FaseDeApuracao.ApurarSorteio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarQuantidadeRodadas_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarQuantidadeVencedores_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarSorteados_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ExibirRelatorio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ListarVencedores_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.Manipular_UC;

@Controller
@RequestMapping("/")
public class ControllerApuracao {

    ApurarSorteio_UC apurarSorteio_UC; // apura e sorteia numeros ate ter um vencedor

    BuscarSorteados_UC buscarSorteados_UC; // busca os numeros sorteados
    BuscarQuantidadeRodadas_UC buscarQuantidadeRodadas_UC; // busca quantas rodadas teve ate sair o vencedor
    BuscarQuantidadeVencedores_UC buscarQuantidadeVencedores_UC; // busca a lista de vencedores
    ExibirRelatorio_UC exibirRelatorio_UC; // gera o relatorio de todos os numeros apostados
    ListarVencedores_UC listarVencedores_UC; // busca a lista de vencedores

    Manipular_UC manipular_UC;

    @Autowired
    public ControllerApuracao(ApurarSorteio_UC apurarSorteio_UC, ExibirRelatorio_UC exibirRelatorio_UC,
            BuscarSorteados_UC buscarSorteados_UC,
            BuscarQuantidadeRodadas_UC buscarQuantidadeRodadas_UC,
            BuscarQuantidadeVencedores_UC buscarQuantidadeVencedores_UC,
            ListarVencedores_UC listarVencedores_UC, Manipular_UC manipular_UC) {

        this.apurarSorteio_UC = apurarSorteio_UC;
        
        this.buscarSorteados_UC = buscarSorteados_UC;
        this.buscarQuantidadeRodadas_UC = buscarQuantidadeRodadas_UC;
        this.buscarQuantidadeVencedores_UC = buscarQuantidadeVencedores_UC;
        this.listarVencedores_UC = listarVencedores_UC;
        this.exibirRelatorio_UC = exibirRelatorio_UC;
        this.manipular_UC = manipular_UC;
        System.out.println("\n\nCriado Controller Apuração\n\n");
    }

    // FASE DE
    // APURACAO/////////////////////////////////////////////////////////////////////////////////////////
    // apurar vencedores e entao exibir
    @GetMapping("/apuracao")
    public String apurarSorteio(Model model) {
        apurarSorteio_UC.run();
        model.addAttribute("sorteados", buscarSorteados_UC.run()); // numeros sorteados
        model.addAttribute("rodadas", buscarQuantidadeRodadas_UC.run()); // quantidade de rodadas
        model.addAttribute("vencedores", buscarQuantidadeVencedores_UC.run()); // quantidade de vencedores
        return "apuracao/3apuracao";
    }

    // apenas exibir
    @GetMapping("/exibirapuracao")
    public String exibirApuracao(Model model) {
        model.addAttribute("sorteados", buscarSorteados_UC.run()); // numeros sorteados
        model.addAttribute("rodadas", buscarQuantidadeRodadas_UC.run()); // quantidade de rodadas
        model.addAttribute("vencedores", buscarQuantidadeVencedores_UC.run()); // quantidade de vencedores
        return "apuracao/3apuracao";
    }

    // exibir relatorio que volta para o exibir apuracao
    @GetMapping("/gerarrelatorio")
    public String gerarRelatorio(Model model) {
        model.addAttribute("relatorio", exibirRelatorio_UC.run());
        return "apuracao/4exibirrelatorio";
    }

    // exibir lista de vencedores
    @GetMapping("/listarvencedores")
    public String listarVencedores(Model model) {
        model.addAttribute("vencedores", listarVencedores_UC.run());
        return "apuracao/4listarvencedores";
    }

    // https://youtu.be/RKEmrNOo77I?si=ELvHVFphPCunVyJI
    // exibir lista de vencedores
    @GetMapping("/receberpremio")
    public String receberPremio(Model model) {
        model.addAttribute("vencedores", listarVencedores_UC.run());
        return "apuracao/4receberPremio";
    }

    @GetMapping("/manipular")
    public String manipular() {
        manipular_UC.run();
        return "apuracao/3apuracao";
    }
}
