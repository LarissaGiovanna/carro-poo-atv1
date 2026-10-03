package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Tela principal do Sistema de Seguros.
 * Menu centralizador com acesso a todas as telas do sistema.
 */
public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    public TelaPrincipal() {
        super("Sistema de Gest\u00E3o de Seguros - Menu Principal");
        configurarJanela();
        criarMenuSuperior();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 540);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void criarMenuSuperior() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Cadastros
        JMenu menuCadastros = new JMenu("Cadastros");
        menuCadastros.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JMenuItem miSeguradoPessoa = new JMenuItem("Segurado Pessoa F\u00EDsica");
        miSeguradoPessoa.addActionListener(e -> abrirTelaSeguradoPessoa());
        menuCadastros.add(miSeguradoPessoa);

        JMenuItem miSeguradoEmpresa = new JMenuItem("Segurado Empresa (PJ)");
        miSeguradoEmpresa.addActionListener(e -> abrirTelaSeguradoEmpresa());
        menuCadastros.add(miSeguradoEmpresa);

        menuCadastros.addSeparator();

        JMenuItem miSair = new JMenuItem("Sair");
        miSair.addActionListener(e -> System.exit(0));
        menuCadastros.add(miSair);

        // Menu Operações
        JMenu menuOperacoes = new JMenu("Opera\u00E7\u00F5es");
        menuOperacoes.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JMenuItem miDebitoBonus = new JMenuItem("Ajuste de D\u00E9bito de B\u00F4nus");
        miDebitoBonus.addActionListener(e -> abrirTelaAjusteBonus());
        menuOperacoes.add(miDebitoBonus);

        menuBar.add(menuCadastros);
        menuBar.add(menuOperacoes);

        setJMenuBar(menuBar);
    }

    private void inicializarComponentes() {
        // Banner Superior com fundo contrastante e texto nítido
        JPanel pnlBanner = new JPanel();
        pnlBanner.setOpaque(true);
        pnlBanner.setLayout(new BoxLayout(pnlBanner, BoxLayout.Y_AXIS));
        pnlBanner.setBackground(new Color(241, 245, 249)); // Cinza claro elegante
        pnlBanner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTitulo = new JLabel("Sistema de Gest\u00E3o de Seguros");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(15, 23, 42)); // Texto escuro de alto contraste
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("M\u00F3dulo de Cadastros e Mediators (POO)");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(71, 85, 105)); // Cinza ardósia escuro
        lblSubtitulo.setAlignmentX(CENTER_ALIGNMENT);

        pnlBanner.add(lblTitulo);
        pnlBanner.add(Box.createRigidArea(new Dimension(0, 6)));
        pnlBanner.add(lblSubtitulo);
        add(pnlBanner, BorderLayout.NORTH);

        // Painel Central com Botões / Cards
        JPanel pnlBotoes = new JPanel(new GridLayout(3, 1, 14, 14));
        pnlBotoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnPessoa = criarBotaoCard(
                "Segurado Pessoa F\u00EDsica",
                "CRUD completo (Incluir, Buscar, Alterar, Excluir) via SeguradoPessoaMediator",
                new Color(30, 64, 175) // Azul
        );
        btnPessoa.addActionListener(e -> abrirTelaSeguradoPessoa());

        JButton btnEmpresa = criarBotaoCard(
                "Segurado Empresa (Pessoa Jur\u00EDdica)",
                "CRUD completo (Incluir, Buscar, Alterar, Excluir) via SeguradoEmpresaMediator",
                new Color(22, 101, 52) // Verde
        );
        btnEmpresa.addActionListener(e -> abrirTelaSeguradoEmpresa());

        JButton btnBonus = criarBotaoCard(
                "Ajuste de D\u00E9bito de B\u00F4nus",
                "C\u00E1lculo e ajuste de d\u00E9bito de b\u00F4nus via SeguradoMediator",
                new Color(126, 34, 206) // Roxo
        );
        btnBonus.addActionListener(e -> abrirTelaAjusteBonus());

        pnlBotoes.add(btnPessoa);
        pnlBotoes.add(btnEmpresa);
        pnlBotoes.add(btnBonus);

        add(pnlBotoes, BorderLayout.CENTER);

        // Rodapé
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JLabel lblInfo = new JLabel("Desenvolvido em Java Swing nativo \u2022 POO");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(100, 116, 139));
        pnlFooter.add(lblInfo);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    /**
     * Cria botão com borda colorida e texto escuro de alta legibilidade.
     */
    private JButton criarBotaoCard(String titulo, String subtitulo, Color corDestaque) {
        String htmlTexto = "<html><div style='text-align:left; padding-left:8px;'>"
                + "<span style='font-size:14px; font-weight:bold; color:#0F172A;'>" + titulo + "</span><br>"
                + "<span style='font-size:11px; color:#475569;'>" + subtitulo + "</span>"
                + "</div></html>";

        JButton btn = new JButton(htmlTexto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(JButton.LEFT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, corDestaque),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                        BorderFactory.createEmptyBorder(12, 14, 12, 14)
                )
        ));
        return btn;
    }

    private void abrirTelaSeguradoPessoa() {
        TelaSeguradoPessoa tela = new TelaSeguradoPessoa();
        tela.setVisible(true);
    }

    private void abrirTelaSeguradoEmpresa() {
        TelaSeguradoEmpresa tela = new TelaSeguradoEmpresa();
        tela.setVisible(true);
    }

    private void abrirTelaAjusteBonus() {
        TelaAjusteDebitoBonus tela = new TelaAjusteDebitoBonus();
        tela.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
