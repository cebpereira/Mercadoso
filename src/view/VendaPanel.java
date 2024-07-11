package view;

import javax.swing.*;

import java.awt.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import model.Produto;
import model.Cliente;
import model.Venda;

import controller.ProdutoController;
import controller.VendaController;
import controller.ClienteController;

@SuppressWarnings("serial")
public class VendaPanel extends JPanel {
    private JList<Produto> allProductsList;
    private JList<String> vendaList;
    private JButton addButton, returnButton, finalizeButton, refreshButton;
    private JTextField quantityField;
    private JTextField totalField;
    private DefaultListModel<Produto> allProductsModel;
    private DefaultListModel<String> vendaModel;
    private ProdutoController produtoController;
    private VendaController vendaController;
    private ClienteController clienteController;

    public VendaPanel() {
        produtoController = new ProdutoController();
        vendaController = new VendaController();
        clienteController = new ClienteController();
        allProductsModel = new DefaultListModel<>();
        vendaModel = new DefaultListModel<>();
        loadProdutos();

        allProductsList = new JList<>(allProductsModel);
        vendaList = new JList<>(vendaModel);
        quantityField = new JTextField(5);
        totalField = new JTextField(10);
        totalField.setEditable(false);
        addButton = new JButton("Add >>");
        returnButton = new JButton("<< Return");
        finalizeButton = new JButton("Finalize Sale");
        refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(e -> loadProdutos());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurações do JScrollPane para a lista de todos os produtos
        JScrollPane allProductsScrollPane = new JScrollPane(allProductsList);
        allProductsScrollPane.setPreferredSize(new Dimension(250, 300));

        // Configurações do JScrollPane para a lista de produtos do carrinho
        JScrollPane vendaScrollPane = new JScrollPane(vendaList);
        vendaScrollPane.setPreferredSize(new Dimension(250, 300));

        // Adiciona botão de atualizar no topo de tela
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
        add(new JLabel("Carrinho"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 4;
        add(vendaScrollPane, gbc);

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

        // Campo de valor total
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        add(new JLabel("Valor Total:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(totalField, gbc);

        // Botão de finalizar venda
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 5, 5, 5);
        add(finalizeButton, gbc);

        addButton.addActionListener(e -> addToVenda());
        returnButton.addActionListener(e -> returnToProducts());
        finalizeButton.addActionListener(e -> finalizeVenda());
    }

    public void loadProdutos() {
        allProductsModel.clear();
        for (Produto produto : produtoController.getAllProdutos()) {
            allProductsModel.addElement(produto);
        }
    }

    private void updateTotal() {
        double total = calculateTotal();
        totalField.setText(String.format("%.2f", total));
    }

    private void addToVenda() {
        Produto selected = allProductsList.getSelectedValue();
        if (selected != null && !quantityField.getText().isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0) {
                    if (selected.getQuantidadeEstoque() >= quantity) {
                        vendaModel.addElement(selected.getNome() + " - Quantidade: " + quantity);
                        allProductsModel.removeElement(selected);
                        updateTotal();
                    } else {
                        JOptionPane.showMessageDialog(this, "Estoque insuficiente para o produto: " + selected.getNome(), "Estoque Insuficiente", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade positiva.", "Quantidade Inválida", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para a quantidade.", "Quantidade Inválida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void returnToProducts() {
        String selected = vendaList.getSelectedValue();
        if (selected != null) {
            String[] parts = selected.split(" - Quantidade: ");
            String productName = parts[0];
            Produto produto = findProductByNameInAllProducts(productName);
            if (produto != null) {
                allProductsModel.addElement(produto);
                vendaModel.removeElement(selected);
                updateTotal();
            }
        }
    }

    private void finalizeVenda() {
        String clienteNome = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");
        if (clienteNome == null) {
            return;
        }
        
        Cliente cliente = clienteController.getClienteByNome(clienteNome);
        String[] options = cliente != null 
                           ? new String[]{"Dinheiro", "Cartão de Crédito", "Cartão de Débito", "Crédito na Loja"} 
                           : new String[]{"Dinheiro", "Cartão de Crédito", "Cartão de Débito"};
        String formaPagamento = (String) JOptionPane.showInputDialog(this, "Escolha o método de pagamento:\nValor Total: " + totalField.getText(), "Método de Pagamento", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (formaPagamento == null) {
            return;
        }
        
        double valorTotal = calculateTotal();

        Map<Integer, Integer> itens = new HashMap<>();
        for (int i = 0; i < vendaModel.size(); i++) {
            String itemDetails = vendaModel.getElementAt(i);
            String productName = itemDetails.split(" - Quantidade: ")[0];
            int quantity = Integer.parseInt(itemDetails.split(" - Quantidade: ")[1]);
            Produto produto = findProductByNameInAllProducts(productName);
            if (produto != null) {
                itens.put(produto.getId(), quantity);
            }
        }

        if ("Crédito na Loja".equals(formaPagamento) && cliente != null) {
            if (cliente.getLimiteCredito() < valorTotal) {
                JOptionPane.showMessageDialog(this, "Cliente não possui crédito suficiente.", "Crédito Insuficiente", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                cliente.setLimiteCredito(cliente.getLimiteCredito() - valorTotal);
                clienteController.updateCliente(cliente);
            }
        }

        Venda venda = new Venda(cliente != null ? cliente.getId() : null, clienteNome, formaPagamento, valorTotal, itens);
        vendaController.addVenda(venda);
        vendaController.updateProductStock(itens);
        vendaModel.clear();
        JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso!", "Venda Finalizada", JOptionPane.INFORMATION_MESSAGE);
        loadProdutos();
        updateTotal();
    }

    @SuppressWarnings("unused")
	private Produto findProductByName(String name) {
        for (int i = 0; i < allProductsModel.getSize(); i++) {
            Produto p = allProductsModel.getElementAt(i);
            if (p.getNome().equals(name)) {
                return p;
            }
        }
        return null;
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

    private double calculateTotal() {
        double total = 0;
        for (int i = 0; i < vendaModel.size(); i++) {
            String itemDetails = vendaModel.getElementAt(i);
            String productName = itemDetails.split(" - Quantidade: ")[0];
            int quantity = Integer.parseInt(itemDetails.split(" - Quantidade: ")[1]);
            Produto produto = findProductByNameInAllProducts(productName);
            if (produto != null) {
                total += produto.getPrecoVenda() * quantity;
            }
        }
        return total;
    }
}
