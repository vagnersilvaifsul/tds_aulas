package br.edu.ifsul.cstsi.tds_aulas.produto;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProdutoDtoResponse(Long id, String nome, BigDecimal valorDeVenda, String descricao,
                                 Integer estoque) implements Serializable {
  public ProdutoDtoResponse(Produto produto){
    this(produto.getId(), produto.getNome(), produto.getValorDeVenda(), produto.getDescricao(), produto.getEstoque());
  }
}