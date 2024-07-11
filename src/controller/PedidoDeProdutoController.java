package controller;

import java.sql.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DatabaseConnection;

import model.PedidoDeProduto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PedidoDeProdutoController {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public void addPedidoDeProduto(PedidoDeProduto pedido) {
        String sql = "INSERT INTO PedidoDeProduto (data_pedido, itens) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, Date.valueOf(pedido.getDataPedido()));
            preparedStatement.setString(2, new Gson().toJson(pedido.getItens()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductStock(Map<Integer, Integer> produtosParaAtualizar) {
        String sql = "UPDATE Produto SET quantidade_estoque = quantidade_estoque + ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);
            for (Map.Entry<Integer, Integer> entry : produtosParaAtualizar.entrySet()) {
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

    public List<PedidoDeProduto> getAllPedidosDeProduto() {
        List<PedidoDeProduto> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM PedidoDeProduto";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate dataPedido = resultSet.getDate("data_pedido").toLocalDate();
                String itensJson = resultSet.getString("itens");

                Map<Integer, Integer> itens = new Gson().fromJson(itensJson, new TypeToken<Map<Integer, Integer>>(){}.getType());

                PedidoDeProduto pedido = new PedidoDeProduto(id, dataPedido, itens);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }
}
