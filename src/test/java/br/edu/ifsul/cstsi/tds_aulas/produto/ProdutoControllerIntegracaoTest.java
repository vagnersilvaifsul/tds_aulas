package br.edu.ifsul.cstsi.tds_aulas.produto;

import br.edu.ifsul.cstsi.tds_aulas.BaseAPIIntegracaoTest;
import br.edu.ifsul.cstsi.tds_aulas.TdsAulasApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TdsAulasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
//carrega o Context do app em um container Spring Boot com um servidor web
@ActiveProfiles("test") //indica o profile que o Spring Boot deve utilizar para passar os testes
public class ProdutoControllerIntegracaoTest extends BaseAPIIntegracaoTest {

    //Métodos utilitários (eles encapsulam o TestRestTemplate e eliminam a repetição de código nos casos de teste)
    private ResponseEntity<ProdutoDtoResponse> getProduto(String url) {
        return get(url, ProdutoDtoResponse.class);
    }

    //Métodos utilitários (eles encapsulam o TestRestTemplate e eliminam a repetição de código nos casos de teste)
    private ResponseEntity<List<ProdutoDtoResponse>> getProdutosList(String url) {
        var headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });
    }

    @Test
    @DisplayName("Espera uma página com 5 objetos e uma única página de 5 objetos")
    void findAllEsperaUmaPaginaCom5ObjetosEUmaPaginaDe5Objetos() {
        // ACT
        var data = getProdutosList("/api/v1/produtos").getBody();

        // ASSERT (testa se retorna a quantidade de dados esperada)
        assertNotNull(data);
        assertEquals(5, data.size());

        // ACT - testa uma requisição com os parametrôs page e size
        var dataPage = getProdutosList("/api/v1/produtos?page=0&size=5").getBody();

        // ASSERT (testa se retorna o tamanho de página solicitado)
        assertNotNull(dataPage);
        assertEquals(5, dataPage.size());
    }

    @Test
    void findById() {
    }

    @Test
    void findByNome() {
    }

    @Test
    @DisplayName("Insere um registro, retorna 201 Created.") //dá para detalhar o que vai aparecer ao no resultado do teste
    void testInsertEspera201Created() {
        //Triple Arrange, Act, Assert

        // ARRANGE
        var ProdutoDTOPost = new ProdutoDtoPost(
                "Teste",
                "Desc. do produto Teste",
                new BigDecimal("5.00"),
                new BigDecimal("10.00"),
                100
        );

        //Act
        var response = post("/api/v1/produtos", ProdutoDTOPost, null);

        // ASSERT
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        var location = response.getHeaders().get("location").get(0);
        var p = getProduto(location).getBody();
        assertNotNull(p);
        assertEquals("Teste", p.nome());
        assertEquals("Desc. do produto Teste", p.descricao());
        assertEquals(new BigDecimal("10.00"), p.valorDeVenda());
        assertEquals(Integer.valueOf(100), p.estoque());

        // ACT
        delete(location, null);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, getProduto(location).getStatusCode());

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}