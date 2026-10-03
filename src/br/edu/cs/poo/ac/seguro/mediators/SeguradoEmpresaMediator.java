package br.edu.cs.poo.ac.seguro.mediators;

import java.time.LocalDate;
import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {

    // Singleton
    private static SeguradoEmpresaMediator instancia = new SeguradoEmpresaMediator();

    private SeguradoEmpresaMediator() {}

    public static SeguradoEmpresaMediator getInstancia() {
        return instancia;
    }

    // Atributos inicializados na própria definição (conforme especificação)
    SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    private SeguradoEmpresaDAO seguradoEmpresaDAO = new SeguradoEmpresaDAO();

    /**
     * Valida o CNPJ:
     * - Não pode ser nulo nem em branco -> "CNPJ deve ser informado"
     * - Tamanho deve ser 14 caracteres -> "CNPJ deve ter 14 caracteres"
     * - Dígitos verificadores válidos -> "CNPJ com dígito inválido"
     */
    public String validarCnpj(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ deve ser informado";
        }
        if (cnpj.length() != 14) {
            return "CNPJ deve ter 14 caracteres";
        }
        if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ com d\u00EDgito inv\u00E1lido";
        }
        return null;
    }

    /**
     * Valida o faturamento: deve ser maior que zero.
     */
    public String validarFaturamento(double faturamento) {
        if (faturamento <= 0) {
            return "Faturamento deve ser maior que zero";
        }
        return null;
    }

    /**
     * Valida todos os dados de um SeguradoEmpresa.
     */
    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        if (seg.getDataAbertura() == null) {
            return "Data da abertura deve ser informada";
        }
        if (seg.getDataAbertura().isAfter(LocalDate.now())) {
            return "Data da abertura deve ser menor ou igual \u00E0 data atual";
        }

        msg = validarCnpj(seg.getCnpj());
        if (msg != null) return msg;

        msg = validarFaturamento(seg.getFaturamento());
        if (msg != null) return msg;

        return null;
    }

    /**
     * Inclui um SeguradoEmpresa após validar os dados.
     */
    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        boolean incluido = seguradoEmpresaDAO.incluir(seg);
        if (!incluido) {
            return "CNPJ do segurado empresa j\u00E1 existente";
        }
        return null;
    }

    /**
     * Altera um SeguradoEmpresa após validar os dados.
     */
    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        boolean alterado = seguradoEmpresaDAO.alterar(seg);
        if (!alterado) {
            return "CNPJ do segurado empresa n\u00E3o existente";
        }
        return null;
    }

    /**
     * Exclui um SeguradoEmpresa pelo CNPJ.
     */
    public String excluirSeguradoEmpresa(String cnpj) {
        boolean excluido = seguradoEmpresaDAO.excluir(cnpj);
        if (!excluido) {
            return "CNPJ do segurado empresa n\u00E3o existente";
        }
        return null;
    }

    /**
     * Busca um SeguradoEmpresa pelo CNPJ.
     */
    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        return seguradoEmpresaDAO.buscar(cnpj);
    }
}
