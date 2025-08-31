package Interface; // renomeie de Interface para gui para seguir convenção

import Produtos.ProdutoService;
import Produtos.ProdutoDAO;
import Produtos.Produtos;
import BD_Verdurao.BancoDeDados;
import Vendas.VendasDAO;
import Vendas.VendasService;
import BD_Verdurao.BancodeDados_vendas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InterfaceMain extends JFrame {
    private ProdutoService produtoService;
    private VendasService vendasService;

    private JTable tabelaEstoque;
    private JTable tabelaVendas;
    private JTextField campoNomeVenda;
    private JTextField campoQuantidadeVenda;

    public InterfaceMain() {
        // Inicializar conexão e services
        BancoDeDados bd = new BancoDeDados();
        bd.conectar();

        ProdutoDAO produtoDAO = new ProdutoDAO(bd);
        produtoService = new ProdutoService(produtoDAO);

        BancodeDados_vendas bdv = new BancodeDados_vendas();
        VendasDAO vendasDAO = new VendasDAO(bdv);
        vendasService = new VendasService(produtoService, vendasDAO);

        // Configurações da janela
        setTitle("Verdurão UFCat - Estoque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Abas
        JTabbedPane abas = new JTabbedPane();
        abas.add("Estoque", painelEstoque());
        //abas.add("Vendas", painelVendas());
        abas.add("Vendas do Dia", painelVendasDoDia());

        add(abas);

        atualizarEstoque();
    }

    // -----------------------------
    private JPanel painelEstoque() {
        JPanel panel = new JPanel(new BorderLayout());
        tabelaEstoque = new JTable();
        panel.add(new JScrollPane(tabelaEstoque), BorderLayout.CENTER);

        JButton btnAtualizar = new JButton("Atualizar Estoque");
        btnAtualizar.addActionListener(e -> atualizarEstoque());
        panel.add(btnAtualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void atualizarEstoque() {
        List<Produtos> produtos = produtoService.dao.buscarTodos();
        String[] colunas = {"ID", "Nome", "Tipo", "Preço (R$)", "Quantidade"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        for (Produtos p : produtos) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getTipo(),
                    p.getPreco(),
                    p.getQuantidade()
            });
        }

        tabelaEstoque.setModel(model);
    }
/*
    // -----------------------------
    private JPanel painelVendas() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Nome do Produto:"));
        campoNomeVenda = new JTextField();
        panel.add(campoNomeVenda);

        panel.add(new JLabel("Quantidade:"));
        campoQuantidadeVenda = new JTextField();
        panel.add(campoQuantidadeVenda);

        JButton btnVender = new JButton("Vender");
        btnVender.addActionListener(e -> realizarVenda());
        panel.add(btnVender);

        return panel;
    }
*/
    private void realizarVenda() {
        String nome = campoNomeVenda.getText().trim();
        long quantidade;
        try {
            quantidade = Long.parseLong(campoQuantidadeVenda.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
            return;
        }

        // Chamar o método certo!
        vendasService.registrarVenda(nome, quantidade);

        // Atualizar a tabela de estoque e vendas do dia
        atualizarEstoque();
        //atualizarVendasDoDia();
    }


    // -----------------------------
    private JPanel painelVendasDoDia() {
        JPanel panel = new JPanel(new BorderLayout());
        tabelaVendas = new JTable();
        panel.add(new JScrollPane(tabelaVendas), BorderLayout.CENTER);

        JButton btnAtualizar = new JButton("Atualizar Vendas do Dia");
        btnAtualizar.addActionListener(e -> atualizarVendasDoDia());
        panel.add(btnAtualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void atualizarVendasDoDia() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Produto", "Quantidade", "Preço Unit.", "Total", "Horário"}, 0);

        try {
            var rs = vendasService.dao.bdv.listarVendasDoDia();
            while (rs != null && rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_venda"),
                        rs.getString("nome"),
                        rs.getFloat("quantidade_vendida"),
                        rs.getFloat("preco_unitario"),
                        rs.getFloat("valor_total"),
                        rs.getTimestamp("horario_venda")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vendas do dia: " + e.getMessage());
        }

        tabelaVendas.setModel(model);
    }


    // -----------------------------
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceMain janela = new InterfaceMain();
            janela.setVisible(true);
        });
    }
}