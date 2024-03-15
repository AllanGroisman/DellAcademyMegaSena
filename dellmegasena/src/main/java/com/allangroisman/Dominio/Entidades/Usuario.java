package com.allangroisman.Dominio.Entidades;

public class Usuario {
    private long id;
    private String cpf;
    private String nome;

    //Construtor padrão
    public Usuario(long id, String cpf, String nome) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
    }

    //Gets
    public long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", cpf=" + cpf + ", nome=" + nome + "]";
    }
    
    
}
