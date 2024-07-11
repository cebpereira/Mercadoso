package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;

import controller.VendaController;

import model.Venda;

import database.DatabaseConnection;

public class VendaControllerTest {

    @InjectMocks
    private VendaController vendaController;

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @SuppressWarnings("deprecation")
	@Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(DatabaseConnection.getConnection()).thenReturn(connection);
    }

    @Test
    public void testAddVenda() throws SQLException {
        Map<Integer, Integer> itens = new HashMap<>();
        itens.put(1, 10);
        itens.put(2, 5);
        Venda venda = new Venda(1, "João", "Cartão de Crédito", 150.0, itens);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        vendaController.addVenda(venda);

        verify(preparedStatement, times(1)).setInt(1, venda.getClienteId());
        verify(preparedStatement, times(1)).setString(2, venda.getClienteNome());
        verify(preparedStatement, times(1)).setString(3, venda.getFormaPagamento());
        verify(preparedStatement, times(1)).setDouble(4, venda.getValorTotal());
        verify(preparedStatement, times(1)).setString(5, new Gson().toJson(venda.getItens()));
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddVendaWithNullClienteId() throws SQLException {
        Map<Integer, Integer> itens = new HashMap<>();
        itens.put(1, 10);
        itens.put(2, 5);
        Venda venda = new Venda(null, "João", "Cartão de Crédito", 150.0, itens);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        vendaController.addVenda(venda);

        verify(preparedStatement, times(1)).setNull(1, Types.INTEGER);
        verify(preparedStatement, times(1)).setString(2, venda.getClienteNome());
        verify(preparedStatement, times(1)).setString(3, venda.getFormaPagamento());
        verify(preparedStatement, times(1)).setDouble(4, venda.getValorTotal());
        verify(preparedStatement, times(1)).setString(5, new Gson().toJson(venda.getItens()));
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetAllVendas() throws SQLException {
        List<Venda> expectedVendas = new ArrayList<>();
        Map<Integer, Integer> itensVenda1 = new HashMap<>();
        itensVenda1.put(1, 10);
        itensVenda1.put(2, 5);
        expectedVendas.add(new Venda(1, 1, "João", "Cartão de Crédito", 150.0, itensVenda1));

        Map<Integer, Integer> itensVenda2 = new HashMap<>();
        itensVenda2.put(1, 5);
        itensVenda2.put(2, 10);
        expectedVendas.add(new Venda(2, 2, "Maria", "Dinheiro", 120.0, itensVenda2));

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getInt("cliente_id")).thenReturn(1, 2);
        when(resultSet.getString("cliente_nome")).thenReturn("João", "Maria");
        when(resultSet.getString("forma_pagamento")).thenReturn("Cartão de Crédito", "Dinheiro");
        when(resultSet.getDouble("valor_total")).thenReturn(150.0, 120.0);
        when(resultSet.getString("itens")).thenReturn(new Gson().toJson(itensVenda1), new Gson().toJson(itensVenda2));

        List<Venda> actualVendas = vendaController.getAllVendas();

        assertEquals(expectedVendas.size(), actualVendas.size());
        for (int i = 0; i < expectedVendas.size(); i++) {
            Venda expected = expectedVendas.get(i);
            Venda actual = actualVendas.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getClienteId(), actual.getClienteId());
            assertEquals(expected.getClienteNome(), actual.getClienteNome());
            assertEquals(expected.getFormaPagamento(), actual.getFormaPagamento());
            assertEquals(expected.getValorTotal(), actual.getValorTotal(), 0.001);
            assertEquals(expected.getItens(), actual.getItens());
        }
    }

    @Test
    public void testUpdateProductStock() throws SQLException {
        Map<Integer, Integer> produtosVendidos = new HashMap<>();
        produtosVendidos.put(1, 10);
        produtosVendidos.put(2, 5);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        vendaController.updateProductStock(produtosVendidos);

        verify(connection, times(1)).setAutoCommit(false);
        verify(preparedStatement, times(1)).setInt(1, 10);
        verify(preparedStatement, times(1)).setInt(2, 1);
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).setInt(1, 5);
        verify(preparedStatement, times(1)).setInt(2, 2);
        verify(preparedStatement, times(1)).executeUpdate();
        verify(connection, times(1)).commit();
    }
    
    @Test
    public void testGetProdutoQuantityByVenda() {
        Map<Integer, Integer> itensVenda1 = new HashMap<>();
        itensVenda1.put(1, 10);
        itensVenda1.put(2, 5);
        Venda venda1 = new Venda(1, 1, "João", "Cartão de Crédito", 150.0, itensVenda1);

        Map<Integer, Integer> itensVenda2 = new HashMap<>();
        itensVenda2.put(1, 5);
        itensVenda2.put(2, 10);
        Venda venda2 = new Venda(2, 2, "Maria", "Dinheiro", 120.0, itensVenda2);

        List<Venda> vendas = new ArrayList<>();
        vendas.add(venda1);
        vendas.add(venda2);

        when(vendaController.getAllVendas()).thenReturn(vendas);

        int quantityProduto1 = vendaController.getProdutoQuantityByVenda(1);
        assertEquals(15, quantityProduto1); // 10 + 5

        int quantityProduto2 = vendaController.getProdutoQuantityByVenda(2);
        assertEquals(15, quantityProduto2); // 5 + 10
    }
}

