package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.Venda;

public class VendaTest {

    private Venda venda;

    @Before
    public void setUp() {
        Map<Integer, Integer> itens = new HashMap<>();
        itens.put(1, 2);
        itens.put(2, 3);
        venda = new Venda(1, "João", "Cartão de Crédito", 100.0, itens);
    }

    @Test
    public void testGetId() {
        venda.setId(1);
        assertEquals(1, venda.getId());
    }

    @Test
    public void testSetId() {
        venda.setId(2);
        assertEquals(2, venda.getId());
    }

    @Test
    public void testGetClienteId() {
        venda.setClienteId(1);
        assertEquals(Integer.valueOf(1), venda.getClienteId());
    }

    @Test
    public void testSetClienteId() {
        venda.setClienteId(2);
        assertEquals(Integer.valueOf(2), venda.getClienteId());
    }

    @Test
    public void testGetClienteNome() {
        assertEquals("João", venda.getClienteNome());
    }

    @Test
    public void testSetClienteNome() {
        venda.setClienteNome("Maria");
        assertEquals("Maria", venda.getClienteNome());
    }

    @Test
    public void testGetFormaPagamento() {
        assertEquals("Cartão de Crédito", venda.getFormaPagamento());
    }

    @Test
    public void testSetFormaPagamento() {
        venda.setFormaPagamento("Dinheiro");
        assertEquals("Dinheiro", venda.getFormaPagamento());
    }

    @Test
    public void testGetValorTotal() {
        assertEquals(100.0, venda.getValorTotal(), 0.001);
    }

    @Test
    public void testSetValorTotal() {
        venda.setValorTotal(150.0);
        assertEquals(150.0, venda.getValorTotal(), 0.001);
    }

    @Test
    public void testGetItens() {
        Map<Integer, Integer> itens = venda.getItens();
        assertNotNull(itens);
        assertEquals(2, itens.size());
        assertTrue(itens.containsKey(1));
        assertTrue(itens.containsKey(2));
        assertEquals(Integer.valueOf(2), itens.get(1));
        assertEquals(Integer.valueOf(3), itens.get(2));
    }

    @Test
    public void testSetItens() {
        Map<Integer, Integer> novosItens = new HashMap<>();
        novosItens.put(3, 4);
        novosItens.put(4, 5);
        venda.setItens(novosItens);

        Map<Integer, Integer> itens = venda.getItens();
        assertNotNull(itens);
        assertEquals(2, itens.size());
        assertTrue(itens.containsKey(3));
        assertTrue(itens.containsKey(4));
        assertEquals(Integer.valueOf(4), itens.get(3));
        assertEquals(Integer.valueOf(5), itens.get(4));
    }
}

