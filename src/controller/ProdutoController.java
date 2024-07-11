package controller;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DatabaseConnection;

import model.Produto;

public class ProdutoController {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public void addProduto(Produto produto) {
        String sql = "INSERT INTO Produto (nome, tipo, preco_compra, preco_venda, fabricacao, validade, quantidade_estoque) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getTipo());
            preparedStatement.setDouble(3, produto.getPrecoCompra());
            preparedStatement.setDouble(4, produto.getPrecoVenda());
            preparedStatement.setString(5, produto.getFabricacao());
            preparedStatement.setString(6, produto.getValidade());
            preparedStatement.setInt(7, produto.getQuantidadeEstoque());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produto> getAllProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produto";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String tipo = resultSet.getString("tipo");
                double precoCompra = resultSet.getDouble("preco_compra");
                double precoVenda = resultSet.getDouble("preco_venda");
                String fabricacao = resultSet.getString("fabricacao");
                String validade = resultSet.getString("validade");
                int quantidadeEstoque = resultSet.getInt("quantidade_estoque");

                Produto produto = new Produto(id, nome, tipo, precoCompra, precoVenda, fabricacao, validade, quantidadeEstoque);
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public void updateProduto(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, tipo = ?, preco_compra = ?, preco_venda = ?, fabricacao = ?, validade = ?, quantidade_estoque = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getTipo());
            preparedStatement.setDouble(3, produto.getPrecoCompra());
            preparedStatement.setDouble(4, produto.getPrecoVenda());
            preparedStatement.setString(5, produto.getFabricacao());
            preparedStatement.setString(6, produto.getValidade());
            preparedStatement.setInt(7, produto.getQuantidadeEstoque());
            preparedStatement.setInt(8, produto.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduto(int id) {
        String sql = "DELETE FROM Produto WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Produto getProdutoById(int id) {
        Produto produto = null;
        String sql = "SELECT * FROM Produto WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String tipo = resultSet.getString("tipo");
                double precoCompra = resultSet.getDouble("preco_compra");
                double precoVenda = resultSet.getDouble("preco_venda");
                String fabricacao = resultSet.getString("fabricacao");
                String validade = resultSet.getString("validade");
                int quantidadeEstoque = resultSet.getInt("quantidade_estoque");

                produto = new Produto(id, nome, tipo, precoCompra, precoVenda, fabricacao, validade, quantidadeEstoque);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    public void updateProductStock(Map<Produto, Integer> produtosParaAtualizar) {
        String sql = "UPDATE Produto SET quantidade_estoque = quantidade_estoque + ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            for (Map.Entry<Produto, Integer> entry : produtosParaAtualizar.entrySet()) {
                preparedStatement.setInt(1, entry.getValue());
                preparedStatement.setInt(2, entry.getKey().getId());
                preparedStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
