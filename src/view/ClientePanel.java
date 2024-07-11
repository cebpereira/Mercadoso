package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;
import model.Cliente;

import java.awt.*;

@SuppressWarnings("serial")
public class ClientePanel extends JPanel {
    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private ClienteController clienteController;

    public ClientePanel() {
        clienteController = new ClienteController();
        setLayout(new BorderLayout());

        // Configuração da tabela
        table = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nome", "RG", "Data de Nascimento", "Limite de Crédito"}));
        loadClientes();

        // Botões
        addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> openClienteModal(null));
        editButton = new JButton("Editar");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int clienteId = (int) table.getModel().getValueAt(selectedRow, 0);
                Cliente cliente = clienteController.getClienteById(clienteId);
                openClienteModal(cliente);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.");
            }
        });
        
        deleteButton = new JButton("Deletar");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int clienteId = (int) table.getModel().getValueAt(selectedRow, 0);
                clienteController.deleteCliente(clienteId);
                loadClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para deletar.");
            }
        });

        // Adicionando elementos ao Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Adicionando elementos ao Panel Principal
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadClientes() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Cliente cliente : clienteController.getAllClientes()) {
            model.addRow(new Object[]{cliente.getId(), cliente.getNome(), cliente.getRg(), cliente.getDataNascimento(), cliente.getLimiteCredito()});
        }
    }

    private void openClienteModal(Cliente cliente) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Cliente", Dialog.ModalityType.APPLICATION_MODAL);
        ClienteFormPanel formPanel = new ClienteFormPanel(cliente, clienteController, dialog, this);
        dialog.setContentPane(formPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
