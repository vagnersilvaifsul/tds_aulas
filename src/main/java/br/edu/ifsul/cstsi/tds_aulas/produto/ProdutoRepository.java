package br.edu.ifsul.cstsi.tds_aulas.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    //Método em Domain Speak (só pelo nome do método o Spring Data irá injetar o SQL em tempo de execução)
    Optional<List<Produto>> findByNomeStartingWith(String nome);

    //Método em Query Speak (em JPQL). Note o nome da entidade Produto no JPQL, isso mostra que ele roda no objeto JPA.
    @Query("SELECT p FROM Produto p WHERE p.nome LIKE CONCAT(?1, '%')")
    Optional<List<Produto>> findByNomeQuerySpeakJPQL(String nome);

    //Método em Query Speak (em SQL). Note que utiliza o tradicional SQl, inclusive o nome da tabela é a que está no banco de dados.
    @Query(value = "SELECT p.* FROM produtos p WHERE p.nome LIKE CONCAT(?1, '%') ORDER BY p.nome", nativeQuery = true)
    Optional<List<Produto>> findByNomeQuerySpeakSQL(String nome);
}