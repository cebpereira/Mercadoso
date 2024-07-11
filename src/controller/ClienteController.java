package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;

import model.Cliente;

public class ClienteController {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public void addCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, rg, data_nascimento, limite_credito) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getRg());
            preparedStatement.setString(3, cliente.getDataNascimento());
            preparedStatement.setDouble(4, cliente.getLimiteCredito());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String rg = resultSet.getString("rg");
                String dataNascimento = resultSet.getString("data_nascimento"); // Nome da coluna ajustado
                double limiteCredito = resultSet.getDouble("limite_credito"); // Nome da coluna ajustado

                Cliente cliente = new Cliente(nome, rg, dataNascimento, limiteCredito);
                cliente.setId(id);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public void updateCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, rg = ?, data_nascimento = ?, limite_credito = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getRg());
            preparedStatement.setString(3, cliente.getDataNascimento());
            preparedStatement.setDouble(4, cliente.getLimiteCredito());
            preparedStatement.setInt(5, cliente.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCliente(int id) {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente getClienteById(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String rg = resultSet.getString("rg");
                String dataNascimento = resultSet.getString("data_nascimento"); // Nome da coluna ajustado
                double limiteCredito = resultSet.getDouble("limite_credito"); // Nome da coluna ajustado

                cliente = new Cliente(nome, rg, dataNascimento, limiteCredito);
                cliente.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
    
    public Cliente getClienteByNome(String nome) {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE nome = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String rg = resultSet.getString("rg");
                String dataNascimento = resultSet.getString("data_nascimento");
                double limiteCredito = resultSet.getDouble("limite_credito");

                cliente = new Cliente(nome, rg, dataNascimento, limiteCredito);
                cliente.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

}
