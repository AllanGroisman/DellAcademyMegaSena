package com.allangroisman.Persistencia.Sorteio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.allangroisman.Dominio.Entidades.Sorteio;
import com.allangroisman.Dominio.Interface.IRepSorteio;

@Repository
@Primary
public class RepSorteioJPA implements IRepSorteio{
    IRepSorteioJPA repJPA;

    @Autowired
    public RepSorteioJPA(IRepSorteioJPA repJPA) {
        this.repJPA = repJPA;
    }

    @Override
    public void save(Sorteio sorteio) {
        repJPA.save(sorteio);
    }

}
