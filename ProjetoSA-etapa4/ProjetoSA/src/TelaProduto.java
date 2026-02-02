
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author denis
 */
public class TelaProduto extends TelaBase {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtNome, txtPreco, txtEstoque;
    private JComboBox<String> cbUnidade, cbTamanho;
    private JButton btnSalvar;
    private Produto produtoSelecionado = null;

    public TelaProduto() {
        super("Gerenciamento de Produtos");
        setSize(1060, 760);
        setLocationRelativeTo(null);
        inicializarUI();
        carregarProdutos();
    }

    private void inicializarUI() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("📦  Gerenciamento de Produtos"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Cadastrar, editar e controlar o estoque"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        JPanel cardTabela = criarCard("Produtos");
        JPanel barraAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        barraAcoes.setOpaque(false);
        JButton btnNovo    = criarBotaoPrimario("＋ Novo Produto");
        btnNovo.addActionListener(e -> limparFormulario());
        JButton btnExcluir = criarBotaoDanger("🗑 Excluir Selecionado");
        btnExcluir.addActionListener(e -> excluirProduto());
        JButton btnEstoque = criarBotaoSecundario("🔍 Consultar Estoque");
        btnEstoque.addActionListener(e -> new TelaConsultaEstoque().setVisible(true));
        barraAcoes.add(btnNovo); barraAcoes.add(btnExcluir); barraAcoes.add(btnEstoque);
        cardTabela.add(barraAcoes, BorderLayout.NORTH);

        String[] cols = {"ID","Nome","Tam.","Unid.","Preço (R$)","Estoque","Status"};
        modeloTabela = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        estilizarTabela(tabela);
        tabela.getColumnModel().getColumn(6).setCellRenderer(
                (t, val, sel, foc, row, col) -> {
                    JLabel lbl = new JLabel(val != null ? val.toString() : "", SwingConstants.CENTER);
                    lbl.setOpaque(true);
                    String v = val != null ? val.toString() : "";
                    lbl.setBackground(sel ? COR_ACCENT2 : COR_BG_PANEL);
                    lbl.setForeground("Normal".equals(v) ? COR_SUCCESS : "Sem Estoque".equals(v) ? COR_DANGER : COR_WARNING);
                    lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    return lbl;
                });
        int[] larg = {40,180,55,55,90,70,110};
        for (int i=0;i<larg.length;i++) tabela.getColumnModel().getColumn(i).setPreferredWidth(larg[i]);
        tabela.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) preencherFormulario(); });
        cardTabela.add(criarScrollEscuro(tabela), BorderLayout.CENTER);

        JPanel cardForm = criarCard("Dados do Produto");
        cardForm.setPreferredSize(new Dimension(280, 0));
        JPanel campos = new JPanel();
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
        campos.setOpaque(false);
        campos.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));

        txtNome    = criarCampoTexto(18);
        txtPreco   = criarCampoTexto(18);
        txtEstoque = criarCampoTexto(18);
        cbUnidade  = new JComboBox<>(new String[]{"UN","KG","LT","PC","MT"});
        cbTamanho  = new JComboBox<>(new String[]{"N/A","P","M","G","GG","38","40","42"});
        estilizarCombo(cbUnidade); estilizarCombo(cbTamanho);

        campos.add(campoPainel("Nome do Produto:", txtNome));
        campos.add(Box.createVerticalStrut(8));
        campos.add(comboPainel("Unidade:", cbUnidade));
        campos.add(Box.createVerticalStrut(8));
        campos.add(comboPainel("Tamanho:", cbTamanho));
        campos.add(Box.createVerticalStrut(8));
        campos.add(campoPainel("Preço (R$):", txtPreco));
        campos.add(Box.createVerticalStrut(8));
        campos.add(campoPainel("Estoque:", txtEstoque));
        campos.add(Box.createVerticalStrut(16));

        btnSalvar = criarBotaoPrimario("✔  Salvar Produto");
        btnSalvar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnSalvar.setAlignmentX(0);
        btnSalvar.addActionListener(e -> salvarProduto());
        campos.add(btnSalvar);

        cardForm.add(campos, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(12, 0));
        centro.setOpaque(false);
        centro.add(cardTabela, BorderLayout.CENTER);
        centro.add(cardForm,   BorderLayout.EAST);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setOpaque(false);
        JButton btnVoltar = criarBotaoSecundario("← Voltar ao Menu");
        btnVoltar.addActionListener(e -> dispose());
        rodape.add(btnVoltar);

        root.add(centro, BorderLayout.CENTER);
        root.add(rodape, BorderLayout.SOUTH);

        painelConteudo.removeAll();
        painelConteudo.add(root, BorderLayout.CENTER);
        painelConteudo.revalidate();
    }

    private JPanel campoPainel(String lbl, JTextField tf) {
        JPanel p = new JPanel(new BorderLayout(0,3)); p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
        JLabel l = criarLabel(lbl); l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        p.add(l, BorderLayout.NORTH); p.add(tf, BorderLayout.CENTER);
        return p;
    }

    private JPanel comboPainel(String lbl, JComboBox<String> cb) {
        JPanel p = new JPanel(new BorderLayout(0,3)); p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
        JLabel l = criarLabel(lbl); l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        p.add(l, BorderLayout.NORTH); p.add(cb, BorderLayout.CENTER);
        return p;
    }

    private void carregarProdutos() {
        modeloTabela.setRowCount(0);
        for (Produto p : new ProdutoDAO().listarTodos()) {
            String st = p.getEstoque()==0?"Sem Estoque":p.getEstoque()<=5?"Estoque Baixo":"Normal";
            modeloTabela.addRow(new Object[]{p.getId(),p.getNome(),p.getTamanho(),
                    p.getUnidade(),String.format("%.2f",p.getPreco()),p.getEstoque(),st});
        }
    }

    private void preencherFormulario() {
        int linha = tabela.getSelectedRow(); if (linha<0) return;
        produtoSelecionado = new ProdutoDAO().buscarPorId((int)modeloTabela.getValueAt(linha,0));
        if (produtoSelecionado!=null) {
            txtNome.setText(produtoSelecionado.getNome());
            txtPreco.setText(String.valueOf(produtoSelecionado.getPreco()));
            txtEstoque.setText(String.valueOf(produtoSelecionado.getEstoque()));
            cbUnidade.setSelectedItem(produtoSelecionado.getUnidade());
            cbTamanho.setSelectedItem(produtoSelecionado.getTamanho());
            btnSalvar.setText("✔  Atualizar Produto");
        }
    }

    private void limparFormulario() {
        produtoSelecionado=null; txtNome.setText(""); txtPreco.setText(""); txtEstoque.setText("");
        cbUnidade.setSelectedIndex(0); cbTamanho.setSelectedIndex(0);
        tabela.clearSelection(); btnSalvar.setText("✔  Salvar Produto");
    }

    private void salvarProduto() {
        String nome=txtNome.getText().trim(), prTx=txtPreco.getText().trim().replace(",","."),
               estTx=txtEstoque.getText().trim();
        if (nome.isEmpty()||prTx.isEmpty()||estTx.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Preencha todos os campos."); return;
        }
        try {
            double preco=Double.parseDouble(prTx); int estoque=Integer.parseInt(estTx);
            if (preco<0||estoque<0) throw new NumberFormatException();
            Produto p=(produtoSelecionado!=null)?produtoSelecionado:new Produto();
            p.setNome(nome); p.setPreco(preco); p.setEstoque(estoque);
            p.setUnidade(cbUnidade.getSelectedItem().toString());
            p.setTamanho(cbTamanho.getSelectedItem().toString());
            new ProdutoDAO().salvarOuAtualizar(p);
            JOptionPane.showMessageDialog(this,"Produto salvo!");
            limparFormulario(); carregarProdutos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Preço e estoque devem ser números positivos.","Erro",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirProduto() {
        int linha=tabela.getSelectedRow(); if (linha<0) { JOptionPane.showMessageDialog(this,"Selecione um produto."); return; }
        int id=(int)modeloTabela.getValueAt(linha,0);
        String n=modeloTabela.getValueAt(linha,1).toString();
        if (JOptionPane.showConfirmDialog(this,"Excluir \""+n+"\"?","Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            new ProdutoDAO().excluir(id); limparFormulario(); carregarProdutos();
        }
    }
}
