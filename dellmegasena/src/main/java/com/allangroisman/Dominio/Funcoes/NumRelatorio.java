package com.allangroisman.Dominio.Funcoes;

public class NumRelatorio {
    private int numero;
    private int repeticoes;

    public NumRelatorio(int numero, int repeticoes) {
        this.numero = numero;
        this.repeticoes = repeticoes;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public void contarRepeticao() {
        this.repeticoes++;
    }

}
