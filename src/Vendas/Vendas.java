package Vendas;

import java.sql.Timestamp;

public class Vendas {
    private int id_produto;           // <-- novo atributo
    private Timestamp horario_venda;  // melhor usar Timestamp
    private double valor_total;
    private double preco_unitario;
    private double quantidade_vendida;

    // Getters e Setters
    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public Timestamp getHorario_venda() {
        return horario_venda;
    }

    public void setHorario_venda(Timestamp horario_venda) {
        this.horario_venda = horario_venda;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public double getPreco_unitario() {
        return preco_unitario;
    }

    public void setPreco_unitario(double preco_unitario) {
        this.preco_unitario = preco_unitario;
    }

    public double getQuantidade_vendida() {
        return quantidade_vendida;
    }

    public void setQuantidade_vendida(double quantidade_vendida) {
        this.quantidade_vendida = quantidade_vendida;
    }
}


//Verificação
