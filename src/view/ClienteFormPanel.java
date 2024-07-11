package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import controller.ClienteController;

import model.Cliente;

import com.toedter.calendar.JDateChooser;

import java.awt.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

@SuppressWarnings("serial")
public class ClienteFormPanel extends JPanel {
    private JTextField nomeField, limiteCreditoField;
    private JFormattedTextField rgField;
    private JDateChooser dataNascimentoChooser;
    private JButton saveButton;
    private Cliente cliente;
    private ClienteController clienteController;
    private JDialog dialog;
    private ClientePanel clientePanel;

    public ClienteFormPanel(Cliente cliente, ClienteController clienteController, JDialog dialog, ClientePanel clientePanel) {
        this.cliente = cliente;
        this.clienteController = clienteController;
        this.dialog = dialog;
        this.clientePanel = clientePanel;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nomeField = new JTextField(20);
        limiteCreditoField = new JTextField(20);
        try {
            rgField = new JFormattedTextField(new MaskFormatter("##.###.###-##"));
            rgField.setColumns(20);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dataNascimentoChooser = new JDateChooser();
        dataNascimentoChooser.setDateFormatString("yyyy-MM-dd");
        dataNascimentoChooser.setPreferredSize(new Dimension(200, 20));

        saveButton = new JButton(cliente == null ? "Adicionar" : "Atualizar");

        saveButton.addActionListener(e -> saveCliente());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("RG:"), gbc);
        gbc.gridx = 1;
        add(rgField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Data de Nascimento:"), gbc);
        gbc.gridx = 1;
        add(dataNascimentoChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Limite de Crédito:"), gbc);
        gbc.gridx = 1;
        add(limiteCreditoField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);

        if (cliente != null) {
            nomeField.setText(cliente.getNome());
            rgField.setText(cliente.getRg());
            try {
                Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse(cliente.getDataNascimento());
                dataNascimentoChooser.setDate(dataNascimento);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            limiteCreditoField.setText(String.valueOf(cliente.getLimiteCredito()));
        }
    }

    private void saveCliente() {
        if (nomeField.getText().isEmpty() || rgField.getText().isEmpty() || dataNascimentoChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Todos os campos exceto limite são obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cliente == null) {
            cliente = new Cliente("", "", "", 0.0);
        }
        
        cliente.setNome(nomeField.getText());
        cliente.setRg(rgField.getText());
        cliente.setDataNascimento(new SimpleDateFormat("yyyy-MM-dd").format(dataNascimentoChooser.getDate()));
        cliente.setLimiteCredito(Double.parseDouble(limiteCreditoField.getText()));

        if (cliente.getId() == 0) {
            clienteController.addCliente(cliente);
        } else {
            clienteController.updateCliente(cliente);
        }
        
        clientePanel.loadClientes();
        dialog.dispose();
    }
}
