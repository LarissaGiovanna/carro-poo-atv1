package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;

public class SeguradoMediator {

    private static SeguradoMediator instancia = new SeguradoMediator();

    private SeguradoMediator() {}

    public static SeguradoMediator getInstancia() {
        return instancia;
    }

    /**
     * Valida o nome do segurado: não pode ser nulo nem em branco.
     * Retorna mensagem de erro, ou null se válido.
     */
    public String validarNome(String nome) {
        if (StringUtils.ehNuloOuBranco(nome)) {
            return "Nome inválido";
        }
        return null;
    }

    /**
     * Valida o endereço do segurado: não pode ser nulo; logradouro e CEP
     * são obrigatórios.
     * Retorna mensagem de erro, ou null se válido.
     */
    public String validarEndereco(Endereco endereco) {
        if (endereco == null) {
            return "Endereço inválido";
        }
        if (StringUtils.ehNuloOuBranco(endereco.getLogradouro())) {
            return "Logradouro inválido";
        }
        if (StringUtils.ehNuloOuBranco(endereco.getCep())) {
            return "CEP inválido";
        }
        return null;
    }

    /**
     * Valida a data de criação/nascimento/abertura do segurado:
     * não pode ser nula nem estar no futuro.
     * Retorna mensagem de erro, ou null se válida.
     */
    public String validarDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null) {
            return "Data inválida";
        }
        if (dataCriacao.isAfter(LocalDate.now())) {
            return "Data inválida";
        }
        return null;
    }

    /**
     * Ajusta o valor a ser debitado do bônus, garantindo que o bônus
     * não fique negativo.
     * - Se bonus >= valorDebito → debita valorDebito integralmente.
     * - Se bonus < valorDebito  → debita apenas o saldo disponível (bonus).
     *
     * @return o valor efetivamente debitável do bônus
     */
    public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
        if (bonus == null || valorDebito == null) {
            return BigDecimal.ZERO;
        }
        if (bonus.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        if (bonus.compareTo(valorDebito) >= 0) {
            return valorDebito;
        }
        return bonus;
    }
}
