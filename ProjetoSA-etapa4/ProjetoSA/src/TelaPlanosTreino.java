
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
public class TelaPlanosTreino extends TelaBase {
    
  private JComboBox<Alunos>  comboAlunos;
    private JComboBox<String>  comboObjetivo, comboFrequencia;
    private JTextArea          txtDescricao;
    private JTable             tabelaPlanos;
    private DefaultTableModel  modeloTabela;

    public TelaPlanosTreino() {
        super("Planos de Treino");
        setSize(1060, 800);
        setLocationRelativeTo(null);
        inicializarUI();
        carregarPlanosTreino();
    }

    private void inicializarUI() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("💪  Planos de Treino"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Criar e gerenciar planos de treino dos alunos"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        JPanel cardForm = criarCard("Novo Plano de Treino");
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboAlunos = new JComboBox<>();
        estilizarCombo(comboAlunos);
        carregarAlunos();

        String[] objetivos = {"Emagrecimento","Ganho de Massa Muscular","Condicionamento Físico",
                "Definição Muscular","Reabilitação","Hipertrofia"};
        comboObjetivo = new JComboBox<>(objetivos);
        estilizarCombo(comboObjetivo);

        String[] freqs = {"2x por semana","3x por semana","4x por semana",
                "5x por semana","6x por semana","Diariamente"};
        comboFrequencia = new JComboBox<>(freqs);
        estilizarCombo(comboFrequencia);

        txtDescricao = new JTextArea(3, 30);
        txtDescricao.setBackground(COR_BG_CARD);
        txtDescricao.setForeground(COR_TEXTO);
        txtDescricao.setCaretColor(COR_TEXTO);
        txtDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));

        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=1; form.add(criarLabel("Aluno:"), gbc);
        gbc.gridx=1; gbc.gridwidth=3; form.add(comboAlunos, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=1; form.add(criarLabel("Objetivo:"), gbc);
        gbc.gridx=1; gbc.gridwidth=2; form.add(comboObjetivo, gbc);
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1; form.add(criarLabel("Frequência Semanal:"), gbc);
        gbc.gridx=1; gbc.gridwidth=2; form.add(comboFrequencia, gbc);
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=1; form.add(criarLabel("Descrição:"), gbc);
        gbc.gridx=1; gbc.gridwidth=3; form.add(new JScrollPane(txtDescricao), gbc);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        btnRow.setOpaque(false);
        JButton btnSalvar = criarBotaoPrimario("✔  Salvar Plano");
        btnSalvar.addActionListener(e -> salvarPlanoTreino());
        JButton btnLimpar = criarBotaoSecundario("✕  Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        btnRow.add(btnSalvar); btnRow.add(btnLimpar);

        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=4; form.add(btnRow, gbc);
        cardForm.add(form, BorderLayout.CENTER);

        JPanel cardTab = criarCard("Planos de Treino Cadastrados");
        String[] cols = {"ID","Aluno","Objetivo","Frequência","Data Criação","Descrição"};
        modeloTabela = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaPlanos = new JTable(modeloTabela);
        estilizarTabela(tabelaPlanos);
        cardTab.add(criarScrollEscuro(tabelaPlanos), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cardForm, cardTab);
        split.setDividerLocation(360);
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

    private void carregarAlunos() {
        comboAlunos.removeAllItems();
        List<Alunos> lista = new AlunosDAO().listarTodos();
        if (lista.isEmpty()) JOptionPane.showMessageDialog(this, "Não há alunos cadastrados!");
        else for (Alunos a : lista) comboAlunos.addItem(a);
    }

    private void salvarPlanoTreino() {
        Alunos a = (Alunos) comboAlunos.getSelectedItem();
        if (a == null) { JOptionPane.showMessageDialog(this, "Selecione um aluno!"); return; }
        try {
            PlanoTreino pt = new PlanoTreino(a.getNome(),
                    comboObjetivo.getSelectedItem().toString(),
                    comboFrequencia.getSelectedItem().toString(),
                    txtDescricao.getText().trim());
            pt.setId(0);
            new PlanoTreinoDAO().salvarOuAtualizar(pt);
            JOptionPane.showMessageDialog(this, "Plano de treino salvo!");
            limparCampos(); carregarPlanosTreino();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void limparCampos() {
        if (comboAlunos.getItemCount() > 0) comboAlunos.setSelectedIndex(0);
        comboObjetivo.setSelectedIndex(0); comboFrequencia.setSelectedIndex(0);
        txtDescricao.setText("");
    }

    private void carregarPlanosTreino() {
        modeloTabela.setRowCount(0);
        for (PlanoTreino pt : new PlanoTreinoDAO().listarTodos()) {
            String desc = pt.getDescricao() != null ? pt.getDescricao() : "";
            modeloTabela.addRow(new Object[]{pt.getId(), pt.getAluno(), pt.getObjetivo(),
                    pt.getFrequencia(), pt.getDataCriacao(),
                    desc.length() > 50 ? desc.substring(0, 47) + "..." : desc});
        }
    }
}
