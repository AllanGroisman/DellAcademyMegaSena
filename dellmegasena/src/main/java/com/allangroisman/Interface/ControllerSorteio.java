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

import com.allangroisman.Aplicacao.FaseDeApostas.ApostaSurpresinha_UC;
import com.allangroisman.Aplicacao.FaseDeApostas.CriarAposta_UC;
import com.allangroisman.Aplicacao.FaseDeApostas.ListarTodasApostas_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ApurarSorteio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarQuantidadeRodadas_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarSorteados_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.BuscarQuantidadeVencedores_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ExibirRelatorio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ListarVencedores_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.Manipular_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.EncerrarApostas_UC;
import com.allangroisman.Aplicacao.FaseDeEntrada.CriarSorteio_UC;

@Controller
@RequestMapping("/")
public class ControllerSorteio {

    CriarSorteio_UC novoSorteio_UC; // Implementada - cria novo sorteio

    CriarAposta_UC criarAposta_UC; // Implementada - cria nova aposta normal
    ApostaSurpresinha_UC apostaSurpresinha_UC; // Implementada - cria nova aposta surpresinha

    ListarTodasApostas_UC listarApostas_UC; // Implementado - lista todas as apostas

    EncerrarApostas_UC encerrarApostas_UC; // Implementado fecha a fase de apostas e sorteia os primeiros 5 numeros

    @Autowired
    public ControllerSorteio(CriarSorteio_UC novoSorteio_UC, CriarAposta_UC criarAposta_UC,
            ApostaSurpresinha_UC apostaSurpresinha_UC,
            ListarTodasApostas_UC listarApostas_UC, EncerrarApostas_UC encerrarApostas_UC) {

        this.novoSorteio_UC = novoSorteio_UC; // Implementado, mas o id nao funciona ainda
        this.criarAposta_UC = criarAposta_UC; // Implementado e Testado
        this.apostaSurpresinha_UC = apostaSurpresinha_UC; // Implementado e testad
        this.listarApostas_UC = listarApostas_UC; // Nao tem como ser testado
        this.encerrarApostas_UC = encerrarApostas_UC; // Testado

        System.out.println("\n\nCriado Controller Sorteio\n\n");

    }

    // TELA INICIAL DO
    // PROGRAMA////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public String inicioServicos() {
        return "telaInicial";
    }

    // MENU DE SORTEIO
    // /////////////////////////////////////////////////////////////////////////////////////////
    // Cria um novo sorteio e entrar no menu de sorteio
    @GetMapping("/criarsorteio")
    public String novoSorteio(Model model) {
        String mensagem = novoSorteio_UC.run();
        model.addAttribute("mensagem", mensagem);
        return "sorteio/1menuSorteio";
    }

    // Volta pro menu de sorteio
    @GetMapping("/menusorteio")
    public String menuSorteio(Model model) {
        return "sorteio/1menuSorteio";
    }

    // APOSTAS NORMAIS
    // /////////////////////////////////////////////////////////////////////////////////////////
    // Abrir formulario para inscricao
    @GetMapping("/novaaposta")
    public String novaAposta() {
        return "sorteio/2novaAposta";
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

        model.addAttribute("resposta", criarAposta_UC.run(nome, cpf, listaApostas));
        return "sorteio/2enviarFormulario";
    }

    // APOSTA
    // SURPRESINHA///////////////////////////////////////////////////////////////////////////////////////
    // Abrir formulario SURPRESINHA
    @GetMapping("/surpresinha")
    public String novaApostaSurpresinha() {
        return "sorteio/2novaApostaSurpresinha";
    }

    // Aposta o surpresinha
    @GetMapping("/surpresinha/{nome}/{cpf}")
    public String apostaSurpresinha(@PathVariable("nome") String nome,
            @PathVariable("cpf") String cpf,
            Model model) {
        model.addAttribute("resposta", apostaSurpresinha_UC.run(nome, cpf));
        return "sorteio/2enviarFormulario";
    }

    // LISTAR
    // APOSTAS///////////////////////////////////////////////////////////////////////////////////////////
    // visualizar lista de apostas
    @GetMapping("/listarapostas")
    public String listarApostas(Model model) {

        model.addAttribute("resposta", listarApostas_UC.run());
        return "sorteio/2listarApostas";
    }

    // ENCERRAMENTO DAS
    // APOSTAS////////////////////////////////////////////////////////////////////////////////
    // sortear resultado
    // Fecha o sorteio para novas apostas
    // Sorteia 5 primeiros numeros
    // vai para tela de apuracao
    @GetMapping("/encerrarapostas")
    public String encerrarApostas(Model model) {

        model.addAttribute("resposta", encerrarApostas_UC.run());
        return "apuracao/3encerrarApostas";
    }

}
