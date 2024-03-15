package com.allangroisman.Dominio.Entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Sorteio {

    private long id; //id unico do sorteio
    private LocalDateTime dataCriacao; //data em que o sorteio foi criado
    private ArrayList<Long> listaApostas = new ArrayList<>(); //lista de todos os ids das apostas feitas neste sorteio
    private Set<Integer> numerosSorteados = new HashSet<>(); //numeros sorteados, pode ter os 5 iniciais + 25 outros
    private Set<Long> ganhadores = new HashSet<>(); //id dos ganhadores, se não tiver, ninguem ganhou
   
    public Sorteio(long id, LocalDateTime dataCriacao) {
        this.id = id;
        this.dataCriacao = dataCriacao;
    }

    
    //Gets
    public long getId() {
        return id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public ArrayList<Long> getListaApostas() {
        return listaApostas;
    }

    public Set<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }

    public Set<Long> getGanhadores() {
        return ganhadores;
    }

    //Set
    public void adicionarGanhador(Long ganhador) {
        this.ganhadores.add(ganhador);
    }

    public boolean adicionarNumeroSorteado(int numeroSorteado) {
        
        //Se o numero ja foi sorteado, 
        if(numerosSorteados.contains(numeroSorteado)){ 
            return false;
        }

        this.numerosSorteados.add(numeroSorteado);
        return true;

    }


    @Override
    public String toString() {
        return "Sorteio [id=" + id + ", dataCriacao=" + dataCriacao + ", listaApostas=" + listaApostas
                + ", numerosSorteados=" + numerosSorteados + ", ganhadores=" + ganhadores + "]";
    }

}
