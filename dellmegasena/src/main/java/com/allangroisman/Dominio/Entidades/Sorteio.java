package com.allangroisman.Dominio.Entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Sorteio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "sorteio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aposta> apostas = new ArrayList<>();

    // private static int countId = 0;
    // private int idOriginal; // id unico do sorteio
    private LocalDateTime dataCriacao; // data em que o sorteio foi criado

    // private ArrayList<Aposta> listaApostas = new ArrayList<>(); // lista de todos
    // os ids das apostas feitas

    private Set<Integer> numerosSorteados = new HashSet<>(); // numeros sorteados, pode ter os 5 iniciais + 25 outros

    // private ArrayList<Aposta> listaVencedores = new ArrayList<>(); // se nao
    // tiver nada ninguem ganhou

    private boolean aberto = true;

    public Sorteio() {
        this.dataCriacao = LocalDateTime.now(); // autoexplicaTIVO
        // this.idOriginal = countId;
        /// countId++;
        System.out.println("NOVO SORTEIO CRIADO /////////////////////////////////" + this.id);
    }

    // // Gets
    // public int getIdOriginal() {
    // return idOriginal;
    // }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    // public ArrayList<Aposta> getListaApostas() {
    // return listaApostas;
    // }

    public Set<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }

    // public ArrayList<Aposta> getListaVencedores() {
    // return listaVencedores;
    // }

    public boolean getAberto() {
        return aberto;
    }

    // FUNÇÕES DE APOSTA
    // adiciona uma nova aposta
    // public void adicionarAposta(Aposta novaAposta) {
    // listaApostas.add(novaAposta);
    // }

    // FUNÇÕES DE SORTEIO/APURAÇÃO
    // fecha o sorteio para novas apostas
    public void fechar() {
        this.aberto = false;
    }

    // Adiciona um numero extra ao resultado
    public void adicionarNumeroResultado(int numeroSorteado) { // sorteia mais um numero ao resultado
        numerosSorteados.add(numeroSorteado);
    }

    // procura se tem vencedores no sorteio e os adiciona a lista de vencedores
    // public boolean procurarVencedores() {
    // // itera sobre a lista de apostas procurando match dos cinco apostados com os
    // 5
    // // ou mais sorteados
    // boolean achou = false;
    // for (Aposta aposta : listaApostas) {
    // Set<Integer> numerosApostados = aposta.getNumerosApostados();
    // // Verifica se a aposta contém todos os números do resultado do sorteio
    // if (numerosApostados.containsAll(numerosSorteados)) {
    // listaVencedores.add(aposta);
    // achou = true;
    // }
    // }
    // return achou;
    // }

    public void manipularResultado() {
        Set<Integer> numerosManipulados = Set.of(1, 2, 3, 4, 5);

        this.numerosSorteados = numerosManipulados;
    }

    public Long getId() {
        return this.id;
    }

    public void addAposta(Aposta novaAposta) {
        this.apostas.add(novaAposta);
    }

    public List<Aposta> getListaApostas() {
        return this.apostas;
    }

    public void setNumerosSorteados(Set<Integer> numerosSorteados) {
        this.numerosSorteados = numerosSorteados;
    }

    // @Override
    // public String toString() {
    // return "Sorteio [id=" + idOriginal + ", dataCriacao=" + dataCriacao + ",
    // listaApostas=" + listaApostas
    // + ", numerosSorteados=" + numerosSorteados + ", ganhadores=" +
    // listaVencedores + ", quantidade numeros: "+ numerosSorteados.size() +"]";
    // }

}