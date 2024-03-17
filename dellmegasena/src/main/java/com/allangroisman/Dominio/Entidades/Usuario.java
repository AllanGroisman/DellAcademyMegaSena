package com.allangroisman.Dominio.Entidades;

public class Usuario {
    
    private String cpf;
    private String nome;

    //Construtor padrão
    public Usuario(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    //gets
    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Usuario cpf=" + cpf + ", nome=" + nome + "]";
    }    
}
