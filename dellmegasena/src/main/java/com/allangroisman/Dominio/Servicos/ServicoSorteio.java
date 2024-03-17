package com.allangroisman.Dominio.Servicos;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allangroisman.Dominio.Entidades.Aposta;
import com.allangroisman.Dominio.Entidades.Sorteio;
import com.allangroisman.Dominio.Funcoes.Sorteador;

@Service
public class ServicoSorteio {
    // private IRepApostasJPA repApostas;
    // private IRepSorteiosJPA repSorteios;
    // private IRepUsuariosJPA repUsuarios;

    private static Sorteio sorteioAtual;

    @Autowired
    public ServicoSorteio() {
        sorteioAtual = new Sorteio();
    }
    // public ServicoSorteio(IRepApostasJPA repApostas, IRepSorteiosJPA repSorteios,
    // IRepUsuariosJPA repUsuarios) {
    // this.repApostas = repApostas;
    // this.repSorteios = repSorteios;
    // this.repUsuarios = repUsuarios;
    // }

    public String novoSorteio() {
        //sorteioAtual = new Sorteio();
        return "Sorteio Criado" + sorteioAtual.toString();
    }

    public String criarAposta(String nome, String cpf, Set<Integer> listaNumeros) {
        // se foi adicionado quantidade de numeros errada, ja retorna o erro
        if (listaNumeros.size() != 5) {
            return "Quantidade de números incorreta";
        }

        // Busca a lista de apostas ja realizadas
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();

        // Testa se esse usuario esta fazendo uma aposta repetida
        for (Aposta aposta : listaApostas) {
            if (aposta.getCpfUsuario().equals(cpf)) {
                if (aposta.getNumerosApostados().containsAll(listaNumeros)) {
                    return "Aposta já realizada pelo usuário";
                }
            }
        }

        // Caso a aposta seja inédita, cria uma nova e adiciona na lista de apostas
        Aposta novaAposta = new Aposta(cpf, listaNumeros);
        sorteioAtual.adicionarAposta(novaAposta);
        return "Aposta " + novaAposta.toString() + "realizada com sucesso.";
    }

    public ArrayList<String> listarAposta() {
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();
        ArrayList<String> listaApostasString = listaApostas.stream()
                .map(Object::toString)
                .collect(Collectors.toCollection(ArrayList::new));
        return listaApostasString;
    }

    public Set<Integer> sortearResultado() {
        // Fecha o sorteio para novas apostas
        sorteioAtual.fechar();
        // Cria um set com os numeros sorteados
        Set<Integer> numerosSorteados = sorteioAtual.getNumerosSorteados();
        // adiciona 5 numeros atraves do sorteador
        for (int i = 0; i < 5; i++) {
            sorteioAtual.adicionarNumeroResultado(Sorteador.sortearNumero(numerosSorteados));
        }
        return numerosSorteados;
    }

    public ArrayList<String> apurarSorteio() {
        // procura vencedores
        int count = 0;
        while (!sorteioAtual.procurarVencedores() || count < 25) { // tenta procurar vencedores até 25 vezes
            sorteioAtual.adicionarNumeroResultado(Sorteador.sortearNumero(sorteioAtual.getNumerosSorteados()));                                                                                
            count++;
        }

        // Transforma a saída para String genérica
        ArrayList<Aposta> listaVencedores = sorteioAtual.getListaVencedores();
        ArrayList<String> listaVencedoresString = listaVencedores.stream()
                .map(Object::toString)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(sorteioAtual.getNumerosSorteados()); // TIRAR
        return listaVencedoresString;
    }
}
