package model;

import java.time.LocalDate;
import java.util.Map;

public class PedidoDeProduto {
    private int id;
    private LocalDate dataPedido;
    private Map<Integer, Integer> itens;

    // Construtor
    public PedidoDeProduto(LocalDate dataPedido, Map<Integer, Integer> itens) {
        this.dataPedido = dataPedido;
        this.itens = itens;
    }

    // Construtor com ID
    public PedidoDeProduto(int id, LocalDate dataPedido, Map<Integer, Integer> itens) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.itens = itens;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Map<Integer, Integer> getItens() {
        return itens;
    }

    public void setItens(Map<Integer, Integer> itens) {
        this.itens = itens;
    }
}
