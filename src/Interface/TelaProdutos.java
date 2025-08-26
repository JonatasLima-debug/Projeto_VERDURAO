package Interface;

import Produtos.ProdutoDAO;
import Produtos.Produtos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaProdutos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ProdutoDAO dao;

    public TelaProdutos(ProdutoDAO dao) {
        this.dao = dao;
        configurarJanela();
        configurarTabela();
        carregarProdutos();
    }

    private void configurarJanela() {
        setTitle("Lista de Produtos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void configurarTabela() {
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço", "Tipo", "Quantidade"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void carregarProdutos() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        List<Produtos> lista = dao.buscarTodos();

        for (Produtos p : lista) {
            modeloTabela.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    String.format("R$ %.2f", p.getPreco() /100), // centavos para reais
                    p.getTipo(),
                    p.getQuantidade()
            });
        }
    }
}
