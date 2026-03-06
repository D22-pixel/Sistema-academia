
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author denis
 */
public class TelaMatriculas extends TelaBase {

    private JComboBox<Alunos>  comboAlunos;
    private JComboBox<Planos>  comboPlanos;
    private JComboBox<String>  comboStatus;
    private JTextArea          txtResumo;

    public TelaMatriculas() {
        super("Nova Matrícula");
        setSize(760, 580);
        setLocationRelativeTo(null);
        inicializarUI();
    }

    private void inicializarUI() {
        txtResumo = new JTextArea(9, 40);
        txtResumo.setEditable(false);
        txtResumo.setBackground(COR_BG_CARD);
        txtResumo.setForeground(COR_TEXTO);
        txtResumo.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtResumo.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("✅  Nova Matrícula"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Vincule um aluno a um plano de mensalidade"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        JPanel cardForm = criarCard("Dados da Matrícula");
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboAlunos = new JComboBox<>();
        estilizarCombo(comboAlunos);
        carregarAlunos();

        comboPlanos = new JComboBox<>();
        estilizarCombo(comboPlanos);
        comboPlanos.addActionListener(e -> atualizarResumo());
        carregarPlanos();

        String[] status = {"Ativa", "Pendente", "Cancelada"};
        comboStatus = new JComboBox<>(status);
        estilizarCombo(comboStatus);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        form.add(criarLabel("Aluno:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        form.add(comboAlunos, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        form.add(criarLabel("Plano:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        form.add(comboPlanos, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        form.add(criarLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1;
        form.add(comboStatus, gbc);

        cardForm.add(form, BorderLayout.CENTER);

        JPanel cardResumo = criarCard("Resumo da Matrícula");
        JScrollPane sc = new JScrollPane(txtResumo);
        sc.setBackground(COR_BG_CARD);
        sc.getViewport().setBackground(COR_BG_CARD);
        sc.setBorder(BorderFactory.createLineBorder(COR_BORDER, 1));
        cardResumo.add(sc, BorderLayout.CENTER);

        JPanel centro = new JPanel(new BorderLayout(0, 14));
        centro.setOpaque(false);
        centro.add(cardForm,   BorderLayout.NORTH);
        centro.add(cardResumo, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rodape.setOpaque(false);
        JButton btnMatricular = criarBotaoPrimario("✔  Confirmar Matrícula");
        btnMatricular.addActionListener(e -> realizarMatricula());
        JButton btnVoltar = criarBotaoSecundario("← Voltar");
        btnVoltar.addActionListener(e -> dispose());
        rodape.add(btnMatricular);
        rodape.add(btnVoltar);

        root.add(centro, BorderLayout.CENTER);
        root.add(rodape, BorderLayout.SOUTH);

        painelConteudo.removeAll();
        painelConteudo.add(root, BorderLayout.CENTER);
        painelConteudo.revalidate();
        atualizarResumo();
    }

    private void carregarAlunos() {
        List<Alunos> lista = new AlunosDAO().listarTodos();
        if (lista == null || lista.isEmpty()) {
            comboAlunos.addItem(null);
            JOptionPane.showMessageDialog(this, "Não há alunos cadastrados!");
        } else {
            for (Alunos a : lista) comboAlunos.addItem(a);
        }
    }

    private void carregarPlanos() {
        comboPlanos.removeAllItems();
        List<Planos> lista = new PlanosDAO().listarTodos();
        if (lista.isEmpty()) comboPlanos.addItem(null);
        else for (Planos p : lista) comboPlanos.addItem(p);
    }

    private void atualizarResumo() {
        Alunos a = (Alunos) comboAlunos.getSelectedItem();
        Planos p = (Planos) comboPlanos.getSelectedItem();
        if (a == null || p == null) { txtResumo.setText("Selecione um aluno e um plano."); return; }
        StringBuilder sb = new StringBuilder();
        sb.append("─".repeat(48)).append("\n");
        sb.append("         RESUMO DA MATRÍCULA\n");
        sb.append("─".repeat(48)).append("\n\n");
        sb.append("ALUNO\n  Nome:     ").append(a.getNome()).append("\n");
        sb.append("  CPF:      ").append(a.getCPF()).append("\n");
        if (a.getTelefone() != null) sb.append("  Telefone: ").append(a.getTelefone()).append("\n");
        sb.append("\nPLANO\n  ").append(p.getNomePlano()).append("\n");
        if (p.getDescricao() != null) sb.append("  ").append(p.getDescricao()).append("\n");
        sb.append("  Duração: ").append(p.getDuracaoDias()).append(" dias\n");
        sb.append("\nVALOR   R$ ").append(String.format("%.2f", p.getValor())).append("\n");
        sb.append("\n").append("─".repeat(48)).append("\n");
        txtResumo.setText(sb.toString());
    }

    private void realizarMatricula() {
        Alunos a = (Alunos) comboAlunos.getSelectedItem();
        Planos p = (Planos) comboPlanos.getSelectedItem();
        String status = (String) comboStatus.getSelectedItem();
        if (a == null || p == null) {
            JOptionPane.showMessageDialog(this, "Selecione aluno e plano!"); return;
        }
        for (Matriculas m : DadosSistema.getInstancia().getMatriculasPorAluno(a)) {
            if ("Ativa".equals(m.getStatusPagamento())) {
                int op = JOptionPane.showConfirmDialog(this,
                        "Aluno já possui matrícula ativa!\nCancelar atual e criar nova?",
                        "Aviso", JOptionPane.YES_NO_OPTION);
                if (op == JOptionPane.YES_OPTION) m.setStatusPagamento("Cancelada");
                else return;
            }
        }
        Matriculas nova = new Matriculas(a, p, status);
        MatriculasDAO dao = new MatriculasDAO();
        if (dao.salvar(nova)) {
            DadosSistema.getInstancia().adicionarMatricula(nova);
            JOptionPane.showMessageDialog(this,
                    "Matrícula realizada!\n\nAluno: " + a.getNome()
                    + "\nPlano: " + p.getNomePlano()
                    + "\nValor: R$ " + String.format("%.2f", p.getValor()),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar matrícula!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
