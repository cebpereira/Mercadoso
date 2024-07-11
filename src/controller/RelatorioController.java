package controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import model.Produto;
import model.Venda;

import java.io.IOException;

import java.util.List;

public class RelatorioController {

    private ProdutoController produtoController;
    private VendaController vendaController;

    public RelatorioController() {
        produtoController = new ProdutoController();
        vendaController = new VendaController();
    }

    public void gerarRelatorio(String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.OVERWRITE, true, true)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 14);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);

                // Adiciona título
                contentStream.showText("Relatório de Vendas");
                contentStream.newLine();
                contentStream.newLine();

                // Adiciona valor total de vendas
                double valorTotalVendas = calcularValorTotalVendas();
                contentStream.showText("Valor Total de Vendas: R$ " + valorTotalVendas);
                contentStream.newLine();
                contentStream.newLine();

                // Adiciona lucro bruto
                double lucroBruto = calcularLucroBruto();
                contentStream.showText("Lucro Bruto: R$ " + lucroBruto);
                contentStream.newLine();
                contentStream.newLine();

                // Adiciona lucro líquido
                double lucroLiquido = calcularLucroLiquido();
                contentStream.showText("Lucro Líquido: R$ " + lucroLiquido);
                contentStream.newLine();
                contentStream.newLine();

                // Adiciona total de itens vendidos
                int totalItensVendidos = calcularTotalItensVendidos();
                contentStream.showText("Total de Itens Vendidos: " + totalItensVendidos);
                contentStream.newLine();
                contentStream.newLine();

                // Adiciona total de itens pedidos
                int totalItensPedidos = calcularTotalItensPedidos();
                contentStream.showText("Total de Itens Pedidos: " + totalItensPedidos);
                contentStream.newLine();
                contentStream.newLine();

                // Adiciona tabela de itens em estoque e suas quantidades
                contentStream.showText("Itens em Estoque:");
                contentStream.newLine();
                contentStream.newLine();

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

                List<Produto> produtos = produtoController.getAllProdutos();
                for (Produto produto : produtos) {
                    contentStream.showText(produto.getNome() + " - Quantidade: " + produto.getQuantidadeEstoque());
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            document.save(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double calcularValorTotalVendas() {
        List<Venda> vendas = vendaController.getAllVendas();
        return vendas.stream().mapToDouble(Venda::getValorTotal).sum();
    }

    public double calcularLucroBruto() {
        List<Venda> vendas = vendaController.getAllVendas();
        double totalVenda = vendas.stream().mapToDouble(Venda::getValorTotal).sum();
        double totalCompra = vendas.stream()
                .flatMap(venda -> venda.getItens().entrySet().stream())
                .mapToDouble(entry -> {
                    Produto produto = produtoController.getProdutoById(entry.getKey());
                    if (produto != null) {
                        return produto.getPrecoCompra() * entry.getValue();
                    }
                    return 0.0;
                })
                .sum();
        return totalVenda - totalCompra;
    }

    public double calcularLucroLiquido() {
        return calcularLucroBruto();
    }

    public int calcularTotalItensVendidos() {
        List<Venda> vendas = vendaController.getAllVendas();
        return vendas.stream().flatMapToInt(venda -> venda.getItens().values().stream().mapToInt(Integer::intValue)).sum();
    }

    public int calcularTotalItensPedidos() {
        List<Produto> produtos = produtoController.getAllProdutos();
        return produtos.stream().mapToInt(Produto::getQuantidadeEstoque).sum();
    }
}
