package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Cliente;

public class ClienteTest {

    private Cliente cliente;

    @Before
    public void setUp() {
        cliente = new Cliente("João", "12345678", "1990-01-01", 5000.0);
    }

    @Test
    public void testGetId() {
        cliente.setId(1);
        assertEquals(1, cliente.getId());
    }

    @Test
    public void testSetId() {
        cliente.setId(2);
        assertEquals(2, cliente.getId());
    }

    @Test
    public void testGetNome() {
        assertEquals("João", cliente.getNome());
    }

    @Test
    public void testSetNome() {
        cliente.setNome("Maria");
        assertEquals("Maria", cliente.getNome());
    }

    @Test
    public void testGetRg() {
        assertEquals("12345678", cliente.getRg());
    }

    @Test
    public void testSetRg() {
        cliente.setRg("87654321");
        assertEquals("87654321", cliente.getRg());
    }

    @Test
    public void testGetDataNascimento() {
        assertEquals("1990-01-01", cliente.getDataNascimento());
    }

    @Test
    public void testSetDataNascimento() {
        cliente.setDataNascimento("1991-02-02");
        assertEquals("1991-02-02", cliente.getDataNascimento());
    }

    @Test
    public void testGetLimiteCredito() {
        assertEquals(5000.0, cliente.getLimiteCredito(), 0.001);
    }

    @Test
    public void testSetLimiteCredito() {
        cliente.setLimiteCredito(6000.0);
        assertEquals(6000.0, cliente.getLimiteCredito(), 0.001);
    }
}
