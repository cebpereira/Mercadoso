package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.PedidoDeProduto;

public class PedidoDeProdutoTest {

    private PedidoDeProduto pedido;

    @Before
    public void setUp() {
        Map<Integer, Integer> itens = new HashMap<>();
        itens.put(1, 5);
        itens.put(2, 10);
        pedido = new PedidoDeProduto(LocalDate.of(2023, 7, 10), itens);
    }

    @Test
    public void testGetId() {
        pedido.setId(1);
        assertEquals(1, pedido.getId());
    }

    @Test
    public void testSetId() {
        pedido.setId(2);
        assertEquals(2, pedido.getId());
    }

    @Test
    public void testGetDataPedido() {
        assertEquals(LocalDate.of(2023, 7, 10), pedido.getDataPedido());
    }

    @Test
    public void testSetDataPedido() {
        pedido.setDataPedido(LocalDate.of(2023, 8, 15));
        assertEquals(LocalDate.of(2023, 8, 15), pedido.getDataPedido());
    }

    @Test
    public void testGetItens() {
        Map<Integer, Integer> itens = pedido.getItens();
        assertNotNull(itens);
        assertEquals(2, itens.size());
        assertTrue(itens.containsKey(1));
        assertTrue(itens.containsKey(2));
        assertEquals(Integer.valueOf(5), itens.get(1));
        assertEquals(Integer.valueOf(10), itens.get(2));
    }

    @Test
    public void testSetItens() {
        Map<Integer, Integer> novosItens = new HashMap<>();
        novosItens.put(3, 15);
        novosItens.put(4, 20);
        pedido.setItens(novosItens);

        Map<Integer, Integer> itens = pedido.getItens();
        assertNotNull(itens);
        assertEquals(2, itens.size());
        assertTrue(itens.containsKey(3));
        assertTrue(itens.containsKey(4));
        assertEquals(Integer.valueOf(15), itens.get(3));
        assertEquals(Integer.valueOf(20), itens.get(4));
    }
}
