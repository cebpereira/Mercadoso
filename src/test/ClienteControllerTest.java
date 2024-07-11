package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.ClienteController;

import model.Cliente;

import database.DatabaseConnection;

public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

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
    public void testAddCliente() throws SQLException {
        Cliente cliente = new Cliente("João", "12345678", "1990-01-01", 5000.0);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        clienteController.addCliente(cliente);

        verify(preparedStatement, times(1)).setString(1, "João");
        verify(preparedStatement, times(1)).setString(2, "12345678");
        verify(preparedStatement, times(1)).setString(3, "1990-01-01");
        verify(preparedStatement, times(1)).setDouble(4, 5000.0);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetAllClientes() throws SQLException {
        List<Cliente> expectedClientes = new ArrayList<>();
        expectedClientes.add(new Cliente("João", "12345678", "1990-01-01", 5000.0));
        expectedClientes.add(new Cliente("Maria", "87654321", "1985-05-15", 3000.0));

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("nome")).thenReturn("João", "Maria");
        when(resultSet.getString("rg")).thenReturn("12345678", "87654321");
        when(resultSet.getString("data_nascimento")).thenReturn("1990-01-01", "1985-05-15");
        when(resultSet.getDouble("limite_credito")).thenReturn(5000.0, 3000.0);

        List<Cliente> actualClientes = clienteController.getAllClientes();

        assertEquals(expectedClientes.size(), actualClientes.size());
        for (int i = 0; i < expectedClientes.size(); i++) {
            Cliente expected = expectedClientes.get(i);
            Cliente actual = actualClientes.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getNome(), actual.getNome());
            assertEquals(expected.getRg(), actual.getRg());
            assertEquals(expected.getDataNascimento(), actual.getDataNascimento());
            assertEquals(expected.getLimiteCredito(), actual.getLimiteCredito(), 0.001);
        }
    }

    @Test
    public void testUpdateCliente() throws SQLException {
        Cliente cliente = new Cliente("João", "12345678", "1990-01-01", 5000.0);
        cliente.setId(1);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        clienteController.updateCliente(cliente);

        verify(preparedStatement, times(1)).setString(1, "João");
        verify(preparedStatement, times(1)).setString(2, "12345678");
        verify(preparedStatement, times(1)).setString(3, "1990-01-01");
        verify(preparedStatement, times(1)).setDouble(4, 5000.0);
        verify(preparedStatement, times(1)).setInt(5, 1);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteCliente() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        clienteController.deleteCliente(1);

        verify(preparedStatement, times(1)).setInt(1, 1);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetClienteById() throws SQLException {
        Cliente expectedCliente = new Cliente("João", "12345678", "1990-01-01", 5000.0);
        expectedCliente.setId(1);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nome")).thenReturn("João");
        when(resultSet.getString("rg")).thenReturn("12345678");
        when(resultSet.getString("data_nascimento")).thenReturn("1990-01-01");
        when(resultSet.getDouble("limite_credito")).thenReturn(5000.0);

        Cliente actualCliente = clienteController.getClienteById(1);

        assertNotNull(actualCliente);
        assertEquals(expectedCliente.getId(), actualCliente.getId());
        assertEquals(expectedCliente.getNome(), actualCliente.getNome());
        assertEquals(expectedCliente.getRg(), actualCliente.getRg());
        assertEquals(expectedCliente.getDataNascimento(), actualCliente.getDataNascimento());
        assertEquals(expectedCliente.getLimiteCredito(), actualCliente.getLimiteCredito(), 0.001);
    }

    @Test
    public void testGetClienteByNome() throws SQLException {
        Cliente expectedCliente = new Cliente("João", "12345678", "1990-01-01", 5000.0);
        expectedCliente.setId(1);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nome")).thenReturn("João");
        when(resultSet.getString("rg")).thenReturn("12345678");
        when(resultSet.getString("data_nascimento")).thenReturn("1990-01-01");
        when(resultSet.getDouble("limite_credito")).thenReturn(5000.0);

        Cliente actualCliente = clienteController.getClienteByNome("João");

        assertNotNull(actualCliente);
        assertEquals(expectedCliente.getId(), actualCliente.getId());
        assertEquals(expectedCliente.getNome(), actualCliente.getNome());
        assertEquals(expectedCliente.getRg(), actualCliente.getRg());
        assertEquals(expectedCliente.getDataNascimento(), actualCliente.getDataNascimento());
        assertEquals(expectedCliente.getLimiteCredito(), actualCliente.getLimiteCredito(), 0.001);
    }
}

