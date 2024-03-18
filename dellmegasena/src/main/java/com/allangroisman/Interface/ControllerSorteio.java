package com.allangroisman.Interface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allangroisman.Aplicacao.FaseDeApostas.CriarAposta_UC;
import com.allangroisman.Aplicacao.FaseDeApostas.ListarApostas_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.ApurarSorteio_UC;
import com.allangroisman.Aplicacao.FaseDeApuracao.SortearResultado_UC;
import com.allangroisman.Aplicacao.FaseDeEntrada.CriarSorteio_UC;

@RestController
@RequestMapping("/")
public class ControllerSorteio {

    CriarSorteio_UC novoSorteio_UC;
    CriarAposta_UC criarAposta_UC;
    ListarApostas_UC listarApostas_UC;
    SortearResultado_UC sortearResultado_UC;
    ApurarSorteio_UC apurarSorteio_UC;

    @Autowired
    public ControllerSorteio(CriarSorteio_UC novoSorteio_UC, CriarAposta_UC criarAposta_UC,
            ListarApostas_UC listarApostas_UC, SortearResultado_UC sortearResultado_UC, ApurarSorteio_UC apurarSorteio_UC) {

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

    // Ao iniciar, cria um novo sorteio
    @GetMapping("/criarsorteio")
    public String novoSorteio() {
        return novoSorteio_UC.run();
    }

    // criar nova aposta com dados do usuario
    @GetMapping("/novaaposta/{cpf}")
    public String novaAposta(@PathVariable("cpf") String cpf) {
        Set<Integer> listaApostas = new HashSet<Integer>();
        listaApostas.add(10);
        listaApostas.add(11);
        listaApostas.add(12);
        listaApostas.add(13);
        listaApostas.add(15);

        if(listaApostas.size() != 5){
            return "Quantidade de números errada";
        }
        return criarAposta_UC.run("CARA", cpf, listaApostas);
    }

    // visualizar lista de apostas
    @GetMapping("/listarapostas")
    public ArrayList<String> listarApostas() {
        ArrayList<String> listaApostas = listarApostas_UC.run();
        if(listaApostas.isEmpty()){
            listaApostas.add("VAZIA");
        }
        return listaApostas;
    }

    // sortear resultado
    @GetMapping("/sortearresultado")
    public Set<Integer> sortearResultado(){
        return sortearResultado_UC.run();
    }
    // apurar vencedores
    @GetMapping("/apuracao")
    public ArrayList<String> apurarSorteio() {
        ArrayList<String> listaVencedores = apurarSorteio_UC.run();
        if(listaVencedores.isEmpty()){
            listaVencedores.add("NÃO HÁ VENCEDORES");
        }
        return listaVencedores;
    }

}
