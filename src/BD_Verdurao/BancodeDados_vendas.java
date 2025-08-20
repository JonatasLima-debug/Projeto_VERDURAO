package BD_Verdurao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BancodeDados_vendas {
    private Connection connection;

    public BancodeDados_vendas(Connection connection) {
        this.connection = connection;
    }

    public BancodeDados_vendas() {
        try {
            this.connection = BD_Verdurao.BancoDeDados.getConexao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inserir venda
    public void inserirVendas(Timestamp horario_venda, double valor_total, double preco_unitario, int id_produto, double quantidade_vendida) {
        String sql = "INSERT INTO vendas (horario_venda, valor_total, preco_unitario, id_produto, quantidade_vendida) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, horario_venda);
            stmt.setDouble(2, valor_total);
            stmt.setDouble(3, preco_unitario);
            stmt.setInt(4, id_produto);
            stmt.setDouble(5, quantidade_vendida);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BancodeDados_vendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Listar vendas do dia
    public ResultSet listarVendasDoDia() {
        String sql = "SELECT v.id_venda, v.horario_venda, v.valor_total, v.preco_unitario, v.quantidade_vendida, p.nome " +
                "FROM vendas v " +
                "JOIN produtos p ON v.id_produto = p.id " +
                "WHERE DATE(v.horario_venda) = CURRENT_DATE";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas do dia: " + e.getMessage());
            return null;
        }
    }
}
