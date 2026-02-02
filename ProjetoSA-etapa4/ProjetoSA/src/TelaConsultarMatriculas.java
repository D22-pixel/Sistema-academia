
import java.awt.BorderLayout;
import java.awt.Color;
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
public class TelaConsultarMatriculas extends JFrame{
    private JTable tabelaMatriculas;
    private DefaultTableModel modeloTabela;
    private JButton btnAtualizar, btnVoltar;
    private JComboBox<String> comboFiltro;
    
    public TelaConsultarMatriculas() {
        setTitle("Consultar Matrículas");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
        carregarMatriculas();
    }
    
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(Color.white);

        JLabel lblTitulo = new JLabel("CONSULTAR MATRÍCULAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0,10,10));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
   
        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFiltros.setBackground(Color.white);
        painelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        JLabel lblFiltro = new JLabel("Status:");
        lblFiltro.setFont(new Font("Arial", Font.BOLD, 12));
        painelFiltros.add(lblFiltro);
        
        String[] opcoesFiltro = {"Todas", "Ativa", "Pendente", "Cancelada"};
        comboFiltro = new JComboBox<>(opcoesFiltro);
        comboFiltro.setFont(new Font("Arial", Font.PLAIN, 12));
        comboFiltro.addActionListener(e -> carregarMatriculas());
        painelFiltros.add(comboFiltro);
        
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(new Color(250,250,250));
        btnAtualizar.setForeground(Color.black);
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 11));
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.addActionListener(e -> carregarMatriculas());
        painelFiltros.add(btnAtualizar);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(Color.white);
        painelTabela.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 10, 10), 2),
            "Matrículas Cadastradas",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(0, 10, 10)
        ));
        
        String[] colunas = {"ID", "Aluno", "CPF", "Plano", "Valor", "Data Início", "Data Fim", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaMatriculas = new JTable(modeloTabela);
        tabelaMatriculas.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaMatriculas.setRowHeight(25);
        tabelaMatriculas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaMatriculas.getTableHeader().setBackground(new Color(0, 10, 10));
        tabelaMatriculas.getTableHeader().setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(tabelaMatriculas);
        painelTabela.add(scrollPane, BorderLayout.CENTER);
 
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setBackground(Color.white);
        painelCentral.add(painelFiltros, BorderLayout.NORTH);
        painelCentral.add(painelTabela, BorderLayout.CENTER);
        
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(Color.white);
        
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBackground(new Color(250,250,250));
        btnVoltar.setForeground(Color.black);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnVoltar);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void carregarMatriculas() {
        modeloTabela.setRowCount(0);
        List<Matriculas> matriculas = DadosSistema.getInstancia().getMatriculas();
        String filtroSelecionado = (String) comboFiltro.getSelectedItem();
        
        for (Matriculas matricula : matriculas) {

            if (!filtroSelecionado.equals("Todas") && 
                !matricula.getStatusPagamento().equals(filtroSelecionado)) {
                continue;
            }
            
            Object[] linha = {
                matricula.getId(),
                matricula.getAluno().getNome(),
                matricula.getAluno().getCPF(),
                matricula.getPlano().getNomePlano(),
                "R$ " + String.format("%.2f", matricula.getPlano().getValor()),
                matricula.getDataInicio(),
                matricula.getDataFim(),
                matricula.getStatusPagamento()
            };
            modeloTabela.addRow(linha);
        }

        atualizarTotalMatriculas();
    }
    
    private void atualizarTotalMatriculas() {
        int total = modeloTabela.getRowCount();
        setTitle("Consultar Matrículas - Total: " + total + " matrícula(s)");
    }
}

