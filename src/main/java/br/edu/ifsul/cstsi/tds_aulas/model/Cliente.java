package br.edu.ifsul.cstsi.tds_aulas.model;

import java.util.Collection;

public class Cliente { //UserDetails usuário padrão do Spring Boot
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean isConfirmado = false;
    private Byte situacao;

    //Associações
    private Collection<Pedido> pedidos;

}
