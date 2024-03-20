package com.allangroisman.Dominio.Servicos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allangroisman.Dominio.Entidades.Aposta;
import com.allangroisman.Dominio.Entidades.Sorteio;
import com.allangroisman.Dominio.Funcoes.Sorteador;

@Service
public class ServicoSorteio {

    private static Sorteio sorteioAtual;

    @Autowired
    public ServicoSorteio() {
        
    }

    public String novoSorteio() {
        // Cria um novo sorteio
        sorteioAtual = new Sorteio();
        return "ID do Sorteio Criado: " + sorteioAtual.getId();
    }

    public String criarAposta(String nome, String cpf, Set<Integer> listaNumeros) {
        if(!sorteioAtual.getAberto()){
            return "Fase de apostas já finalizada.";
        }
        // se foi adicionado quantidade de numeros errada, ja retorna o erro
        if (listaNumeros.size() != 5) {
            return "Quantidade de números incorreta.";
        }

        for (Integer integer : listaNumeros) {
            if(integer <1 || integer > 50){
                return "Os números devem estar entre 1 e 50.";
            }
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

        // Caso a aposta seja inédita, apenas cria uma nova e adiciona na lista de
        // apostas
       // Aposta novaAposta = new Aposta(nome, cpf, listaNumeros);
        sorteioAtual.adicionarAposta( new Aposta(nome, cpf, listaNumeros));
        System.out.println("NOVA APOSTA");
        return "Aposta realizada com sucesso.";
    }

    public ArrayList<String> listarApostas() {
        //pega todas as apostas do sorteio atual e converte para um array de string
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();
        ArrayList<String> listaApostasString = listaApostas.stream()
                .map(Object::toString)
                .collect(Collectors.toCollection(ArrayList::new));
        if(listaApostas.isEmpty()){
            listaApostasString.add("NÁO HÁ APOSTAS");
        }
        return listaApostasString;
    }

    public Set<Integer> sortearResultado() {
        // Cria um set com os numeros sorteados
        Set<Integer> numerosSorteados = sorteioAtual.getNumerosSorteados();
        //se ja foi sorteado os 5 primeiros numeros e fechou o sorteio para novas apostas, retorna os sorteados
        if(!sorteioAtual.getAberto()){
            return numerosSorteados;
        }
        //Se é a primeira vez sorteando
        // Fecha o sorteio para novas apostas
        sorteioAtual.fechar();
        
        // adiciona 5 numeros atraves do sorteador
        for (int i = 0; i < 5; i++) {
            sorteioAtual.adicionarNumeroResultado(Sorteador.sortearNumero(numerosSorteados));
        }
        //retorna os numeros sorteados
        return numerosSorteados;
    }

    public ArrayList<String> apurarSorteio() {
        //se ainda ta na fase de apostas
        if(sorteioAtual.getAberto()){
            ArrayList<String> respostaVazia = new ArrayList<>();
            respostaVazia.add("AINDA EM FASE DE APOSTAS");
            return respostaVazia;
        }
        // procura vencedores
        //se nao há
        int count = 0;
        while (!sorteioAtual.procurarVencedores() && count < 25) { // tenta procurar vencedores até 25 vezes
            sorteioAtual.adicionarNumeroResultado(Sorteador.sortearNumero(sorteioAtual.getNumerosSorteados()));
            count++;
        }

        // Transforma a saída para String genérica
        ArrayList<Aposta> listaVencedores = sorteioAtual.getListaVencedores();
        ArrayList<String> listaVencedoresString = listaVencedores.stream()
                .sorted(Comparator.comparing(aposta -> aposta.getNomeUsuario())) //coloca em ordem alfabetica
                .map(Object::toString) //transforma pra string pra so assim mandar como resposta.
                .collect(Collectors.toCollection(ArrayList::new));
        if(listaVencedoresString.isEmpty()){
            listaVencedoresString.add("NÃO HÁ VENCEDORES!");
        }
        return listaVencedoresString;
    }

    public ArrayList<String> listarApostaUsuario(String cpf) {
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();

        // Filtra a lista pelo cpf e converte pra string
        ArrayList<String> apostasDoUsuario = listaApostas.stream()
                .filter(aposta -> aposta.getCpfUsuario().equals(cpf))
                .map(aposta -> aposta.toString()) 
                .collect(Collectors.toCollection(ArrayList::new));

        if(apostasDoUsuario.isEmpty()){
            apostasDoUsuario.add("NÃO HÁ APOSTAS DO USUÁRIO " + cpf);
        }
        return apostasDoUsuario;
    }

    public Map<Integer, Integer> exibirRelatorio() {
        // cria um mapa zerado com 1 a 50
        Map<Integer, Integer> relatorio = new HashMap<>();
        for (int i = 1; i < 51; i++) {
            relatorio.put(i, 0);

        }
        // busca a lista de apostas
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();
        for (Aposta aposta : listaApostas) {
            // busca os numeros apostados em cada aposta
            Set<Integer> numerosApostados = aposta.getNumerosApostados();
            // pra cada numero apostado, atualiza a contagem dele no relatorio
            for (Integer numero : numerosApostados) {
                relatorio.put(numero, relatorio.get(numero) + 1);
            }
        }
          
        return relatorio;
    }

    public Set<Integer> buscarSorteados() {
        return sorteioAtual.getNumerosSorteados();
    }

    public String apostaSurpresinha(String nome, String cpf) {
        //Cria a lista de numeros
        Set<Integer> listaNumeros = new HashSet<>();
        //Adiciona 5 numeros aleatorios
        for (int i = 0; i < 5; i++) {
            listaNumeros.add(Sorteador.sortearNumero(listaNumeros));           
        }
        //chama a funcao original de apostar com esta lista
        return criarAposta(nome, cpf, listaNumeros);
        
    }

    public int buscarQuantidadeRodadas() {
        return sorteioAtual.getNumerosSorteados().size() - 5;
    }

    public int buscarQuantidadeVencedores() {
        return sorteioAtual.getListaVencedores().size();
    }

    public ArrayList<String> listarVencedores() {
        // Transforma a saída para String genérica
        ArrayList<Aposta> listaVencedores = sorteioAtual.getListaVencedores();
        ArrayList<String> listaVencedoresString = listaVencedores.stream()
                .sorted(Comparator.comparing(aposta -> aposta.getNomeUsuario())) //coloca em ordem alfabetica
                .map(Object::toString) //transforma pra string pra so assim mandar como resposta.
                .collect(Collectors.toCollection(ArrayList::new));
        if(listaVencedoresString.isEmpty()){
            listaVencedoresString.add("NÃO HÁ VENCEDORES!");
        }
        return listaVencedoresString;
    }

    public void manipular() {
        sorteioAtual.manipularResultado();
    }
}
