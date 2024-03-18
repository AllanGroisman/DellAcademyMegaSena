package com.allangroisman.Dominio.Entidades;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Aposta {
     

    @Id
    private long id; //começa em 1000
    private static long countId = 1000;
    
    private String nomeUsuario;
    private String cpfUsuario; //pode ser comum entre apostas

    private Set<Integer> numerosApostados = new HashSet<>(); //tem que ter 5 números por aposta

    //Construtor padrão
    public Aposta(String nomeUsuario, String cpf, Set<Integer> numerosApostados) {
        this.id = countId; //atribui o proximo id
        countId++; //acrescenta 1 para o prox sorteio
        this.nomeUsuario = nomeUsuario;
        this.cpfUsuario = cpf;
        this.numerosApostados = numerosApostados;
    }

    //Gets
    public long getId() {
        return id;
    }
   
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public Set<Integer> getNumerosApostados() {
        return numerosApostados;
    }

    @Override
    public String toString() {
        return "Aposta [id=" + id + ", nomeUsuario=" + nomeUsuario + ", cpfUsuario=" + cpfUsuario
                + ", numerosApostados=" + numerosApostados + "]";
    }

   
    
}
