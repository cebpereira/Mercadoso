package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.ProdutoController;

import model.Produto;

import java.awt.*;

@SuppressWarnings("serial")
public class ProdutoPanel extends JPanel {
    private JTable table;
    private JButton addButton, editButton, deleteButton, refreshButton;
    private ProdutoController produtoController;

    public ProdutoPanel() {
        produtoController = new ProdutoController();
        setLayout(new BorderLayout());

        // Configuração da tabela
        table = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nome", "Tipo", "Preço de Compra", "Preço de Venda", "Fabricação", "Validade", "Quantidade em Estoque"}));
        loadProdutos();

        // Botões
        addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> openProdutoModal(null));
        editButton = new JButton("Editar");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int produtoId = (int) table.getModel().getValueAt(selectedRow, 0);
                Produto produto = produtoController.getProdutoById(produtoId);
                openProdutoModal(produto);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
            }
        });
        deleteButton = new JButton("Deletar");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int produtoId = (int) table.getModel().getValueAt(selectedRow, 0);
                produtoController.deleteProduto(produtoId);
                loadProdutos();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para deletar.");
            }
        });

        refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(e -> loadProdutos());

        // Botões do Painel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Adicionando elementos ao Painel Principal
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadProdutos() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Produto produto : produtoController.getAllProdutos()) {
            model.addRow(new Object[]{produto.getId(), produto.getNome(), produto.getTipo(), produto.getPrecoCompra(), produto.getPrecoVenda(), produto.getFabricacao(), produto.getValidade(), produto.getQuantidadeEstoque()});
        }
    }

    private void openProdutoModal(Produto produto) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Produto", Dialog.ModalityType.APPLICATION_MODAL);
        ProdutoFormPanel formPanel = new ProdutoFormPanel(produto, produtoController, dialog, this);
        dialog.setContentPane(formPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
