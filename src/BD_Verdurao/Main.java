package BD_Verdurao;

import BD_Verdurao.BancoDeDados;
import BD_Verdurao.BancodeDados_vendas;
import Produtos.ProdutoDAO;
import Produtos.ProdutoService;
import Produtos.Produtos;
import Vendas.VendasService;
import Vendas.VendasDAO;

public class Main {
    public static void main(String[] args) {
        // 1️⃣ Conectar no banco
        BancoDeDados bd = new BancoDeDados();
        bd.conectar();

        // 2️⃣ Criar DAO e Service de produtos
        ProdutoDAO produtoDAO = new ProdutoDAO(bd);
        ProdutoService produtoService = new ProdutoService(produtoDAO);

        // 3️⃣ Criar DAO e Service de vendas
        BancodeDados_vendas bdv = new BancodeDados_vendas();
        VendasDAO vendasDAO = new VendasDAO(bdv);
        VendasService vendasService = new VendasService(produtoService, vendasDAO);

        // ---------------------------
        // 4️⃣ Cadastrar um produto
        produtoService.CadastrarProduto("Banana", 500, "Fruta", 1000); // preço 500 centavos = R$5,00

        // 5️⃣ Realizar uma venda pelo nome
        vendasService.registrarVenda("Banana", 200); // vender 200 gramas/unidades

        // 6️⃣ Listar vendas do dia
        vendasService.listarVendasDoDia();

        // ---------------------------
        // 7️⃣ Desconectar
        bd.desconectar();
    }
}
