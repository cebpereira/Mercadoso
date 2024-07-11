package view;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import controller.RelatorioController;

@SuppressWarnings("serial")
public class RelatorioPanel extends JPanel {
    private JButton gerarRelatorioButton;
    private JLabel previewLabel;
    private RelatorioController relatorioController;
    private static final String FILE_PATH = "relatorio_vendas.pdf";

    public RelatorioPanel() {
        relatorioController = new RelatorioController();

        setLayout(new BorderLayout());

        gerarRelatorioButton = new JButton("Gerar Relatório");
        gerarRelatorioButton.addActionListener(e -> gerarRelatorio());

        previewLabel = new JLabel();
        previewLabel.setHorizontalAlignment(JLabel.CENTER);
        previewLabel.setVerticalAlignment(JLabel.CENTER);

        add(gerarRelatorioButton, BorderLayout.NORTH);
        add(new JScrollPane(previewLabel), BorderLayout.CENTER);
    }

    private void gerarRelatorio() {
        relatorioController.gerarRelatorio(FILE_PATH);
        mostrarPreview(FILE_PATH);
    }

    private void mostrarPreview(String filePath) {
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 200, ImageType.RGB); // Renderiza a primeira página com 200 DPI e tipo RGB

            ImageIcon imageIcon = new ImageIcon(bim);
            previewLabel.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
