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
import com.allangroisman.Aplicacao.FaseDeApuracao.SortearResultado_UC;
import com.allangroisman.Aplicacao.FaseDeEntrada.CriarSorteio_UC;

@Controller
@RequestMapping("/")
public class ControllerSorteio {

    CriarSorteio_UC novoSorteio_UC; // cria novo sorteio
    CriarAposta_UC criarAposta_UC; // cria nova aposta
    ApostaSurpresinha_UC apostaSurpresinha_UC; // cria nova aposta surpresinha
    ListarTodasApostas_UC listarApostas_UC; // lista todas as apostas

    SortearResultado_UC sortearResultado_UC; // faz o primeiro sorteio de 5 numeros
    ApurarSorteio_UC apurarSorteio_UC; // apura e sorteia numeros ate ter um vencedor
    
    BuscarSorteados_UC buscarSorteados_UC; // busca os numeros sorteados
    BuscarQuantidadeRodadas_UC buscarQuantidadeRodadas_UC; // busca quantas rodadas teve ate sair o vencedor
    BuscarQuantidadeVencedores_UC buscarQuantidadeVencedores_UC; // busca a lista de vencedores
    ExibirRelatorio_UC exibirRelatorio_UC; //gera o relatorio de todos os numeros apostados
    ListarVencedores_UC listarVencedores_UC; //busca a lista de vencedores

    @Autowired
    public ControllerSorteio(CriarSorteio_UC novoSorteio_UC, CriarAposta_UC criarAposta_UC,
            ListarTodasApostas_UC listarApostas_UC, SortearResultado_UC sortearResultado_UC,
            ApurarSorteio_UC apurarSorteio_UC, ExibirRelatorio_UC exibirRelatorio_UC,
            BuscarSorteados_UC buscarSorteados_UC, ApostaSurpresinha_UC apostaSurpresinha_UC,
            BuscarQuantidadeRodadas_UC buscarQuantidadeRodadas_UC, BuscarQuantidadeVencedores_UC buscarQuantidadeVencedores_UC,
            ListarVencedores_UC listarVencedores_UC) {

        this.novoSorteio_UC = novoSorteio_UC; // Implementado, mas o id nao funciona ainda
        this.criarAposta_UC = criarAposta_UC; // Implementado e Testado
        this.listarApostas_UC = listarApostas_UC; // Nao tem como ser testado

        this.sortearResultado_UC = sortearResultado_UC; // Testado
        this.apurarSorteio_UC = apurarSorteio_UC;
        this.buscarSorteados_UC = buscarSorteados_UC;
        this.buscarQuantidadeRodadas_UC = buscarQuantidadeRodadas_UC;
        this.buscarQuantidadeVencedores_UC = buscarQuantidadeVencedores_UC;
        this.listarVencedores_UC = listarVencedores_UC;
        

        this.exibirRelatorio_UC = exibirRelatorio_UC;
        
        this.apostaSurpresinha_UC = apostaSurpresinha_UC; //Implementado e testado

        

        System.out.println("\n\nCriado Controller MegaSena\n\n");

    }

    // TELA INICIAL DO PROGRAMA
    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public String inicioServicos() {
        return "telaInicial";
    }
    //MENU DE SORTEIO /////////////////////////////////////////////////////////////////////////////////////////
    // Cria um novo sorteio e entra no menu de sorteio
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

    //MENU DE APOSTAS/////////////////////////////////////////////////////////////////////////////////////////
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

    //LISTAR APOSTAS///////////////////////////////////////////////////////////////////////////////////////////
    // visualizar lista de apostas
    @GetMapping("/listarapostas")
    public String listarApostas(Model model) {

        model.addAttribute("resposta", listarApostas_UC.run());
        return "sorteio/2listarApostas";
    }

    //ENCERRAMENTO DAS APOSTAS E SORTEAMENTO DO RESULTADO
    // sortear resultado
    // Fecha o sorteio para novas apostas
    // Sorteia 5 primeiros numeros
    @GetMapping("/encerrarapostas")
    public String encerrarApostas(Model model) {

        model.addAttribute("resposta", sortearResultado_UC.run());
        return "apuracao/3encerrarApostas";
    }
    //FASE DE APURACAO/////////////////////////////////////////////////////////////////////////////////////////
    // apurar vencedores e entao exibir
    @GetMapping("/apuracao")
    public String apurarSorteio(Model model) {
        apurarSorteio_UC.run();
        model.addAttribute("sorteados", buscarSorteados_UC.run()); //numeros sorteados
        model.addAttribute("rodadas", buscarQuantidadeRodadas_UC.run()); //quantidade de rodadas
        model.addAttribute("vencedores", buscarQuantidadeVencedores_UC.run()); //quantidade de vencedores
        return "apuracao/3apuracao";
    }
    //apenas exibir
    @GetMapping("/exibirapuracao")
    public String exibirApuracao(Model model) {
        model.addAttribute("sorteados", buscarSorteados_UC.run()); //numeros sorteados
        model.addAttribute("rodadas", buscarQuantidadeRodadas_UC.run()); //quantidade de rodadas
        model.addAttribute("vencedores", buscarQuantidadeVencedores_UC.run()); //quantidade de vencedores
        return "apuracao/3apuracao";
    }
    //exibir relatorio que volta para o exibir apuracao
    @GetMapping("/gerarrelatorio")
    public String gerarRelatorio(Model model) {
        model.addAttribute("relatorio", exibirRelatorio_UC.run());
        return "apuracao/4exibirrelatorio";
    }
   
    //exibir lista de vencedores
    @GetMapping("/listarvencedores")
    public String listarVencedores(Model model) {
        model.addAttribute("vencedores", listarVencedores_UC.run());
        return "apuracao/4listarvencedores";
    }


}
