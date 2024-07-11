package test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.ProdutoController;
import controller.RelatorioController;
import controller.VendaController;

import model.Produto;
import model.Venda;

public class RelatorioControllerTest {

    @InjectMocks
    private RelatorioController relatorioController;

    @Mock
    private ProdutoController produtoController;

    @Mock
    private VendaController vendaController;

    @SuppressWarnings("deprecation")
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGerarRelatorio() throws IOException {
        // Mock dos métodos do ProdutoController
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100));
        produtos.add(new Produto(2, "Feijão", "Alimento", 8.0, 12.0, "2023-02-01", "2024-02-01", 50));
        when(produtoController.getAllProdutos()).thenReturn(produtos);

        // Mock dos métodos do VendaController
        List<Venda> vendas = new ArrayList<>();
        Map<Integer, Integer> itensVenda1 = new HashMap<>();
        itensVenda1.put(1, 10);
        itensVenda1.put(2, 5);
        vendas.add(new Venda(1, "João", "Cartão de Crédito", 150.0, itensVenda1));

        Map<Integer, Integer> itensVenda2 = new HashMap<>();
        itensVenda2.put(1, 5);
        itensVenda2.put(2, 10);
        vendas.add(new Venda(2, "Maria", "Dinheiro", 120.0, itensVenda2));
        when(vendaController.getAllVendas()).thenReturn(vendas);

        // Caminho do arquivo temporário para teste
        String filePath = "relatorio_test.pdf";

        // Gera o relatório
        relatorioController.gerarRelatorio(filePath);

        // Verifica se o arquivo foi criado
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        // Opcionalmente, você pode adicionar mais verificações no conteúdo do PDF gerado

        // Apaga o arquivo após o teste
        file.delete();
    }

    @Test
    public void testCalcularValorTotalVendas() {
        // Mock dos métodos do VendaController
        List<Venda> vendas = new ArrayList<>();
        vendas.add(new Venda(1, "João", "Cartão de Crédito", 150.0, new HashMap<>()));
        vendas.add(new Venda(2, "Maria", "Dinheiro", 120.0, new HashMap<>()));
        when(vendaController.getAllVendas()).thenReturn(vendas);

        double valorTotalVendas = relatorioController.calcularValorTotalVendas();
        assertEquals(270.0, valorTotalVendas, 0.001);
    }

    @Test
    public void testCalcularLucroBruto() {
        // Mock dos métodos do ProdutoController e VendaController
        List<Venda> vendas = new ArrayList<>();
        Map<Integer, Integer> itensVenda1 = new HashMap<>();
        itensVenda1.put(1, 10);  // Produto 1, 10 unidades
        itensVenda1.put(2, 5);   // Produto 2, 5 unidades
        vendas.add(new Venda(1, "João", "Cartão de Crédito", 150.0, itensVenda1));

        Produto produto1 = new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100);
        Produto produto2 = new Produto(2, "Feijão", "Alimento", 8.0, 12.0, "2023-02-01", "2024-02-01", 50);
        when(produtoController.getProdutoById(1)).thenReturn(produto1);
        when(produtoController.getProdutoById(2)).thenReturn(produto2);
        when(vendaController.getAllVendas()).thenReturn(vendas);

        double lucroBruto = relatorioController.calcularLucroBruto();
        assertEquals(30.0, lucroBruto, 0.001);  // 150.0 - (10*10 + 8*5) = 150.0 - 120.0 = 30.0
    }

    @Test
    public void testCalcularLucroLiquido() {
        // Mock do método calcularLucroBruto
        when(relatorioController.calcularLucroBruto()).thenReturn(30.0);

        double lucroLiquido = relatorioController.calcularLucroLiquido();
        assertEquals(30.0, lucroLiquido, 0.001);
    }

    @Test
    public void testCalcularTotalItensVendidos() {
        // Mock dos métodos do VendaController
        List<Venda> vendas = new ArrayList<>();
        Map<Integer, Integer> itensVenda1 = new HashMap<>();
        itensVenda1.put(1, 10);  // Produto 1, 10 unidades
        itensVenda1.put(2, 5);   // Produto 2, 5 unidades
        vendas.add(new Venda(1, "João", "Cartão de Crédito", 150.0, itensVenda1));

        Map<Integer, Integer> itensVenda2 = new HashMap<>();
        itensVenda2.put(1, 5);   // Produto 1, 5 unidades
        itensVenda2.put(2, 10);  // Produto 2, 10 unidades
        vendas.add(new Venda(2, "Maria", "Dinheiro", 120.0, itensVenda2));
        when(vendaController.getAllVendas()).thenReturn(vendas);

        int totalItensVendidos = relatorioController.calcularTotalItensVendidos();
        assertEquals(30, totalItensVendidos);  // 10+5 + 5+10 = 30
    }

    @Test
    public void testCalcularTotalItensPedidos() {
        // Mock dos métodos do ProdutoController
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1, "Arroz", "Alimento", 10.0, 15.0, "2023-01-01", "2024-01-01", 100));
        produtos.add(new Produto(2, "Feijão", "Alimento", 8.0, 12.0, "2023-02-01", "2024-02-01", 50));
        when(produtoController.getAllProdutos()).thenReturn(produtos);

        int totalItensPedidos = relatorioController.calcularTotalItensPedidos();
        assertEquals(150, totalItensPedidos);  // 100 + 50 = 150
    }
}

