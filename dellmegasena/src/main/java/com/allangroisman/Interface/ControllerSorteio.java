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
import org.springframework.ui.Model;


import com.allangroisman.Aplicacao.FaseDeApostas.CriarAposta_UC;
import com.allangroisman.Aplicacao.FaseDeApostas.ListarTodasApostas_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ApurarSorteio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarSorteados_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ExibirRelatorio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.SortearResultado_UC;
import com.allangroisman.Aplicacao.FaseDeEntrada.CriarSorteio_UC;


@Controller
@RequestMapping("/")
public class ControllerSorteio {

    CriarSorteio_UC novoSorteio_UC; //esta criando 2 
    CriarAposta_UC criarAposta_UC; //IMPLEMENTADO
    ListarTodasApostas_UC listarApostas_UC; //IMPLEMENTADO
    SortearResultado_UC sortearResultado_UC; //quase
    ApurarSorteio_UC apurarSorteio_UC;
    ExibirRelatorio_UC exibirRelatorio_UC;
    BuscarSorteados_UC buscarSorteados_UC;

    @Autowired
    public ControllerSorteio(CriarSorteio_UC novoSorteio_UC, CriarAposta_UC criarAposta_UC,
            ListarTodasApostas_UC listarApostas_UC, SortearResultado_UC sortearResultado_UC,
            ApurarSorteio_UC apurarSorteio_UC, ExibirRelatorio_UC exibirRelatorio_UC,
            BuscarSorteados_UC buscarSorteados_UC) {

        this.novoSorteio_UC = novoSorteio_UC;
        this.criarAposta_UC = criarAposta_UC;
        this.listarApostas_UC = listarApostas_UC;
        this.sortearResultado_UC = sortearResultado_UC;
        this.apurarSorteio_UC = apurarSorteio_UC;
        this.exibirRelatorio_UC = exibirRelatorio_UC;
        this.buscarSorteados_UC = buscarSorteados_UC;

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
    public String criarAposta() {
        return "criarAposta";
    }

    // criar nova aposta com dados do usuario
    @GetMapping("/novaaposta/{nome}/{cpf}/{n1}/{n2}/{n3}/{n4}/{n5}")
    public String submeterNovaAposta(
            @PathVariable("nome") String nome,
            @PathVariable("cpf") String cpf,
            @PathVariable("n1") Integer n1,
            @PathVariable("n2") Integer n2,
            @PathVariable("n3") Integer n3,
            @PathVariable("n4") Integer n4,
            @PathVariable("n5") Integer n5,
            Model model) {
        // cria a lista de apostas e adiciona os 5 numeros
        Set<Integer> listaApostas = new HashSet<Integer>();
        listaApostas.add(n1);
        listaApostas.add(n2);
        listaApostas.add(n3);
        listaApostas.add(n4);
        listaApostas.add(n5);

        model.addAttribute("mensagem", criarAposta_UC.run(nome, cpf, listaApostas));
        return "submeterFormulario";
    }

    // visualizar lista de apostas
    @GetMapping("/listarapostas")
    public String listarApostas(Model model) {
        
        model.addAttribute("mensagem", listarApostas_UC.run());
        return "listaApostas";
    }

    // sortear resultado
    // Fecha o sorteio para novas apostas
    // Sorteia 5 primeiros numeros
    @GetMapping("/sortear")
    public String sortearResultado(Model model) {

        model.addAttribute("mensagem",sortearResultado_UC.run());
        return "resultadoSorteado";
    }

    // apurar vencedores
    @GetMapping("/apuracao")
    public String apurarSorteio(Model model) {

        model.addAttribute("mensagem",apurarSorteio_UC.run());
        return "apuracao";
    }

    @GetMapping("/numerossorteados")
    public String buscarSorteados(Model model) {

        model.addAttribute("mensagem",buscarSorteados_UC.run());
        return "numerosSorteados";
    }

    @GetMapping("/relatoriosorteio")
    public String exibirRelatorio(Model model) {
        model.addAttribute("mensagem",exibirRelatorio_UC.run());
        return "relatorio";
    }


    //EXEMPLO
    @GetMapping("/minhaPagina")
    public String minhaPagina(Model model) {
        model.addAttribute("mensagem", "Olá, Thymeleaf!wwwww");
        return "minhaPagina"; // Nome do arquivo HTML Thymeleaf dentro de src/main/resources/templates
    }
}
