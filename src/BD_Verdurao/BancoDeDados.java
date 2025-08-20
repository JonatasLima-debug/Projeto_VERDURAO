package BD_Verdurao;

import Produtos.ProdutoDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BancoDeDados {
    private Connection connection = null;

    public void conectar() {
        String servidor = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.exyicoqykxivnpwzpjjl&password=Verdurao@123&sslmode=require";
        String usuario = "postgres.exyicoqykxivnpwzpjjl";
        String senha = "Verdurao@123";

        try {
            this.connection = DriverManager.getConnection(servidor, usuario, senha);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }

    public static Connection getConexao() throws SQLException {
        String servidor = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.exyicoqykxivnpwzpjjl&password=Verdurao@123&sslmode=require";
        String usuario = "postgres.exyicoqykxivnpwzpjj";
        String senha = "Verdurao@123";
        return DriverManager.getConnection(servidor, usuario, senha);
    }

    public boolean estaConectado() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public void inserirProduto(String nome, int preco, String tipo, long quantidade) {
        String query = "INSERT INTO produtos (nome, preco, tipo, quantidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setInt(2, preco);
            stmt.setString(3, tipo);
            stmt.setLong(4, quantidade);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarProdutos(int id, String nome, int preco, String tipo, long quantidade) {
        String query = "UPDATE produtos SET nome = ?, preco = ?, tipo = ?, quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setInt(2, preco);
            stmt.setString(3, tipo);
            stmt.setLong(4, quantidade);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletarProdutos(int id) {
        String query = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarProdutos() {
        String query = "SELECT * FROM produtos";
        try (PreparedStatement stmt = this.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int preco = rs.getInt("preco");
                String tipo = rs.getString("tipo");
                long quantidade = rs.getLong("quantidade");
                System.out.println("ID: " + id + " | Nome: " + nome + " | Preco: " + preco + " | Tipo: " + tipo + " | Quantidade: " + quantidade);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarProduto(String termo) {
        String query = "SELECT * FROM produtos WHERE nome ILIKE ? OR tipo ILIKE ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            String termoLike = "%" + termo + "%";
            stmt.setString(1, termoLike);
            stmt.setString(2, termoLike);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean encontrou = false;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int preco = rs.getInt("preco");
                    String tipo = rs.getString("tipo");
                    long quantidade = rs.getLong("quantidade");
                    System.out.println("ID: " + id + " | Nome: " + nome + " | Preco: " + preco + " | Tipo: " + tipo + " | Quantidade: " + quantidade);
                    encontrou = true;
                }
                if (!encontrou) {
                    System.out.println("Nenhum produto encontrado com o termo: " + termo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int obterPrecoProduto(String nomeProduto) {
        String query = "SELECT preco FROM produtos WHERE nome = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, nomeProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("preco");
                } else {
                    System.out.println("Produto não encontrado: " + nomeProduto);
                    return -1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public long obterQuantidade(String nomeProduto) {
        String query = "SELECT quantidade FROM produtos WHERE nome = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, nomeProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("quantidade");
                } else {
                    System.out.println("Produto não encontrado: " + nomeProduto);
                    return -1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    //Aqui, atualiza a quantidade de produtos após a venda. Manda para o DAO e depois vai para o Service
    public void quantidadePosVenda(String nome, long quantidadePos) {
        String query = "UPDATE produtos SET quantidade = ? WHERE nome = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setLong(1, quantidadePos);
            stmt.setString(2, nome);
            stmt.executeUpdate(); // Faltava isso
        } catch (SQLException ex) {
            Logger.getLogger(BancoDeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Buscar um produto no banco a partir do nome, vou mandar isso para o DAO e do DAO para o Service na hora de cadastrar
    public boolean produtoExiste(String nomeProduto) {
        String query = "SELECT COUNT(*) FROM produtos WHERE UPPER(nome) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomeProduto.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean existeID(int id) {
        String query = "SELECT COUNT(*) FROM produtos WHERE UPPER(nome) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
