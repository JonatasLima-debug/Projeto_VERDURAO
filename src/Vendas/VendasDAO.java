package Vendas;

import BD_Verdurao.BancodeDados_vendas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendasDAO {
    public BancodeDados_vendas bdv;


    public VendasDAO(BancodeDados_vendas bdv) {
        this.bdv = bdv;
    }

    public boolean cadastrarVenda(Vendas venda) {
        try {
            bdv.inserirVendas(
                    venda.getHorario_venda(),
                    venda.getValor_total(),
                    venda.getPreco_unitario(),
                    venda.getId_produto(),
                    venda.getQuantidade_vendida()
            );
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar venda: " + e.getMessage());
            return false;
        }
    }

    public void mostrarVendasDoDia() {
        try {
            ResultSet rs = bdv.listarVendasDoDia();
            if (rs != null) {
                System.out.println("=== Vendas de Hoje ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Produto: %s | Quantidade: %.2f | Preço Unit: %.2f | Total: %.2f | Horário: %s%n",
                            rs.getInt("id_venda"),
                            rs.getString("nome"),
                            rs.getDouble("quantidade_vendida"),
                            rs.getDouble("preco_unitario"),
                            rs.getDouble("valor_total"),
                            rs.getTimestamp("horario_venda").toString());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao mostrar vendas do dia: " + e.getMessage());
        }
    }

  


}
