package com.allangroisman.Dominio.Entidades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Aposta {
    @Id
    private long id; //começa em 1000
    private static long countId = 1000;
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sorteio_id")
    private Sorteio sorteio;
    
    private boolean vencedora = false;
    
    public Aposta() {
    }

    private String nomeUsuario;
    private String cpfUsuario; //pode ser comum entre apostas

    private Set<Integer> numerosApostados = new HashSet<>(); //tem que ter 5 números por aposta

    //Construtor padrão
    @Autowired
    public Aposta(String nomeUsuario, String cpf, Set<Integer> numerosApostados) {
        this.id = countId; //atribui o proximo id
        countId++; //acrescenta 1 para o prox sorteio
        this.nomeUsuario = nomeUsuario;
        this.cpfUsuario = cpf;
        this.numerosApostados = numerosApostados;
        System.out.println("NOVA APOSTA CRIADA" + this.id);
    }

    // //Gets
    // public long getIdOriginal() {
    //     return idOriginal;
    // }
   
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
        return "Nome: " + nomeUsuario + ". Cpf:" + cpfUsuario + ". Aposta " +". Números apostados: " + numerosApostados + "\n";
    }

    public void setSorteio(Sorteio sorteioAtual) {
        this.sorteio = sorteioAtual;
    }

    public void setVencedora() {
        vencedora = true;
    }

    public boolean getVencedora() {
        return vencedora;
    }

    

   
    
}
