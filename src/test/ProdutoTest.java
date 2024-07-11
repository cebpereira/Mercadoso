package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Produto;

public class ProdutoTest {

    private Produto produto;

    @Before
    public void setUp() {
        produto = new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100);
    }

    @Test
    public void testGetId() {
        assertEquals(1, produto.getId());
    }

    @Test
    public void testSetId() {
        produto.setId(2);
        assertEquals(2, produto.getId());
    }

    @Test
    public void testGetNome() {
        assertEquals("Arroz", produto.getNome());
    }

    @Test
    public void testSetNome() {
        produto.setNome("Feijão");
        assertEquals("Feijão", produto.getNome());
    }

    @Test
    public void testGetTipo() {
        assertEquals("Alimento", produto.getTipo());
    }

    @Test
    public void testSetTipo() {
        produto.setTipo("Grão");
        assertEquals("Grão", produto.getTipo());
    }

    @Test
    public void testGetPrecoCompra() {
        assertEquals(10.0, produto.getPrecoCompra(), 0.001);
    }

    @Test
    public void testSetPrecoCompra() {
        produto.setPrecoCompra(12.0);
        assertEquals(12.0, produto.getPrecoCompra(), 0.001);
    }

    @Test
    public void testGetPrecoVenda() {
        assertEquals(15.0, produto.getPrecoVenda(), 0.001);
    }

    @Test
    public void testSetPrecoVenda() {
        produto.setPrecoVenda(18.0);
        assertEquals(18.0, produto.getPrecoVenda(), 0.001);
    }

    @Test
    public void testGetFabricacao() {
        assertEquals("2023-01-01", produto.getFabricacao());
    }

    @Test
    public void testSetFabricacao() {
        produto.setFabricacao("2023-06-01");
        assertEquals("2023-06-01", produto.getFabricacao());
    }

    @Test
    public void testGetValidade() {
        assertEquals("2024-01-01", produto.getValidade());
    }

    @Test
    public void testSetValidade() {
        produto.setValidade("2024-06-01");
        assertEquals("2024-06-01", produto.getValidade());
    }

    @Test
    public void testGetQuantidadeEstoque() {
        assertEquals(100, produto.getQuantidadeEstoque());
    }

    @Test
    public void testSetQuantidadeEstoque() {
        produto.setQuantidadeEstoque(150);
        assertEquals(150, produto.getQuantidadeEstoque());
    }

    @Test
    public void testToString() {
        String expected = "Arroz (Alimento) - Preço: 15.0 - Estoque: 100";
        assertEquals(expected, produto.toString());
    }
}

