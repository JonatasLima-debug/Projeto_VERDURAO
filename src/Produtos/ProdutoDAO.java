package Produtos;

import BD_Verdurao.BancoDeDados;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private BancoDeDados bd;

    public ProdutoDAO(BancoDeDados bd) {
        this.bd = bd;
    }

    public BancoDeDados getBanco() {
        return this.bd;
    }

    // Inserir produto
    public boolean inserir(Produtos p) {
        try {
            bd.inserirProduto(p.getNome(), p.getPreco(), p.getTipo(), p.getQuantidade());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Atualizar produto
    public boolean atualizar(Produtos p) {
        try {
            bd.editarProdutos(p.getId(), p.getNome(), p.getPreco(), p.getTipo(), p.getQuantidade());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deletar produto
    public void deletar(int id) {
        bd.deletarProdutos(id);
    }

    // Buscar preço por nome
    public int obterPrecoPorNome(String nomeProduto) {
        String sql = "SELECT preco FROM produtos WHERE UPPER(nome) = ?";
        try (Connection conn = BancoDeDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeProduto.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("preco");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Buscar quantidade por nome
    public long obterQuantidadePorNome(String nomeProduto) {
        String sql = "SELECT quantidade FROM produtos WHERE UPPER(nome) = ?";
        try (Connection conn = BancoDeDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeProduto.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getLong("quantidade");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Atualizar quantidade após venda pelo nome
    public void atualizarQuantidadePosVenda(String nomeProduto, long novaQuantidade) {
        String sql = "UPDATE produtos SET quantidade = ? WHERE UPPER(nome) = ?";
        try (Connection conn = BancoDeDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, novaQuantidade);
            stmt.setString(2, nomeProduto.toUpperCase());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obter ID do produto pelo nome
    public int obterIdPorNome(String nomeProduto) {
        String sql = "SELECT id FROM produtos WHERE UPPER(nome) = ?";
        try (Connection conn = BancoDeDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeProduto.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Checar existência de produto pelo nome
    public boolean produtoExiste(String nomeProduto) {
        return obterIdPorNome(nomeProduto) != -1;
    }

    // Checar existência pelo ID
    public boolean existeID(int id) {
        String sql = "SELECT COUNT(*) FROM produtos WHERE id = ?";
        try (Connection conn = BancoDeDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Buscar todos produtos
    public List<Produtos> buscarTodos() {
        List<Produtos> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conn = BancoDeDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produtos p = new Produtos();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getInt("preco"));
                p.setTipo(rs.getString("tipo"));
                p.setQuantidade(rs.getLong("quantidade"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
