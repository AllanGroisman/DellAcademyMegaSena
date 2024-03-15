package com.allangroisman.Dominio.Servicos;

import com.allangroisman.Persistencia.Apostas.IRepApostasJPA;
import com.allangroisman.Persistencia.Sorteios.IRepSorteiosJPA;
import com.allangroisman.Persistencia.Usuarios.IRepUsuariosJPA;

@Service
public class ServicoAposta {
    private IRepApostasJPA repApostas;
    private IRepSorteiosJPA repSorteios;
    private IRepUsuariosJPA repUsuarios;

    @AutoWired
    public ServicoAposta(IRepApostasJPA repApostas, IRepSorteiosJPA repSorteios, IRepUsuariosJPA repUsuarios) {
        this.repApostas = repApostas;
        this.repSorteios = repSorteios;
        this.repUsuarios = repUsuarios;
    }

    public String novaAposta(){
        
    }




}



