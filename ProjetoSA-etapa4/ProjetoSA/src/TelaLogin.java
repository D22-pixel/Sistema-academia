

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class TelaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public TelaLogin() {
        setTitle("Academia — Login");
        setSize(440, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(TelaBase.COR_BG_MAIN);
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(0x0D0D0D));
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, TelaBase.COR_ACCENT),
                BorderFactory.createEmptyBorder(22, 0, 22, 0)));

        JLabel lblIcone = new JLabel("⬡--⬡");
        lblIcone.setFont(TelaBase.resolverFonteIcone(40));
        lblIcone.setForeground(TelaBase.COR_ACCENT2);
        lblIcone.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("SISTEMA DE ACADEMIA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Faça login para continuar", SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSub.setForeground(TelaBase.COR_TEXTO_SEC);
        lblSub.setAlignmentX(CENTER_ALIGNMENT);

        header.add(lblIcone);
        header.add(Box.createVerticalStrut(6));
        header.add(lblTitulo);
        header.add(Box.createVerticalStrut(4));
        header.add(lblSub);
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(TelaBase.COR_BG_MAIN);
        form.setBorder(BorderFactory.createEmptyBorder(30, 44, 16, 44));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 6, 9, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(TelaBase.criarLabel("Usuário:"), gbc);
        txtUsuario = TelaBase.criarCampoTexto(13);
        gbc.gridx = 1;
        form.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(TelaBase.criarLabel("Senha:"), gbc);
        txtSenha = new JPasswordField(13);
        txtSenha.setBackground(TelaBase.COR_BG_CARD);
        txtSenha.setForeground(TelaBase.COR_TEXTO);
        txtSenha.setCaretColor(TelaBase.COR_TEXTO);
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 13));
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TelaBase.COR_BORDER, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        gbc.gridx = 1;
        form.add(txtSenha, gbc);
        add(form, BorderLayout.CENTER);

        JPanel painelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 18));
        painelBtns.setBackground(TelaBase.COR_BG_MAIN);

        JButton btnEntrar = TelaBase.criarBotaoPrimario("  Entrar  ");
        btnEntrar.setPreferredSize(new Dimension(145, 38));
        btnEntrar.addActionListener(e -> realizarLogin());

        JButton btnSair = TelaBase.criarBotaoSecundario("  Sair  ");
        btnSair.setPreferredSize(new Dimension(145, 38));
        btnSair.addActionListener(e -> System.exit(0));

        painelBtns.add(btnEntrar);
        painelBtns.add(btnSair);
        add(painelBtns, BorderLayout.SOUTH);

        txtSenha.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuarios u = DadosSistema.getInstancia().autenticarUsuario(usuario, senha);
        if (u != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + u.getNomeUsuario() + "!", "Login", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(() -> new MenuPrincipal(u).setVisible(true));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
        }
    }
}
