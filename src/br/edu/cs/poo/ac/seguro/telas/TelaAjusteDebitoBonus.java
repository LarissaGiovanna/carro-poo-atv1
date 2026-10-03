package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import br.edu.cs.poo.ac.seguro.mediators.SeguradoMediator;

/**
 * Tela gr\u00E1fica em Java Swing para a funcionalidade especificada de Ajuste de D\u00E9bito de B\u00F4nus.
 * Utiliza como atributo o mediator {@link SeguradoMediator}.
 */
public class TelaAjusteDebitoBonus extends JFrame {

    private static final long serialVersionUID = 1L;

    // Requisito 4: Atributo mediator da tela
    private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();

    // Campos de entrada aderentes (JSpinners numéricos)
    private JSpinner spnBonusAtual;
    private JSpinner spnValorDebito;

    // Campos de exibição do resultado (somente leitura)
    private JTextField txtValorDebitado;
    private JTextField txtNovoSaldoBonus;
    private JLabel lblMensagemStatus;

    // Botões
    private JButton btnCalcular;
    private JButton btnLimpar;

    public TelaAjusteDebitoBonus() {
        super("Ajuste de D\u00E9bito de B\u00F4nus - Sistema de Seguros");
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(580, 440);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        // Cabeçalho com fundo claro e texto de alto contraste
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlHeader.setOpaque(true);
        pnlHeader.setBackground(new Color(241, 245, 249));
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
        ));

        JLabel lblTitulo = new JLabel("Ajuste de D\u00E9bito de B\u00F4nus");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(15, 23, 42)); // Texto escuro legível
        pnlHeader.add(lblTitulo);
        add(pnlHeader, BorderLayout.NORTH);

        // Painel Central
        JPanel pnlCentral = new JPanel(new GridBagLayout());
        pnlCentral.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- ENTRADA DE DADOS ---
        JPanel pnlEntrada = new JPanel(new GridBagLayout());
        pnlEntrada.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Par\u00E2metros para o D\u00E9bito",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), new Color(15, 23, 42)));

        GridBagConstraints gbcIn = new GridBagConstraints();
        gbcIn.insets = new Insets(6, 6, 6, 6);
        gbcIn.fill = GridBagConstraints.HORIZONTAL;

        // Bônus Atual
        gbcIn.gridx = 0; gbcIn.gridy = 0; gbcIn.weightx = 0.4;
        pnlEntrada.add(new JLabel("B\u00F4nus Atual Dispon\u00EDvel (R$):"), gbcIn);

        spnBonusAtual = new JSpinner(new SpinnerNumberModel(100.0, 0.0, 999999999.0, 10.0));
        gbcIn.gridx = 1; gbcIn.gridy = 0; gbcIn.weightx = 0.6;
        pnlEntrada.add(spnBonusAtual, gbcIn);

        // Valor a Debitar Solicitado
        gbcIn.gridx = 0; gbcIn.gridy = 1; gbcIn.weightx = 0.4;
        pnlEntrada.add(new JLabel("Valor Solicitado para D\u00E9bito (R$):"), gbcIn);

        spnValorDebito = new JSpinner(new SpinnerNumberModel(50.0, 0.0, 999999999.0, 10.0));
        gbcIn.gridx = 1; gbcIn.gridy = 1; gbcIn.weightx = 0.6;
        pnlEntrada.add(spnValorDebito, gbcIn);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        pnlCentral.add(pnlEntrada, gbc);

        // --- RESULTADO DO AJUSTE ---
        JPanel pnlResultado = new JPanel(new GridBagLayout());
        pnlResultado.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Resultado do Ajuste (SeguradoMediator)",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), new Color(15, 23, 42)));

        GridBagConstraints gbcOut = new GridBagConstraints();
        gbcOut.insets = new Insets(6, 6, 6, 6);
        gbcOut.fill = GridBagConstraints.HORIZONTAL;

        // Valor Efetivamente Debitado
        gbcOut.gridx = 0; gbcOut.gridy = 0; gbcOut.weightx = 0.4;
        pnlResultado.add(new JLabel("Valor Efetivamente Debitado (R$):"), gbcOut);

        txtValorDebitado = new JTextField(12);
        txtValorDebitado.setEditable(false);
        txtValorDebitado.setForeground(new Color(15, 23, 42));
        txtValorDebitado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbcOut.gridx = 1; gbcOut.gridy = 0; gbcOut.weightx = 0.6;
        pnlResultado.add(txtValorDebitado, gbcOut);

        // Novo Saldo
        gbcOut.gridx = 0; gbcOut.gridy = 1; gbcOut.weightx = 0.4;
        pnlResultado.add(new JLabel("Novo Saldo de B\u00F4nus (R$):"), gbcOut);

        txtNovoSaldoBonus = new JTextField(12);
        txtNovoSaldoBonus.setEditable(false);
        txtNovoSaldoBonus.setForeground(new Color(15, 23, 42));
        txtNovoSaldoBonus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbcOut.gridx = 1; gbcOut.gridy = 1; gbcOut.weightx = 0.6;
        pnlResultado.add(txtNovoSaldoBonus, gbcOut);

        // Label explicativo
        lblMensagemStatus = new JLabel("Informe os valores acima e clique em 'Ajustar D\u00E9bito'.");
        lblMensagemStatus.setForeground(new Color(51, 65, 85));
        gbcOut.gridx = 0; gbcOut.gridy = 2; gbcOut.gridwidth = 2;
        pnlResultado.add(lblMensagemStatus, gbcOut);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1.0;
        pnlCentral.add(pnlResultado, gbc);

        add(pnlCentral, BorderLayout.CENTER);

        // --- PAINEL DE BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        btnLimpar = new JButton("Limpar");
        btnLimpar.setForeground(new Color(15, 23, 42));
        btnLimpar.addActionListener(e -> limparCampos());
        pnlBotoes.add(btnLimpar);

        btnCalcular = new JButton("Ajustar D\u00E9bito");
        btnCalcular.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCalcular.setForeground(new Color(15, 23, 42));
        btnCalcular.addActionListener(e -> acaoCalcularDebito());
        pnlBotoes.add(btnCalcular);

        add(pnlBotoes, BorderLayout.SOUTH);
    }

    private void limparCampos() {
        spnBonusAtual.setValue(0.0);
        spnValorDebito.setValue(0.0);
        txtValorDebitado.setText("");
        txtNovoSaldoBonus.setText("");
        lblMensagemStatus.setText("Campos reiniciados.");
        lblMensagemStatus.setForeground(new Color(51, 65, 85));
    }

    private void acaoCalcularDebito() {
        try {
            double bonusDouble = ((Number) spnBonusAtual.getValue()).doubleValue();
            double debitoDouble = ((Number) spnValorDebito.getValue()).doubleValue();

            BigDecimal bonus = BigDecimal.valueOf(bonusDouble);
            BigDecimal debitoSolicitado = BigDecimal.valueOf(debitoDouble);

            // Requisito 4: Chamada ao mediator
            BigDecimal debitoEfetivo = seguradoMediator.ajustarDebitoBonus(bonus, debitoSolicitado);
            BigDecimal novoSaldo = bonus.subtract(debitoEfetivo);

            txtValorDebitado.setText(String.format("R$ %.2f", debitoEfetivo.doubleValue()));
            txtNovoSaldoBonus.setText(String.format("R$ %.2f", novoSaldo.doubleValue()));

            if (bonus.compareTo(BigDecimal.ZERO) <= 0) {
                lblMensagemStatus.setText("Aten\u00E7\u00E3o: O b\u00F4nus dispon\u00EDvel \u00E9 zero. Nenhum valor foi debitado.");
                lblMensagemStatus.setForeground(new Color(180, 60, 0));
            } else if (debitoSolicitado.compareTo(bonus) > 0) {
                lblMensagemStatus.setText("Aviso: D\u00E9bito solicitado excedia o saldo. Foi debitado apenas o saldo dispon\u00EDvel.");
                lblMensagemStatus.setForeground(new Color(180, 60, 0));
            } else {
                lblMensagemStatus.setText("Sucesso: D\u00E9bito integral aplicado com sucesso.");
                lblMensagemStatus.setForeground(new Color(22, 101, 52));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular ajuste de d\u00E9bito: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            TelaAjusteDebitoBonus tela = new TelaAjusteDebitoBonus();
            tela.setVisible(true);
        });
    }
}
