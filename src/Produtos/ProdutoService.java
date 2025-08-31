package Produtos;
import javax.swing.JOptionPane;

public class ProdutoService {

    public ProdutoDAO dao;

    public ProdutoService(ProdutoDAO dao) {
        this.dao = dao;
    }

    // Vender produto pelo nome
    public boolean vender(String nomeProduto, float quantidadeCliente) {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            System.out.println("Nome do produto inválido.");
            JOptionPane.showMessageDialog(null, "Nome do produto inválido");
            return false;
        }

        if (quantidadeCliente <= 0) {
            System.out.println("Quantidade inválida para venda.");
            JOptionPane.showMessageDialog(null, "Quantidade inválida para venda");
            return false;
        }

        float precoUnitario = dao.obterPrecoPorNome(nomeProduto);
        float estoqueAtual = dao.obterQuantidadePorNome(nomeProduto);

        if (precoUnitario == -1 || estoqueAtual == -1) {
            System.out.println("Produto não encontrado: " + nomeProduto);
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
            return false;
        }

        if (estoqueAtual < quantidadeCliente) {
            System.out.println("Estoque insuficiente! Estoque atual: " + estoqueAtual);
            JOptionPane.showMessageDialog(null, "Estoque do produto insuficiente");
            return false;
        }

        float novoEstoque = estoqueAtual - quantidadeCliente;
        dao.atualizarQuantidadePosVenda(nomeProduto, novoEstoque);

        System.out.printf("Venda realizada! Produto: %s | Total a pagar: R$ %.2f | Novo estoque: %s%n",
                nomeProduto, (precoUnitario * quantidadeCliente), novoEstoque);
 

        return true;
    }

    public int obterIdPorNome(String nomeProduto) {
        return dao.obterIdPorNome(nomeProduto);
    }

    public float obterPrecoPorNome(String nomeProduto) {
        return dao.obterPrecoPorNome(nomeProduto);
    }

    public void CadastrarProduto(String nome, float preco, String tipo, float quantidade) {
        if (nome == null || nome.trim().isEmpty()) return;
        if (preco <= 0 || quantidade < 0) return;
        if (dao.produtoExiste(nome.toUpperCase())) return;

        Produtos p = new Produtos();
        p.setNome(nome.toUpperCase());
        p.setPreco(preco);
        p.setTipo(tipo);
        p.setQuantidade(quantidade);
        dao.inserir(p);
    }

    public void EditarProduto(int id, String nome,float preco, String tipo, float quantidade) {
        if (nome == null || nome.trim().isEmpty()) return;
        if (preco <= 0 || quantidade < 0) return;

        if (dao.produtoExiste(nome.toUpperCase())) {
            Produtos p = new Produtos();
            p.setId(id);
            p.setNome(nome.toUpperCase());
            p.setPreco(preco);
            p.setTipo(tipo);
            p.setQuantidade(quantidade);
            dao.atualizar(p);
        }
    }

    public void ExcluirProduto(int id) {
        if (dao.existeID(id)) dao.deletar(id);
    }

  
}
