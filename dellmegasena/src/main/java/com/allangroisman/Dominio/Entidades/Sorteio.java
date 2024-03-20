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

    private LocalDateTime dataCriacao; // data em que o sorteio foi criado

    private Set<Integer> numerosSorteados = new HashSet<>(); // numeros sorteados, pode ter os 5 iniciais + 25 outros

    private boolean aberto = true;

    private int qtdVencedores = 0;

    public Sorteio() {
        this.dataCriacao = LocalDateTime.now(); // autoexplicaTIVO
        System.out.println("NOVO SORTEIO CRIADO /////////////////////////////////" + this.id);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Set<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }

    public boolean getAberto() {
        return aberto;
    }

    // FUNÇÕES DE SORTEIO/APURAÇÃO
    // fecha o sorteio para novas apostas
    public void fechar() {
        this.aberto = false;
    }

    // Adiciona um numero extra ao resultado
    public void adicionarNumeroResultado(int numeroSorteado) { // sorteia mais um numero ao resultado
        numerosSorteados.add(numeroSorteado);
    }

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

    public boolean procurarVencedores() {
        for (Aposta aposta : apostas) {
            if(this.numerosSorteados.containsAll(aposta.getNumerosApostados())){
                aposta.setVencedora();
                this.qtdVencedores++;
            }
        }
        if(qtdVencedores > 0){return true;}
        return false;
    }

    public int getQtdVencedores() {
        return this.qtdVencedores;
    }

    @Override
    public String toString() {
        return "Sorteio: " + id + ", Data: " + dataCriacao + " Resultado: "
                + numerosSorteados + ", Status: " + aberto + ", Qtd Vencedores: " + qtdVencedores;
    }

}