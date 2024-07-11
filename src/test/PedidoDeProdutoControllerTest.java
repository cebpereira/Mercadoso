package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;

import controller.PedidoDeProdutoController;

import model.PedidoDeProduto;

import database.DatabaseConnection;

public class PedidoDeProdutoControllerTest {

    @InjectMocks
    private PedidoDeProdutoController pedidoDeProdutoController;

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
    public void testAddPedidoDeProduto() throws SQLException {
        Map<Integer, Integer> itens = new HashMap<>();
        itens.put(1, 5);
        itens.put(2, 10);
        PedidoDeProduto pedido = new PedidoDeProduto(LocalDate.of(2023, 7, 10), itens);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        pedidoDeProdutoController.addPedidoDeProduto(pedido);

        verify(preparedStatement, times(1)).setDate(1, Date.valueOf(pedido.getDataPedido()));
        verify(preparedStatement, times(1)).setString(2, new Gson().toJson(pedido.getItens()));
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdateProductStock() throws SQLException {
        Map<Integer, Integer> produtosParaAtualizar = new HashMap<>();
        produtosParaAtualizar.put(1, 5);
        produtosParaAtualizar.put(2, 10);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        pedidoDeProdutoController.updateProductStock(produtosParaAtualizar);

        verify(connection, times(1)).setAutoCommit(false);
        verify(preparedStatement, times(1)).setInt(1, 5);
        verify(preparedStatement, times(1)).setInt(2, 1);
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).setInt(1, 10);
        verify(preparedStatement, times(1)).setInt(2, 2);
        verify(preparedStatement, times(1)).executeUpdate();
        verify(connection, times(1)).commit();
    }

    @Test
    public void testGetAllPedidosDeProduto() throws SQLException {
        Map<Integer, Integer> itens = new HashMap<>();
        itens.put(1, 5);
        itens.put(2, 10);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getDate("data_pedido")).thenReturn(Date.valueOf(LocalDate.of(2023, 7, 10)));
        when(resultSet.getString("itens")).thenReturn(new Gson().toJson(itens));

        List<PedidoDeProduto> pedidos = pedidoDeProdutoController.getAllPedidosDeProduto();

        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        PedidoDeProduto pedido = pedidos.get(0);
        assertEquals(1, pedido.getId());
        assertEquals(LocalDate.of(2023, 7, 10), pedido.getDataPedido());
        assertEquals(itens, pedido.getItens());
    }
}

