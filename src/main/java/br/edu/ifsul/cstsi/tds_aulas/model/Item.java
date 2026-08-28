package br.edu.ifsul.cstsi.tds_aulas.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal quantidade;
    private BigDecimal totalItem;
    private Byte situacao;

    //Associações
    @ManyToOne(fetch = FetchType.LAZY)
    private Pedido pedido;
    @ManyToOne(fetch = FetchType.EAGER)
    private Produto produto;
}
