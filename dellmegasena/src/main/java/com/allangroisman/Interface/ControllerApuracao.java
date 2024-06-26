package com.allangroisman.Interface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allangroisman.Aplicacao.FaseDeApuracao.ApurarSorteio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarNome_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarQtdRodadas_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarQtdVencedores_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarSorteados_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.EncerrarSorteio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ExibirRelatorio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ListarVencedores_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.Manipular_UC;
import com.allangroisman.Aplicacao.FaseDePremiacao.ReceberPremio_UC;

@Controller
@RequestMapping("/")
public class ControllerApuracao {

    ApurarSorteio_UC apurarSorteio_UC; // Implementado - apura e sorteia numeros ate ter um vencedor e retorna se há ou
                                       // nao vencedores

    BuscarSorteados_UC buscarSorteados_UC; // Implementado - busca os numeros sorteados
    BuscarQtdRodadas_UC buscarQtdRodadas_UC; // Implementado - busca quantas rodadas teve ate sair o vencedor
    BuscarQtdVencedores_UC buscarQtdVencedores_UC; // Implementado - busca a quantidade de vencedores

    ExibirRelatorio_UC exibirRelatorio_UC; // Implementado - gera o relatorio de todos os numeros apostados FALTA SORT
    ListarVencedores_UC listarVencedores_UC; // busca a lista de vencedores

    Manipular_UC manipular_UC;

    EncerrarSorteio_UC encerrarSorteio_UC;
    ReceberPremio_UC receberPremio_UC;
    BuscarNome_UC buscarNome_UC;

    @Autowired
    public ControllerApuracao(ApurarSorteio_UC apurarSorteio_UC, BuscarSorteados_UC buscarSorteados_UC,
            BuscarQtdRodadas_UC buscarQtdRodadas_UC,
            BuscarQtdVencedores_UC buscarQtdVencedores_UC,
            ExibirRelatorio_UC exibirRelatorio_UC,
            ListarVencedores_UC listarVencedores_UC, Manipular_UC manipular_UC,
            EncerrarSorteio_UC encerrarSorteio_UC, ReceberPremio_UC receberPremio_UC,
            BuscarNome_UC buscarNome_UC) {

        this.apurarSorteio_UC = apurarSorteio_UC;

        this.buscarSorteados_UC = buscarSorteados_UC;
        this.buscarQtdRodadas_UC = buscarQtdRodadas_UC;
        this.buscarQtdVencedores_UC = buscarQtdVencedores_UC;
        this.listarVencedores_UC = listarVencedores_UC;
        this.exibirRelatorio_UC = exibirRelatorio_UC;
        this.manipular_UC = manipular_UC;
        this.encerrarSorteio_UC = encerrarSorteio_UC;
        this.receberPremio_UC = receberPremio_UC;
        this.buscarNome_UC = buscarNome_UC;
        System.out.println("\n\nCriado Controller Apuração\n\n");
    }

    // FASE DE
    // APURACAO/////////////////////////////////////////////////////////////////////////////////////////
    // apurar vencedores e entao exibir
    @GetMapping("/apuracao")
    public String apurarSorteio(Model model) {
        apurarSorteio_UC.run();
        model.addAttribute("sorteados", buscarSorteados_UC.run()); // numeros sorteados
        model.addAttribute("rodadas", buscarQtdRodadas_UC.run()); // quantidade de rodadas
        model.addAttribute("vencedores", buscarQtdVencedores_UC.run()); // quantidade de vencedores
        return "apuracao/3apuracao";
    }

    // apenas exibir
    @GetMapping("/exibirapuracao")
    public String exibirApuracao(Model model) {
        model.addAttribute("sorteados", buscarSorteados_UC.run()); // numeros sorteados
        model.addAttribute("rodadas", buscarQtdRodadas_UC.run()); // quantidade de rodadas
        model.addAttribute("vencedores", buscarQtdVencedores_UC.run()); // quantidade de vencedores
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

    // exibir lista de vencedores
    @GetMapping("/receberpremio")
    public String receberPremio(Model model) {
        return "apuracao/4receberPremio";
    }

    @GetMapping("/encerrarsorteio")
    public String encerrarSorteio(Model model) {
        encerrarSorteio_UC.run();
        return "telainicial";
    }

    @GetMapping("/manipular")
    public String manipular() {
        manipular_UC.run();
        return "apuracao/manipular";
    }

    @GetMapping("/premio/{cpf}")
    public String premiacao(@PathVariable("cpf") String cpf, Model model) {

        if (receberPremio_UC.run(cpf)) {
            model.addAttribute("resposta", buscarNome_UC.run(cpf) + "!");
            return "premiacao/premiacao";
        }

        return "premiacao/enganacao";
    }

    @GetMapping("/premiacao/temcerteza")
    public String premiacao() {

        return "premiacao/temcerteza";
    }

}
