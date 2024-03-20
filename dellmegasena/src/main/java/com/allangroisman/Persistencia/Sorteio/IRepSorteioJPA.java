package com.allangroisman.Persistencia.Sorteio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.allangroisman.Dominio.Entidades.Sorteio;

public interface IRepSorteioJPA extends CrudRepository<Sorteio,Long>{
    List<Sorteio> findAll();
}
