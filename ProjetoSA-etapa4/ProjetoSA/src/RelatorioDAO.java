
import com.sun.jdi.connect.spi.Connection;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<Object[]> getRelatorioEstoque() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT nome, preco, estoque FROM produtos ORDER BY nome";

        try (java.sql.Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome    = rs.getString("nome");
                double preco   = rs.getDouble("preco");
                int    estoque = rs.getInt("estoque");
                String status  = estoque == 0 ? "Sem Estoque"
                               : estoque <= 5  ? "Estoque Baixo"
                               : "Normal";

                lista.add(new Object[]{nome,
                                       String.format("R$ %.2f", preco),
                                       estoque,
                                       status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Object[]> getRelatorioVendas() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
                SELECT p.nome, v.quantidade, v.valor_total, v.data_venda
                FROM vendas v
                JOIN produtos p ON v.produto_id = p.id
                ORDER BY v.data_venda DESC
                """;

        try (java.sql.Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("nome"),
                    rs.getInt("quantidade"),
                    String.format("R$ %.2f", rs.getDouble("valor_total")),
                    rs.getTimestamp("data_venda")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public Object[] getDadosDashboard() {
    double faturamento = 0;
    int totalVendas = 0;
    int produtosBaixoEstoque = 0;

    VendaDAO vDao = new VendaDAO();
    List<Venda> vendas = vDao.listarVendasComNome(); //
    totalVendas = vendas.size();
    for (Venda v : vendas) faturamento += v.getValorTotal();

    ProdutoDAO pDao = new ProdutoDAO();
    List<Produto> produtos = pDao.listarTodos(); //
    for (Produto p : produtos) {
        if (p.getEstoque() <= 5) produtosBaixoEstoque++;
    }

    return new Object[]{faturamento, totalVendas, produtosBaixoEstoque};
  }
}