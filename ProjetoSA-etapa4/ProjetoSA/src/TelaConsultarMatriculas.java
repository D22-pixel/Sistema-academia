
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
public class TelaConsultarMatriculas extends TelaBase{
    private JTable tabelaMatriculas;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> comboFiltro;

    public TelaConsultarMatriculas() {
        super("Consultar Matrículas");
        setSize(1060, 760);
        setLocationRelativeTo(null);
        inicializarUI();
        carregarMatriculas();
    }

    private void inicializarUI() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("🔍  Consultar Matrículas"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Visualize e filtre as matrículas cadastradas"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        JPanel filtroCard = criarCard("Filtros");
        filtroCard.setPreferredSize(new Dimension(0, 80));
        JPanel filtroRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        filtroRow.setOpaque(false);
        filtroRow.add(criarLabel("Status:"));
        String[] opcoes = {"Todas", "Ativa", "Pendente", "Cancelada"};
        comboFiltro = new JComboBox<>(opcoes);
        estilizarCombo(comboFiltro);
        comboFiltro.addActionListener(e -> carregarMatriculas());
        filtroRow.add(comboFiltro);
        JButton btnAtualizar = criarBotaoSecundario("↺ Atualizar");
        btnAtualizar.addActionListener(e -> carregarMatriculas());
        filtroRow.add(btnAtualizar);
        filtroCard.add(filtroRow, BorderLayout.CENTER);

        JPanel cardTab = criarCard("Matrículas Cadastradas");
        String[] cols = {"ID", "Aluno", "CPF", "Plano", "Valor", "Data Início", "Data Fim", "Status"};
        modeloTabela = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaMatriculas = new JTable(modeloTabela);
        estilizarTabela(tabelaMatriculas);
        tabelaMatriculas.getColumnModel().getColumn(7).setCellRenderer(
                (table, value, sel, foc, row, col) -> {
                    JLabel lbl = new JLabel(value != null ? value.toString() : "", SwingConstants.CENTER);
                    lbl.setOpaque(true);
                    String v = value != null ? value.toString() : "";
                    lbl.setBackground(sel ? COR_ACCENT2 : COR_BG_PANEL);
                    lbl.setForeground("Ativa".equals(v) ? COR_SUCCESS : "Cancelada".equals(v) ? COR_DANGER : COR_WARNING);
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    return lbl;
                });
        cardTab.add(criarScrollEscuro(tabelaMatriculas), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filtroCard, cardTab);
        split.setDividerLocation(90);
        split.setDividerSize(6);
        split.setResizeWeight(0.0); 
        split.setOpaque(false);
        split.setBorder(null);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setOpaque(false);
        JButton btnVoltar = criarBotaoSecundario("← Voltar ao Menu");
        btnVoltar.addActionListener(e -> dispose());
        rodape.add(btnVoltar);

        root.add(split,  BorderLayout.CENTER);
        root.add(rodape, BorderLayout.SOUTH);

        painelConteudo.removeAll();
        painelConteudo.add(root, BorderLayout.CENTER);
        painelConteudo.revalidate();
    }

    private void carregarMatriculas() {
        modeloTabela.setRowCount(0);
        String filtro = (String) comboFiltro.getSelectedItem();
        for (Matriculas m : new MatriculasDAO().listarParaTabela()) {
            if (!"Todas".equals(filtro) && !m.getStatusPagamento().equals(filtro)) continue;
            modeloTabela.addRow(new Object[]{
                m.getId(), m.getAluno().getNome(), m.getAluno().getCPF(),
                m.getPlano().getNomePlano(),
                "R$ " + String.format("%.2f", m.getPlano().getValor()),
                m.getDataInicio(), "---", m.getStatusPagamento()
            });
        }
        setTitle("Consultar Matrículas — " + modeloTabela.getRowCount() + " registro(s)");
    }
}

