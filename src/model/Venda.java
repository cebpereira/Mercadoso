package model;

import java.util.Map;

public class Venda {
    private int id;
    private Integer clienteId;
    private String clienteNome;
    private String formaPagamento;
    private double valorTotal;
    private Map<Integer, Integer> itens;

    // Construtor
    public Venda(Integer clienteId, String clienteNome, String formaPagamento, double valorTotal, Map<Integer, Integer> itens) {
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
        this.itens = itens;
    }

    // Construtor com ID
    public Venda(int id, Integer clienteId, String clienteNome, String formaPagamento, double valorTotal, Map<Integer, Integer> itens) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
        this.itens = itens;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Map<Integer, Integer> getItens() {
        return itens;
    }

    public void setItens(Map<Integer, Integer> itens) {
        this.itens = itens;
    }
}
