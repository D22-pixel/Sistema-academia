
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class TelaGerenciarPlanos extends TelaBase {
    private JTextField txtNomePlano, txtValor, txtDuracao;
    private JTextArea txtDescricao;
    private JButton btnSalvar, btnLimpar, btnExcluir;
    private JTable tabelaPlanos;
    private DefaultTableModel modeloTabela;

    public TelaGerenciarPlanos() {
        super("Gerenciar Planos");
        setSize(1060, 780);
        setLocationRelativeTo(null);
        inicializarUI();
        carregarPlanos();
    }

    private void inicializarUI() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("📋  Gerenciamento de Planos"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Cadastrar e gerenciar planos de mensalidade"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        JPanel cardForm = criarCard("Dados do Plano");
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNomePlano = criarCampoTexto(26);
        txtValor     = criarCampoTexto(14);
        txtDuracao   = criarCampoTexto(14);
        txtDescricao = new JTextArea(3, 26);
        txtDescricao.setBackground(COR_BG_CARD);
        txtDescricao.setForeground(COR_TEXTO);
        txtDescricao.setCaretColor(COR_TEXTO);
        txtDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));

        addRow(form, gbc, 0, "Nome do Plano:", txtNomePlano, 3);
        addRow(form, gbc, 1, "Valor (R$):",    txtValor,     3);
        addRow(form, gbc, 2, "Duração (dias):",txtDuracao,   3);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        form.add(criarLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        form.add(new JScrollPane(txtDescricao), gbc);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        btnRow.setOpaque(false);
        btnSalvar  = criarBotaoPrimario("✔  Salvar Plano");
        btnSalvar.addActionListener(e -> salvarPlano());
        btnLimpar  = criarBotaoSecundario("✕  Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        btnExcluir = criarBotaoDanger("🗑  Excluir");
        btnExcluir.addActionListener(e -> excluirPlano());
        btnRow.add(btnSalvar); btnRow.add(btnLimpar); btnRow.add(btnExcluir);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        form.add(btnRow, gbc);
        cardForm.add(form, BorderLayout.CENTER);

        JPanel cardTab = criarCard("Planos Cadastrados");
        String[] cols = {"ID", "Nome do Plano", "Valor (R$)", "Duração (dias)", "Descrição"};
        modeloTabela = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaPlanos = new JTable(modeloTabela);
        estilizarTabela(tabelaPlanos);
        cardTab.add(criarScrollEscuro(tabelaPlanos), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cardForm, cardTab);
        split.setDividerLocation(340);
        split.setDividerSize(6);
        split.setResizeWeight(0.4);
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

    private void addRow(JPanel f, GridBagConstraints g, int row,
                        String lbl, JTextField tf, int span) {
        g.gridx = 0; g.gridy = row; g.gridwidth = 1;
        f.add(criarLabel(lbl), g);
        g.gridx = 1; g.gridwidth = span;
        f.add(tf, g);
    }

    private void salvarPlano() {
        String nome  = txtNomePlano.getText().trim(),
               valTx = txtValor.getText().trim(),
               durTx = txtDuracao.getText().trim(),
               desc  = txtDescricao.getText().trim();
        if (nome.isEmpty() || valTx.isEmpty() || durTx.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!"); return;
        }
        try {
            double val = Double.parseDouble(valTx); int dur = Integer.parseInt(durTx);
            if (val <= 0 || dur <= 0) { JOptionPane.showMessageDialog(this, "Valor e duração devem ser > 0!"); return; }
            Planos p = new Planos(nome, desc, val, dur); p.setId(0);
            new PlanosDAO().salvarOuAtualizar(p);
            JOptionPane.showMessageDialog(this, "Plano cadastrado com sucesso!");
            limparCampos(); atualizarTabela();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor e duração devem ser numéricos!");
        }
    }

    private void excluirPlano() {
        int row = tabelaPlanos.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Selecione um plano!"); return; }
        if (JOptionPane.showConfirmDialog(this, "Excluir este plano?",
                "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            new PlanosDAO().excluir((int) tabelaPlanos.getValueAt(row, 0));
            atualizarTabela();
        }
    }

    private void limparCampos() {
        txtNomePlano.setText(""); txtValor.setText(""); txtDuracao.setText(""); txtDescricao.setText("");
    }

    private void carregarPlanos() {
        modeloTabela.setRowCount(0);
        for (Planos p : DadosSistema.getInstancia().getPlanos())
            modeloTabela.addRow(new Object[]{p.getId(), p.getNomePlano(),
                    String.format("%.2f", p.getValor()), p.getDuracaoDias(),
                    p.getDescricao() != null ? p.getDescricao() : "-"});
    }

    public void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Planos p : new PlanosDAO().listarTodos())
            modeloTabela.addRow(new Object[]{p.getId(), p.getNomePlano(),
                    p.getValor(), p.getDuracaoDias(), p.getDescricao()});
    }
}    