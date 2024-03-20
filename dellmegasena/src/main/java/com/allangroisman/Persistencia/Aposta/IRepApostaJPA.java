package com.allangroisman.Persistencia.Aposta;

import org.springframework.data.repository.CrudRepository;

import com.allangroisman.Dominio.Entidades.Aposta;

public interface IRepApostaJPA extends CrudRepository<Aposta,Long> {
    
}
