
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author denis
 */
public class TelaConsultaEstoque extends TelaBase {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField txtBusca;

    public TelaConsultaEstoque() {
        super("Consulta de Estoque");
        setSize(960, 700);
        setLocationRelativeTo(null);
        inicializarUI();
        carregarDados("");
    }

    private void inicializarUI() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("🔍  Consulta de Estoque"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Pesquise produtos e verifique os níveis de estoque"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        JPanel filtroCard = criarCard("Pesquisar");
        filtroCard.setPreferredSize(new Dimension(0, 80));
        JPanel filtroRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filtroRow.setOpaque(false);
        filtroRow.add(criarLabel("Produto:"));
        txtBusca = criarCampoTexto(28);
        filtroRow.add(txtBusca);
        JButton btnBuscar = criarBotaoPrimario("🔍 Buscar");
        btnBuscar.addActionListener(e -> carregarDados(txtBusca.getText().trim()));
        JButton btnLimpar = criarBotaoSecundario("✕ Limpar");
        btnLimpar.addActionListener(e -> { txtBusca.setText(""); carregarDados(""); });
        filtroRow.add(btnBuscar);
        filtroRow.add(btnLimpar);
        filtroCard.add(filtroRow, BorderLayout.CENTER);

        JPanel cardTab = criarCard("Produtos em Estoque");
        String[] cols = {"ID","Nome do Produto","Tamanho","Unid.","Estoque Atual","Preço (R$)"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modelo);
        estilizarTabela(tabela);
        tabela.getColumnModel().getColumn(4).setCellRenderer(
                (t, val, sel, foc, row, col) -> {
                    JLabel lbl = new JLabel(val != null ? val.toString() : "", SwingConstants.CENTER);
                    lbl.setOpaque(true);
                    int est = val != null ? Integer.parseInt(val.toString()) : 0;
                    lbl.setBackground(sel ? COR_ACCENT2 : COR_BG_PANEL);
                    lbl.setForeground(est==0 ? COR_DANGER : est<=5 ? COR_WARNING : COR_SUCCESS);
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    return lbl;
                });
        cardTab.add(criarScrollEscuro(tabela), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filtroCard, cardTab);
        split.setDividerLocation(90);
        split.setDividerSize(6);
        split.setResizeWeight(0.0);
        split.setOpaque(false);
        split.setBorder(null);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setOpaque(false);
        JButton btnFechar = criarBotaoSecundario("← Fechar");
        btnFechar.addActionListener(e -> dispose());
        rodape.add(btnFechar);

        root.add(split,  BorderLayout.CENTER);
        root.add(rodape, BorderLayout.SOUTH);

        painelConteudo.removeAll();
        painelConteudo.add(root, BorderLayout.CENTER);
        painelConteudo.revalidate();
    }

    private void carregarDados(String filtro) {
        modelo.setRowCount(0);
        for (Produto p : new ProdutoDAO().listarTodos()) {
            if (filtro.isEmpty() || p.getNome().toLowerCase().contains(filtro.toLowerCase()))
                modelo.addRow(new Object[]{p.getId(),p.getNome(),p.getTamanho(),
                        p.getUnidade(),p.getEstoque(),String.format("%.2f",p.getPreco())});
        }
    }
}
