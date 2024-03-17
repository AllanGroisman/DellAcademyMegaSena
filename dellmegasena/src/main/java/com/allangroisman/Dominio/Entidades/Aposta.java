package com.allangroisman.Dominio.Entidades;

import java.util.HashSet;
import java.util.Set;

public class Aposta {
    private static long countId = 1000; 

    private long id; //começa em 1000
    private long idSorteio;
    private String cpf; //pode ser comum entre apostas
    private Set<Integer> numerosApostados = new HashSet<>(); //tem que ter 5 números por aposta

    //Construtor padrão
    public Aposta(String cpf, Set<Integer> numerosApostados) {
        this.id = countId; //atribui o proximo id
        countId++; //acrescenta 1 para o prox sorteio
        this.cpf = cpf;
        this.numerosApostados = numerosApostados;
    }

    //Gets
    public long getId() {
        return id;
    }

    public long getIdSorteio(){
        return idSorteio;
    }
    public String getCpfUsuario() {
        return cpf;
    }

    public Set<Integer> getNumerosApostados() {
        return numerosApostados;
    }

    @Override
    public String toString() {
        return "Aposta [id=" + id + ", idUsuario=" + cpf + ", numerosApostados=" + numerosApostados + "]";
    }
    
}
