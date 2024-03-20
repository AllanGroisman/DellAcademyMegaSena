package com.allangroisman.Dominio.Interface;

import java.util.List;

import com.allangroisman.Dominio.Entidades.Sorteio;

public interface IRepSorteio {
    void save(Sorteio sorteio);
    List<Sorteio> all();
}
