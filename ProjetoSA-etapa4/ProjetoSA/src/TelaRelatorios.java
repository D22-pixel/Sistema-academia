
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TelaRelatorios extends TelaBase {

    public TelaRelatorios() {
        super("Relatórios");
        setSize(1060, 760);
        setLocationRelativeTo(null);
        inicializarUI();
    }

    private void inicializarUI() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("📊  Relatórios do Sistema"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Visão geral dos dados cadastrados no sistema"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        int totalAlunos = 0, totalMat = 0, totalProd = 0;
        double totalVendas = 0;
        try {
            totalAlunos = new AlunosDAO().listarTodos().size();
        } catch (Exception ex) {
        }
        try {
            totalMat = new MatriculasDAO().listarParaTabela().size();
        } catch (Exception ex) {
        }
        try {
            totalProd = new ProdutoDAO().listarTodos().size();
        } catch (Exception ex) {
        }
        try {
            for (Venda v : new VendaDAO().listarVendasComNome()) {
                totalVendas += v.getValorTotal();
            }
        } catch (Exception ex) {
        }

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 12, 0));
        statsRow.setOpaque(false);
        statsRow.setPreferredSize(new Dimension(0, 100));
        statsRow.add(criarStat(String.valueOf(totalAlunos), "Total de Alunos", COR_ACCENT2));
        statsRow.add(criarStat(String.valueOf(totalMat), "Matrículas Ativas", COR_SUCCESS));
        statsRow.add(criarStat(String.valueOf(totalProd), "Produtos Cadastrados", COR_WARNING));
        statsRow.add(criarStat("R$ " + String.format("%.0f", totalVendas), "Receita em Vendas", new Color(0xAB47BC)));

        JTabbedPane abas = new JTabbedPane();
        abas.setBackground(COR_BG_PANEL);
        abas.setForeground(COR_TEXTO);
        abas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        abas.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            protected void installDefaults() {
                super.installDefaults();
                highlight = COR_BG_CARD;
                lightHighlight = COR_BG_PANEL;
                shadow = COR_BORDER;
                darkShadow = COR_BG_MAIN;
                focus = COR_ACCENT;
            }
        });
        abas.addTab("👤 Alunos", criarAbaAlunos());
        abas.addTab("✅ Matrículas", criarAbaMatriculas());
        abas.addTab("💰 Vendas", criarAbaVendas());

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                painelEnvolver(statsRow), abas);
        split.setDividerLocation(112);
        split.setDividerSize(6);
        split.setResizeWeight(0.0);
        split.setOpaque(false);
        split.setBorder(null);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setOpaque(false);
        JButton btnFechar = criarBotaoSecundario("← Fechar");
        btnFechar.addActionListener(e -> dispose());
        rodape.add(btnFechar);

        root.add(split, BorderLayout.CENTER);
        root.add(rodape, BorderLayout.SOUTH);

        painelConteudo.removeAll();
        painelConteudo.add(root, BorderLayout.CENTER);
        painelConteudo.revalidate();
    }

    private JPanel painelEnvolver(JPanel p) {
        JPanel w = new JPanel(new BorderLayout());
        w.setOpaque(false);
        w.add(p, BorderLayout.CENTER);
        return w;
    }

    private JPanel criarStat(String valor, String label, Color cor) {
        JPanel card = new JPanel(new BorderLayout(0, 4));
        card.setBackground(COR_BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDER, 1),
                BorderFactory.createEmptyBorder(14, 20, 14, 20)));
        JLabel lblV = new JLabel(valor, SwingConstants.CENTER);
        lblV.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblV.setForeground(cor);
        JLabel lblL = new JLabel(label, SwingConstants.CENTER);
        lblL.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblL.setForeground(COR_TEXTO_SEC);
        card.add(lblV, BorderLayout.CENTER);
        card.add(lblL, BorderLayout.SOUTH);
        return card;
    }

    private JPanel criarAbaAlunos() {
        JPanel p = abaPanel();
        String[] cols = {"ID", "Nome", "CPF", "Telefone", "E-mail", "Data Cadastro"};
        DefaultTableModel m = modelo(cols);
        JTable t = new JTable(m);
        estilizarTabela(t);
        try {
            for (Alunos a : new AlunosDAO().listarTodos()) {
                m.addRow(new Object[]{a.getId(), a.getNome(), a.getCPF(), a.getTelefone(), a.getEmail(), a.getdataCadastro()});
            }
        } catch (Exception ex) {
        }
        p.add(criarScrollEscuro(t), BorderLayout.CENTER);
        return p;
    }

    private JPanel criarAbaMatriculas() {
        JPanel p = abaPanel();
        String[] cols = {"ID", "Aluno", "Plano", "Valor", "Data Início", "Status"};
        DefaultTableModel m = modelo(cols);
        JTable t = new JTable(m);
        estilizarTabela(t);
        try {
            for (Matriculas mt : new MatriculasDAO().listarParaTabela()) {
                m.addRow(new Object[]{mt.getId(), mt.getAluno().getNome(), mt.getPlano().getNomePlano(),
                    "R$ " + String.format("%.2f", mt.getPlano().getValor()), mt.getDataInicio(), mt.getStatusPagamento()});
            }
        } catch (Exception ex) {
        }
        p.add(criarScrollEscuro(t), BorderLayout.CENTER);
        return p;
    }

    private JPanel criarAbaVendas() {
        JPanel p = abaPanel();
        String[] cols = {"ID", "Produto", "Quantidade", "Valor Total (R$)", "Data/Hora"};
        DefaultTableModel m = modelo(cols);
        JTable t = new JTable(m);
        estilizarTabela(t);
        try {
            for (Venda v : new VendaDAO().listarVendasComNome()) {
                m.addRow(new Object[]{v.getId(), v.getNomeProduto(), v.getQuantidade(),
                    String.format("%.2f", v.getValorTotal()), v.getDataVenda()});
            }
        } catch (Exception ex) {
        }
        p.add(criarScrollEscuro(t), BorderLayout.CENTER);
        return p;
    }

    private JPanel abaPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(COR_BG_PANEL);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return p;
    }

    private DefaultTableModel modelo(String[] cols) {
        return new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
    }
}
