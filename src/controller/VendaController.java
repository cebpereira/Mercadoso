package controller;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DatabaseConnection;

import model.Venda;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VendaController {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public void addVenda(Venda venda) {
        String sql = "INSERT INTO Venda (cliente_id, cliente_nome, forma_pagamento, valor_total, itens) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (venda.getClienteId() == null) {
                preparedStatement.setNull(1, Types.INTEGER);
            } else {
                preparedStatement.setInt(1, venda.getClienteId());
            }
            preparedStatement.setString(2, venda.getClienteNome());
            preparedStatement.setString(3, venda.getFormaPagamento());
            preparedStatement.setDouble(4, venda.getValorTotal());
            preparedStatement.setString(5, new Gson().toJson(venda.getItens()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Venda> getAllVendas() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM Venda";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Integer clienteId = resultSet.getInt("cliente_id");
                if (resultSet.wasNull()) {
                    clienteId = null;
                }
                String clienteNome = resultSet.getString("cliente_nome");
                String formaPagamento = resultSet.getString("forma_pagamento");
                double valorTotal = resultSet.getDouble("valor_total");
                String itensJson = resultSet.getString("itens");

                Map<Integer, Integer> itens = new Gson().fromJson(itensJson, new TypeToken<Map<Integer, Integer>>(){}.getType());

                Venda venda = new Venda(id, clienteId, clienteNome, formaPagamento, valorTotal, itens);
                vendas.add(venda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendas;
    }

    public void updateProductStock(Map<Integer, Integer> produtosVendidos) {
        String sql = "UPDATE Produto SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);
            for (Map.Entry<Integer, Integer> entry : produtosVendidos.entrySet()) {
                preparedStatement.setInt(1, entry.getValue());
                preparedStatement.setInt(2, entry.getKey());
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection connection = getConnection()) {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public int getProdutoQuantityByVenda(int produtoId) {
        List<Venda> vendas = getAllVendas();
        return vendas.stream()
                .mapToInt(venda -> venda.getItens().getOrDefault(produtoId, 0))
                .sum();
    }

}
