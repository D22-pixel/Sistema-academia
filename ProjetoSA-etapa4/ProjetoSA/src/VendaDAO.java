/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author denis
 */
public class VendaDAO {
    public void registrarVenda(Venda v) {
        String sqlVenda   = "INSERT INTO vendas (produto_id, quantidade, valor_total) VALUES (?, ?, ?)";
        String sqlEstoque = "UPDATE produtos SET estoque = estoque - ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtVenda   = conn.prepareStatement(sqlVenda);
                 PreparedStatement stmtEstoque = conn.prepareStatement(sqlEstoque)) {

                stmtVenda.setInt(1, v.getProdutoId());
                stmtVenda.setInt(2, v.getQuantidade());
                stmtVenda.setDouble(3, v.getValorTotal());
                stmtVenda.executeUpdate();

                stmtEstoque.setInt(1, v.getQuantidade());
                stmtEstoque.setInt(2, v.getProdutoId());
                stmtEstoque.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Venda> listarVendasComNome() {
        List<Venda> lista = new ArrayList<>();
             String sql = "SELECT v.id, p.nome, v.quantidade, v.valor_total, v.data_venda "
                   + "FROM vendas v "
                   + "JOIN produtos p ON v.produto_id = p.id "
                   + "ORDER BY v.data_venda DESC";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();      // <-- corrigido
             ResultSet rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setNomeProduto(rs.getString("nome"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setDataVenda(rs.getTimestamp("data_venda"));
                lista.add(venda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
} 

