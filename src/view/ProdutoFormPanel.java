package view;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import controller.ProdutoController;

import model.Produto;

import java.awt.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

@SuppressWarnings("serial")
public class ProdutoFormPanel extends JPanel {
    private JTextField nomeField, tipoField, precoCompraField, precoVendaField, quantidadeEstoqueField;
    private JDateChooser fabricacaoChooser, validadeChooser;
    private JButton saveButton;
    private Produto produto;
    private ProdutoController produtoController;
    private JDialog dialog;
    private ProdutoPanel produtoPanel;

    public ProdutoFormPanel(Produto produto, ProdutoController produtoController, JDialog dialog, ProdutoPanel produtoPanel) {
        this.produto = produto;
        this.produtoController = produtoController;
        this.dialog = dialog;
        this.produtoPanel = produtoPanel;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nomeField = new JTextField(20);
        tipoField = new JTextField(20);
        precoCompraField = new JTextField(20);
        precoVendaField = new JTextField(20);
        quantidadeEstoqueField = new JTextField(20);

        fabricacaoChooser = new JDateChooser();
        fabricacaoChooser.setDateFormatString("yyyy-MM-dd");
        fabricacaoChooser.setPreferredSize(new Dimension(200, 20));
        validadeChooser = new JDateChooser();
        validadeChooser.setDateFormatString("yyyy-MM-dd");
        validadeChooser.setPreferredSize(new Dimension(200, 20));

        saveButton = new JButton(produto == null ? "Adicionar" : "Atualizar");

        saveButton.addActionListener(e -> saveProduto());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        add(tipoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Preço de Compra:"), gbc);
        gbc.gridx = 1;
        add(precoCompraField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Preço de Venda:"), gbc);
        gbc.gridx = 1;
        add(precoVendaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Fabricação:"), gbc);
        gbc.gridx = 1;
        add(fabricacaoChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Validade:"), gbc);
        gbc.gridx = 1;
        add(validadeChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Quantidade em Estoque:"), gbc);
        gbc.gridx = 1;
        add(quantidadeEstoqueField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);

        if (produto != null) {
            nomeField.setText(produto.getNome());
            tipoField.setText(produto.getTipo());
            precoCompraField.setText(String.valueOf(produto.getPrecoCompra()));
            precoVendaField.setText(String.valueOf(produto.getPrecoVenda()));
            try {
                Date fabricacao = new SimpleDateFormat("yyyy-MM-dd").parse(produto.getFabricacao());
                fabricacaoChooser.setDate(fabricacao);
                Date validade = new SimpleDateFormat("yyyy-MM-dd").parse(produto.getValidade());
                validadeChooser.setDate(validade);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            quantidadeEstoqueField.setText(String.valueOf(produto.getQuantidadeEstoque()));
        }
    }

    private void saveProduto() {
        if (nomeField.getText().isEmpty() || tipoField.getText().isEmpty() || precoCompraField.getText().isEmpty() || precoVendaField.getText().isEmpty() ||
            fabricacaoChooser.getDate() == null || validadeChooser.getDate() == null || quantidadeEstoqueField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios e devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (produto == null) {
            produto = new Produto(0, "", "", 0.0, 0.0, "", "", 0);
        }
        produto.setNome(nomeField.getText());
        produto.setTipo(tipoField.getText());
        produto.setPrecoCompra(Double.parseDouble(precoCompraField.getText()));
        produto.setPrecoVenda(Double.parseDouble(precoVendaField.getText()));
        produto.setFabricacao(new SimpleDateFormat("yyyy-MM-dd").format(fabricacaoChooser.getDate()));
        produto.setValidade(new SimpleDateFormat("yyyy-MM-dd").format(validadeChooser.getDate()));
        produto.setQuantidadeEstoque(Integer.parseInt(quantidadeEstoqueField.getText()));

        if (produto.getId() == 0) {
            produtoController.addProduto(produto);
        } else {
            produtoController.updateProduto(produto);
        }
        produtoPanel.loadProdutos();
        dialog.dispose();
    }
}
