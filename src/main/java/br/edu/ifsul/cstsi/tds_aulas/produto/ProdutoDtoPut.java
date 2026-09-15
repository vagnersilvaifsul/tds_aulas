package br.edu.ifsul.cstsi.tds_aulas.produto;

import java.math.BigDecimal;

public record ProdutoDtoPut(
        String nome,
        String descricao,
        BigDecimal valorDeCompra,
        BigDecimal valorDeVenda,
        Integer estoque,
        Boolean situacao
) {
    public ProdutoDtoPut(Produto produto) {
        this(produto.getNome(), produto.getDescricao(), produto.getValorDeCompra(), produto.getValorDeVenda(), produto.getEstoque(), produto.getSituacao());
    }
}
