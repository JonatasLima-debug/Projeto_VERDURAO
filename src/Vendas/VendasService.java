package Vendas;

import Produtos.ProdutoService;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VendasService {
    public ProdutoService produtoService;
    public VendasDAO dao;

    public VendasService(ProdutoService produtoService, VendasDAO dao) {
        this.produtoService = produtoService;
        this.dao = dao;
    }

    // Registrar venda pelo nome do produto
    public void registrarVenda(String nomeProduto, float quantidadeCliente) {
        // Tentar vender o produto (atualiza estoque)
        boolean vendaSucesso = produtoService.vender(nomeProduto, quantidadeCliente);

        if (!vendaSucesso) {
            System.out.println("Falha ao registrar venda.");
            return;
        }

        // Obter preço unitário e ID do produto
        float precoUnitario = produtoService.obterPrecoPorNome(nomeProduto);
        int idProduto = produtoService.obterIdPorNome(nomeProduto);

        if (idProduto == -1) {
            System.out.println("Produto não encontrado no banco.");
            return;
        }

        float valorTotal = precoUnitario * quantidadeCliente / 100.0f;

        // Criar objeto Vendas
        Vendas venda = new Vendas();
        venda.setHorario_venda(Timestamp.valueOf(LocalDateTime.now())); // Data atual
        venda.setPreco_unitario(precoUnitario);
        venda.setValor_total(valorTotal);
        venda.setQuantidade_vendida(quantidadeCliente);
        venda.setId_produto(idProduto); // ID correto do produto

        // Cadastrar no banco
        if (dao.cadastrarVenda(venda)) {
            System.out.println("Venda registrada com sucesso!");
        } else {
            System.out.println("Erro ao registrar venda no banco.");
        }
    }

    // Listar vendas do dia
    public void listarVendasDoDia() {
        dao.mostrarVendasDoDia();
    }
}
