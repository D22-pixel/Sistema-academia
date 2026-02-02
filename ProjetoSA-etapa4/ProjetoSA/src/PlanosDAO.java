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
public class PlanosDAO {
    public List<Planos> listarTodos() {
        String sql = "SELECT * FROM planos";
        List<Planos> lista = new ArrayList<>();
        
        try (java.sql.Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Planos p = new Planos();
                p.setId(rs.getInt("id_plano"));
                p.setNomePlano(rs.getString("nome_plano"));
                p.setValor(rs.getDouble("valor"));
                p.setDescricao(rs.getString("descricao"));
                p.setDuracaoDias(rs.getInt("duracao_dias"));
                lista.add(p);
            }
         System.out.println("O banco devolveu " + lista.size() + " planos.");
        
        }catch (SQLException e) { e.printStackTrace(); }
        
        return lista;
    }

    public void salvarOuAtualizar(Planos p) {
        
        System.out.println("Tentando salvar plano com ID: " + p.getId());
        
        String sql;
        if (p.getId() <= 0) {
            sql = "INSERT INTO planos (nome_plano, valor, duracao_dias, descricao) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE planos SET nome_plano=?, valor=?, duracao_dias=?, descricao=? WHERE id_plano=?";
        }

        try (java.sql.Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, p.getNomePlano());
            stmt.setDouble(2, p.getValor());
            stmt.setInt(3, p.getDuracaoDias());
            stmt.setString(4, p.getDescricao());

            if (p.getId() != 0) {
                stmt.setInt(5, p.getId());
            }
            int linhasAfetadas = stmt.executeUpdate(); 
        
        if (linhasAfetadas > 0) {
            System.out.println("Sucesso! Plano gravado no MySQL.");
        }else {System.out.println("Atenção: O banco não acusou erro, mas nenhuma linha foi inserida.");
               }

        } catch (SQLException e) {
            e.printStackTrace();
        }
     }
 public void excluir(int id) {
    String sql = "DELETE FROM planotreino WHERE id = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Plano excluído com sucesso!");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}    
