package com.allangroisman.Interface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.engine.ModelBuilderTemplateHandler;
import org.springframework.ui.Model;


import com.allangroisman.Aplicacao.FaseDeApostas.CriarAposta_UC;
import com.allangroisman.Aplicacao.FaseDeApostas.ListarTodasApostas_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ApurarSorteio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ExibirRelatorio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.SortearResultado_UC;
import com.allangroisman.Aplicacao.FaseDeEntrada.CriarSorteio_UC;


@Controller
@RequestMapping("/")
public class ControllerSorteio {

    CriarSorteio_UC novoSorteio_UC;
    CriarAposta_UC criarAposta_UC;
    ListarTodasApostas_UC listarApostas_UC;
    SortearResultado_UC sortearResultado_UC;
    ApurarSorteio_UC apurarSorteio_UC;
    ExibirRelatorio_UC exibirRelatorio_UC;

    @Autowired
    public ControllerSorteio(CriarSorteio_UC novoSorteio_UC, CriarAposta_UC criarAposta_UC,
            ListarTodasApostas_UC listarApostas_UC, SortearResultado_UC sortearResultado_UC,
            ApurarSorteio_UC apurarSorteio_UC, ExibirRelatorio_UC exibirRelatorio_UC) {

        this.novoSorteio_UC = novoSorteio_UC;
        this.criarAposta_UC = criarAposta_UC;
        this.listarApostas_UC = listarApostas_UC;
        this.apurarSorteio_UC = apurarSorteio_UC;
        this.sortearResultado_UC = sortearResultado_UC;

        System.out.println("\n\nCriado Controller MegaSena\n\n");

    }

    // TELA INICIAL DO PROGRAMA
    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public String inicioServicos() {
        return "telaInicial";
    }

    // Cria um novo sorteio e entra no menu de sorteio
    @GetMapping("/criarsorteio")
    public String novoSorteio(Model model) {
        model.addAttribute("mensagem", novoSorteio_UC.run());
        return "menuSorteio";
    }

    // Abrir formulario para inscricao
    @GetMapping("/criaraposta")
    public String criarAposta(Model model) {
        return "criarAposta";
    }

    // criar nova aposta com dados do usuario
    @PostMapping("/novaaposta/{nome}/{cpf}/{n1}/{n2}/{n3}/{n4}/{n5}")
    public String submeterNovaAposta(@PathVariable("nome") String nome,
            @PathVariable("cpf") String cpf,
            @PathVariable("n1") Integer n1,
            @PathVariable("n2") Integer n2,
            @PathVariable("n3") Integer n3,
            @PathVariable("n4") Integer n4,
            @PathVariable("n5") Integer n5) {
        // cria a lista de apostas e adiciona os 5 numeros
        Set<Integer> listaApostas = new HashSet<Integer>();
        listaApostas.add(n1);
        listaApostas.add(n2);
        listaApostas.add(n3);
        listaApostas.add(n4);
        listaApostas.add(n5);

        // necessario tratar quantidade de numeros, se sao iguais e se sao entre 1 e 50
        if (listaApostas.size() != 5) {
            return "Quantidade de números errada";
        }
        return criarAposta_UC.run(nome, cpf, listaApostas);
    }

    // visualizar lista de apostas
    @GetMapping("/listarapostas")
    public ArrayList<String> listarApostas() {
        ArrayList<String> listaApostas = listarApostas_UC.run();
        if (listaApostas.isEmpty()) {
            listaApostas.add("VAZIA");
        }
        return listaApostas;
    }

    // sortear resultado
    // Fecha o sorteio para novas apostas
    // Sorteia 5 primeiros numeros
    @GetMapping("/sortearresultado")
    public Set<Integer> sortearResultado() {
        return sortearResultado_UC.run();
    }

    // apurar vencedores
    @GetMapping("/apuracao")
    public ArrayList<String> apurarSorteio() {
        ArrayList<String> listaVencedores = apurarSorteio_UC.run();
        if (listaVencedores.isEmpty()) {
            listaVencedores.add("NÃO HÁ VENCEDORES");
        }
        int qtdVencedores = listaVencedores.size();
        listaVencedores.add(0, "São " + qtdVencedores + "vencedores.");
        return listaVencedores;
    }

    @GetMapping("/relatoriosorteio")
    public Map<Integer, Integer> exibirRelatorio() {
        return exibirRelatorio_UC.run();
    }



    @GetMapping("/minhaPagina")
    public String minhaPagina(Model model) {
        model.addAttribute("mensagem", "Olá, Thymeleaf!wwwww");
        return "minhaPagina"; // Nome do arquivo HTML Thymeleaf dentro de src/main/resources/templates
    }
}
