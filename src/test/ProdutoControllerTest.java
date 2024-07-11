package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.ProdutoController;

import model.Produto;

import database.DatabaseConnection;

public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

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
    public void testAddProduto() throws SQLException {
        Produto produto = new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        produtoController.addProduto(produto);

        verify(preparedStatement, times(1)).setString(1, "Arroz");
        verify(preparedStatement, times(1)).setString(2, "Alimento");
        verify(preparedStatement, times(1)).setDouble(3, 10.0);
        verify(preparedStatement, times(1)).setDouble(4, 15.0);
        verify(preparedStatement, times(1)).setString(5, "2023-01-01");
        verify(preparedStatement, times(1)).setString(6, "2024-01-01");
        verify(preparedStatement, times(1)).setInt(7, 100);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetAllProdutos() throws SQLException {
        List<Produto> expectedProdutos = new ArrayList<>();
        expectedProdutos.add(new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100));
        expectedProdutos.add(new Produto(2, "Feijão", "Alimento", 8.0, 12.0, "2023-02-01", "2024-02-01", 50));

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("nome")).thenReturn("Arroz", "Feijão");
        when(resultSet.getString("tipo")).thenReturn("Alimento", "Alimento");
        when(resultSet.getDouble("preco_compra")).thenReturn(10.0, 8.0);
        when(resultSet.getDouble("preco_venda")).thenReturn(15.0, 12.0);
        when(resultSet.getString("fabricacao")).thenReturn("2023-01-01", "2023-02-01");
        when(resultSet.getString("validade")).thenReturn("2024-01-01", "2024-02-01");
        when(resultSet.getInt("quantidade_estoque")).thenReturn(100, 50);

        List<Produto> actualProdutos = produtoController.getAllProdutos();

        assertEquals(expectedProdutos.size(), actualProdutos.size());
        for (int i = 0; i < expectedProdutos.size(); i++) {
            Produto expected = expectedProdutos.get(i);
            Produto actual = actualProdutos.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getNome(), actual.getNome());
            assertEquals(expected.getTipo(), actual.getTipo());
            assertEquals(expected.getPrecoCompra(), actual.getPrecoCompra(), 0.001);
            assertEquals(expected.getPrecoVenda(), actual.getPrecoVenda(), 0.001);
            assertEquals(expected.getFabricacao(), actual.getFabricacao());
            assertEquals(expected.getValidade(), actual.getValidade());
            assertEquals(expected.getQuantidadeEstoque(), actual.getQuantidadeEstoque());
        }
    }

    @Test
    public void testUpdateProduto() throws SQLException {
        Produto produto = new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        produtoController.updateProduto(produto);

        verify(preparedStatement, times(1)).setString(1, "Arroz");
        verify(preparedStatement, times(1)).setString(2, "Alimento");
        verify(preparedStatement, times(1)).setDouble(3, 10.0);
        verify(preparedStatement, times(1)).setDouble(4, 15.0);
        verify(preparedStatement, times(1)).setString(5, "2023-01-01");
        verify(preparedStatement, times(1)).setString(6, "2024-01-01");
        verify(preparedStatement, times(1)).setInt(7, 100);
        verify(preparedStatement, times(1)).setInt(8, 1);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteProduto() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        produtoController.deleteProduto(1);

        verify(preparedStatement, times(1)).setInt(1, 1);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetProdutoById() throws SQLException {
        Produto expectedProduto = new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nome")).thenReturn("Arroz");
        when(resultSet.getString("tipo")).thenReturn("Alimento");
        when(resultSet.getDouble("preco_compra")).thenReturn(10.0);
        when(resultSet.getDouble("preco_venda")).thenReturn(15.0);
        when(resultSet.getString("fabricacao")).thenReturn("2023-01-01");
        when(resultSet.getString("validade")).thenReturn("2024-01-01");
        when(resultSet.getInt("quantidade_estoque")).thenReturn(100);

        Produto actualProduto = produtoController.getProdutoById(1);

        assertNotNull(actualProduto);
        assertEquals(expectedProduto.getId(), actualProduto.getId());
        assertEquals(expectedProduto.getNome(), actualProduto.getNome());
        assertEquals(expectedProduto.getTipo(), actualProduto.getTipo());
        assertEquals(expectedProduto.getPrecoCompra(), actualProduto.getPrecoCompra(), 0.001);
        assertEquals(expectedProduto.getPrecoVenda(), actualProduto.getPrecoVenda(), 0.001);
        assertEquals(expectedProduto.getFabricacao(), actualProduto.getFabricacao());
        assertEquals(expectedProduto.getValidade(), actualProduto.getValidade());
        assertEquals(expectedProduto.getQuantidadeEstoque(), actualProduto.getQuantidadeEstoque());
    }

    @Test
    public void testUpdateProductStock() throws SQLException {
        Map<Produto, Integer> produtosParaAtualizar = new HashMap<>();
        Produto produto1 = new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100);
        Produto produto2 = new Produto(2, "Feijão", "Alimento", 8.0, 12.0, "2023-02-01", "2024-02-01", 50);
        produtosParaAtualizar.put(produto1, 10);
        produtosParaAtualizar.put(produto2, 20);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        produtoController.updateProductStock(produtosParaAtualizar);

        verify(connection, times(1)).setAutoCommit(false);
        verify(preparedStatement, times(1)).setInt(1, 10);
        verify(preparedStatement, times(1)).setInt(2, 1);
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).setInt(1, 20);
        verify(preparedStatement, times(1)).setInt(2, 2);
        verify(preparedStatement, times(1)).executeUpdate();
        verify(connection, times(1)).commit();
    }
}

