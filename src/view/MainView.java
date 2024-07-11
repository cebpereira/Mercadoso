package view;

import java.awt.BorderLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainView extends JFrame {
    private JTabbedPane tabbedPane;
    
    public MainView() {
        setTitle("Sistema de Gerenciamento do Mercadoso");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Aba HOME com informações de boas-vindas
        // Não me orgulho dessa gambiarra
        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("<html><h1>Bem-vindo ao Sistema de Gerenciamento do Mercadoso</h1><hr>"
            + "<ul>"
            + "<li>Gerencie Clientes</li>"
            + "<li>Gerencie Produtos</li>"
            + "<li>Gerencie Pedidos de Produtos</li>"
            + "<li>Realize Vendas</li>"
            + "<li>Gere Relatórios</li>"
            + "</ul></html>", JLabel.CENTER);
        homePanel.add(welcomeLabel, BorderLayout.CENTER);
        
        tabbedPane.addTab("HOME", homePanel);
        tabbedPane.addTab("Clientes", new ClientePanel());
        tabbedPane.addTab("Produtos", new ProdutoPanel());
        tabbedPane.addTab("Pedidos de Produto", new PedidoDeProdutoPanel());
        tabbedPane.addTab("Vendas", new VendaPanel());
        tabbedPane.addTab("Relatório", new RelatorioPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainView().setVisible(true);
        });
    }
}
