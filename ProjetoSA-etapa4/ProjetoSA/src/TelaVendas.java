/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.List;
/**
 *
 * @author denis
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TelaVendas extends TelaBase {

    private JTable tabelaHistorico;
    private DefaultTableModel modeloHistorico;
    private JComboBox<ProdutoItem> cbProduto;
    private JTextField txtQuantidade;
    private JLabel lblPrecoUn, lblTotal, lblEstoqueDisp, lblUnidade, lblTamanho;

    private static class ProdutoItem {

        final Produto produto;

        ProdutoItem(Produto p) {
            this.produto = p;
        }

        public String toString() {
            return String.format("[%d] %s (%s) - %s",
                    produto.getId(), produto.getNome(), produto.getTamanho(), produto.getUnidade());
        }
    }

    public TelaVendas() {
        super("Registro de Vendas");
        setSize(1060, 760);
        setLocationRelativeTo(null);
        inicializarUI();
        carregarProdutos();
        carregarHistorico();
    }

    private void inicializarUI() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("💰  Registro de Vendas"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Registre vendas e consulte o histórico"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        JPanel cardVenda = criarCard("Nova Venda");
        cardVenda.setPreferredSize(new Dimension(330, 0));
        JPanel campos = new JPanel();
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
        campos.setOpaque(false);
        campos.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));

        campos.add(rotulo("Produto:"));
        campos.add(Box.createVerticalStrut(4));
        cbProduto = new JComboBox<>();
        estilizarCombo(cbProduto);
        cbProduto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        cbProduto.addActionListener(e -> atualizarInfoProduto());
        campos.add(cbProduto);
        campos.add(Box.createVerticalStrut(10));

        JPanel infoCard = new JPanel(new GridLayout(4, 2, 5, 5));
        infoCard.setBackground(COR_BG_CARD);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        infoCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        infoCard.add(rotulo("Preço unitário:"));
        lblPrecoUn = infoVal("R$ 0,00");
        infoCard.add(lblPrecoUn);
        infoCard.add(rotulo("Estoque:"));
        lblEstoqueDisp = infoVal("0");
        infoCard.add(lblEstoqueDisp);
        infoCard.add(rotulo("Unidade:"));
        lblUnidade = infoVal("-");
        infoCard.add(lblUnidade);
        infoCard.add(rotulo("Tamanho:"));
        lblTamanho = infoVal("-");
        infoCard.add(lblTamanho);
        campos.add(infoCard);
        campos.add(Box.createVerticalStrut(10));

        campos.add(rotulo("Quantidade:"));
        campos.add(Box.createVerticalStrut(4));
        txtQuantidade = criarCampoTexto(10);
        txtQuantidade.setText("1");
        txtQuantidade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        txtQuantidade.addCaretListener(e -> calcularTotal());
        campos.add(txtQuantidade);
        campos.add(Box.createVerticalStrut(12));

        JPanel totalCard = new JPanel(new BorderLayout(0, 3));
        totalCard.setBackground(COR_BG_CARD);
        totalCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_ACCENT, 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        totalCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JLabel lblRotTotal = new JLabel("TOTAL DA VENDA");
        lblRotTotal.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblRotTotal.setForeground(COR_TEXTO_SEC);
        lblTotal = new JLabel("R$ 0,00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotal.setForeground(COR_ACCENT2);
        totalCard.add(lblRotTotal, BorderLayout.NORTH);
        totalCard.add(lblTotal, BorderLayout.CENTER);
        campos.add(totalCard);
        campos.add(Box.createVerticalStrut(12));

        JButton btnRegistrar = criarBotaoPrimario("✔  Registrar Venda");
        btnRegistrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnRegistrar.addActionListener(e -> registrarVenda());
        JButton btnAtualizar = criarBotaoSecundario("↺  Atualizar Produtos");
        btnAtualizar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btnAtualizar.addActionListener(e -> carregarProdutos());
        campos.add(btnRegistrar);
        campos.add(Box.createVerticalStrut(6));
        campos.add(btnAtualizar);

        cardVenda.add(campos, BorderLayout.NORTH);

        JPanel cardHistorico = criarCard("Histórico de Vendas");
        String[] cols = {"ID", "Produto", "Qtd.", "Valor Total (R$)", "Data/Hora"};
        modeloHistorico = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabelaHistorico = new JTable(modeloHistorico);
        estilizarTabela(tabelaHistorico);
        int[] larg = {40, 200, 50, 120, 160};
        for (int i = 0; i < larg.length; i++) {
            tabelaHistorico.getColumnModel().getColumn(i).setPreferredWidth(larg[i]);
        }
        cardHistorico.add(criarScrollEscuro(tabelaHistorico), BorderLayout.CENTER);

        JPanel centro = new JPanel(new BorderLayout(14, 0));
        centro.setOpaque(false);
        centro.add(cardVenda, BorderLayout.WEST);
        centro.add(cardHistorico, BorderLayout.CENTER);

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

    private JLabel rotulo(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(COR_TEXTO_SEC);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JLabel infoVal(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(COR_TEXTO);
        return l;
    }

    private void carregarProdutos() {
        cbProduto.removeAllItems();
        for (Produto p : new ProdutoDAO().listarTodos()) {
            if (p.getEstoque() > 0) {
                cbProduto.addItem(new ProdutoItem(p));
            }
        }
        if (cbProduto.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum produto com estoque disponível.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        atualizarInfoProduto();
    }

    private void atualizarInfoProduto() {
        ProdutoItem item = (ProdutoItem) cbProduto.getSelectedItem();
        if (item != null) {
            lblPrecoUn.setText(String.format("R$ %.2f", item.produto.getPreco()));
            lblEstoqueDisp.setText(String.valueOf(item.produto.getEstoque()));
            lblUnidade.setText(item.produto.getUnidade());
            lblTamanho.setText(item.produto.getTamanho());
        } else {
            lblPrecoUn.setText("R$ 0,00");
            lblEstoqueDisp.setText("0");
            lblUnidade.setText("-");
            lblTamanho.setText("-");
        }
        calcularTotal();
    }

    private void calcularTotal() {
        ProdutoItem item = (ProdutoItem) cbProduto.getSelectedItem();
        if (item == null) {
            lblTotal.setText("R$ 0,00");
            return;
        }
        try {
            int qtd = Integer.parseInt(txtQuantidade.getText().trim());
            lblTotal.setText(String.format("R$ %.2f", item.produto.getPreco() * qtd));
        } catch (NumberFormatException e) {
            lblTotal.setText("R$ 0,00");
        }
    }

    private void registrarVenda() {
        ProdutoItem item = (ProdutoItem) cbProduto.getSelectedItem();
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto.");
            return;
        }
        int qtd;
        try {
            qtd = Integer.parseInt(txtQuantidade.getText().trim());
            if (qtd <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Produto prod = item.produto;
        if (qtd > prod.getEstoque()) {
            JOptionPane.showMessageDialog(this, "Estoque insuficiente! Disponível: " + prod.getEstoque() + " " + prod.getUnidade(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double total = prod.getPreco() * qtd;
        if (JOptionPane.showConfirmDialog(this, String.format("Confirmar venda?\n\nProduto: %s\nQuantidade: %d %s\nTotal: R$ %.2f", prod.getNome(), qtd, prod.getUnidade(), total), "Confirmar Venda", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        Venda v = new Venda();
        v.setProdutoId(prod.getId());
        v.setQuantidade(qtd);
        v.setValorTotal(total);
        new VendaDAO().registrarVenda(v);
        JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!");
        txtQuantidade.setText("1");
        carregarProdutos();
        carregarHistorico();
    }

    private void carregarHistorico() {
        modeloHistorico.setRowCount(0);
        for (Venda v : new VendaDAO().listarVendasComNome()) {
            modeloHistorico.addRow(new Object[]{v.getId(), v.getNomeProduto(), v.getQuantidade(), String.format("%.2f", v.getValorTotal()), v.getDataVenda()});
        }
    }
}
