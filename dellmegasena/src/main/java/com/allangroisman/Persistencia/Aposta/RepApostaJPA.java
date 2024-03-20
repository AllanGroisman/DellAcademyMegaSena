package com.allangroisman.Persistencia.Aposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.allangroisman.Dominio.Entidades.Aposta;
import com.allangroisman.Dominio.Entidades.Sorteio;
import com.allangroisman.Dominio.Interface.IRepAposta;

@Repository
@Primary
public class RepApostaJPA implements IRepAposta{
    IRepApostaJPA repJPA;

    @Autowired
    public RepApostaJPA(IRepApostaJPA repJPA) {
        this.repJPA = repJPA;
    }

   @Override
    public void save(Aposta aposta) {
        repJPA.save(aposta);
    }
    
}
