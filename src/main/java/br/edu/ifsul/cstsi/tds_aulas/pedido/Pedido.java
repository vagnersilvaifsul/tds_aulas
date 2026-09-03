package br.edu.ifsul.cstsi.tds_aulas.pedido;

import br.edu.ifsul.cstsi.tds_aulas.cliente.Cliente;
import br.edu.ifsul.cstsi.tds_aulas.item.Item;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pagamento;
    private String estado;
    private LocalDate dataCriacao;
    private LocalDate dataModificacao;
    private Byte situacao;
    private BigDecimal totalPedido;

    //Associações
    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private Collection<Item> items;
    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente cliente;
}
