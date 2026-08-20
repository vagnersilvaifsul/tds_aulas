package br.edu.ifsul.cstsi.tds_aulas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public class Pedido {
    private Long id;
    private String pagamento;
    private String estado;
    private LocalDate dataCriacao;
    private LocalDate dataModificacao;
    private Byte situacao;
    private BigDecimal totalPedido;

    //Associações
    private Collection<Item> items;

    private Cliente cliente;
}
