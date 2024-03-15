package com.allangroisman.Dominio.Entidades;

import java.util.HashSet;
import java.util.Set;

public class Aposta {
    
    private long id; //começa em 1000
    private long idUsuario; //pode ser comum entre apostas
    private Set<Integer> numerosApostados = new HashSet<>(); //tem que ter 5 números por aposta

    //Construtor padrão
    public Aposta(long id, long usuario, Set<Integer> numerosApostados) {
        this.id = id;
        this.idUsuario = usuario;
        this.numerosApostados = numerosApostados;
    }

    //Gets
    public long getId() {
        return id;
    }

    public long getUsuario() {
        return idUsuario;
    }

    public Set<Integer> getNumerosApostados() {
        return numerosApostados;
    }

    @Override
    public String toString() {
        return "Aposta [id=" + id + ", idUsuario=" + idUsuario + ", numerosApostados=" + numerosApostados + "]";
    }
    
}
