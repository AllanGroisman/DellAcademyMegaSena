package com.allangroisman.Dominio.Interface;

import java.util.List;

import com.allangroisman.Dominio.Entidades.Aposta;

public interface IRepAposta {
    void save(Aposta aposta);
    List<Aposta> all();    
}
