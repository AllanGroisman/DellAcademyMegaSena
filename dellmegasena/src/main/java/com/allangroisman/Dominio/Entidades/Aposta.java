package com.allangroisman.Dominio.Entidades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Aposta {
    @Id
    private long id; //começa em 1000
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sorteio_id")
    private Sorteio sorteio;
    
    private boolean vencedora = false;
    
   
    private String nomeUsuario;
    private String cpfUsuario; //pode ser comum entre apostas

    private Set<Integer> numerosApostados = new HashSet<>(); //tem que ter 5 números por aposta

    //Construtor padrão
    @Autowired
    public Aposta(Long id, String nomeUsuario, String cpf, Set<Integer> numerosApostados) {
        this.id = id; //atribui o proximo id
        this.nomeUsuario = nomeUsuario;
        this.cpfUsuario = cpf;
        this.numerosApostados = numerosApostados;
        System.out.println("NOVA APOSTA CRIADA" + this.id);
    }
    public Aposta() {
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

    public void setSorteio(Sorteio sorteioAtual) {
        this.sorteio = sorteioAtual;
    }

    public void setVencedora() {
        vencedora = true;
    }

    public boolean getVencedora() {
        return vencedora;
    }
    @Override
    public String toString() {
        return "Aposta: " + id + ", Sorteio: "+ sorteio.getId() +", Usuário: " + nomeUsuario + ", CPF: " + cpfUsuario
                + ", Números: " + numerosApostados.stream().sorted().toList() + ".";
    }
    
}
