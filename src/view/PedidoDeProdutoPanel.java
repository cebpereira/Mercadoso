package view;

import javax.swing.*;

import java.awt.*;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import model.Produto;
import model.PedidoDeProduto;

import controller.ProdutoController;
import controller.PedidoDeProdutoController;

@SuppressWarnings("serial")
public class PedidoDeProdutoPanel extends JPanel {
    private JList<Produto> allProductsList;
    private JList<String> pedidoList;
    private JButton addButton, returnButton, finalizeButton, refreshButton;
    private JTextField quantityField;
    private DefaultListModel<Produto> allProductsModel;
    private DefaultListModel<String> pedidoModel;
    private ProdutoController produtoController;
    private PedidoDeProdutoController pedidoController;

    public PedidoDeProdutoPanel() {
        produtoController = new ProdutoController();
        pedidoController = new PedidoDeProdutoController();
        allProductsModel = new DefaultListModel<>();
        pedidoModel = new DefaultListModel<>();
        loadProdutos();

        allProductsList = new JList<>(allProductsModel);
        pedidoList = new JList<>(pedidoModel);
        quantityField = new JTextField(5);
        addButton = new JButton("Adicionar >>");
        returnButton = new JButton("<< Remover");
        finalizeButton = new JButton("Efetuar Pedido");
        refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(e -> loadProdutos());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurações do JScrollPane para a lista de todos os produtos
        JScrollPane allProductsScrollPane = new JScrollPane(allProductsList);
        allProductsScrollPane.setPreferredSize(new Dimension(250, 300));

        // Configurações do JScrollPane para a lista de produtos do pedido
        JScrollPane pedidoScrollPane = new JScrollPane(pedidoList);
        pedidoScrollPane.setPreferredSize(new Dimension(250, 300));

        // Adiciona botão de atualizar no topo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        add(refreshButton, gbc);

        // Panel central com as listas e os botões de adicionar/retornar
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Todos os Produtos"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 4;
        add(allProductsScrollPane, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        add(new JLabel("Produtos no Pedido"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 4;
        add(pedidoScrollPane, gbc);

        // Botões e campo de quantidade
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(new JLabel("Quantidade:"), gbc);

        gbc.gridy = 3;
        add(quantityField, gbc);

        gbc.gridy = 4;
        add(addButton, gbc);

        gbc.gridy = 5;
        add(returnButton, gbc);

        // Botão de finalizar pedido
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 5, 5, 5);
        add(finalizeButton, gbc);

        addButton.addActionListener(e -> addToPedido());
        returnButton.addActionListener(e -> returnToProducts());
        finalizeButton.addActionListener(e -> finalizePedido());
    }

    public void loadProdutos() {
        allProductsModel.clear();
        for (Produto produto : produtoController.getAllProdutos()) {
            allProductsModel.addElement(produto);
        }
    }

    private void addToPedido() {
        Produto selected = allProductsList.getSelectedValue();
        if (selected != null && !quantityField.getText().isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0) {
                    pedidoModel.addElement(selected.getNome() + " - Quantidade: " + quantity);
                    allProductsModel.removeElement(selected);
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade positiva.", "Quantidade Inválida", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para a quantidade.", "Quantidade Inválida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void returnToProducts() {
        String selected = pedidoList.getSelectedValue();
        if (selected != null) {
            String[] parts = selected.split(" - Quantidade: ");
            String productName = parts[0];
            Produto produto = findProductByNameInAllProducts(productName);
            if (produto != null) {
                allProductsModel.addElement(produto);
                pedidoModel.removeElement(selected);
            }
        }
    }

    private void finalizePedido() {
        Map<Integer, Integer> itens = new HashMap<>();
        Map<Integer, Integer> produtosParaAtualizar = new HashMap<>();
        for (int i = 0; i < pedidoModel.size(); i++) {
            String itemDetails = pedidoModel.getElementAt(i);
            String productName = itemDetails.split(" - Quantidade: ")[0];
            int quantity = Integer.parseInt(itemDetails.split(" - Quantidade: ")[1]);
            Produto produto = findProductByNameInAllProducts(productName);
            if (produto != null) {
                itens.put(produto.getId(), quantity);
                produtosParaAtualizar.put(produto.getId(), quantity);
            }
        }
        PedidoDeProduto pedido = new PedidoDeProduto(LocalDate.now(), itens);
        pedidoController.addPedidoDeProduto(pedido);
        pedidoController.updateProductStock(produtosParaAtualizar);
        pedidoModel.clear();
        JOptionPane.showMessageDialog(this, "Pedido finalizado com sucesso!", "Pedido Finalizado", JOptionPane.INFORMATION_MESSAGE);
        loadProdutos();
    }

    private Produto findProductByNameInAllProducts(String name) {
        List<Produto> produtos = produtoController.getAllProdutos();
        for (Produto p : produtos) {
            if (p.getNome().equals(name)) {
                return p;
            }
        }
        return null;
    }
}
