package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean ehCnpjValido(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return false;
        }

        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("\\D", "");

        // Verifica se todos os dígitos são iguais (inválido)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        int digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);

        return cnpj.endsWith(digito1 + "" + digito2);
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int i = str.length() - 1, idx = 0; i >= 0; i--) {
            soma += Integer.parseInt(str.substring(i, i + 1)) * peso[idx];
            idx++;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    public static boolean ehCpfValido(String cpf) {
        // Remove formatação e verifica tamanho
        cpf = cpf.replaceAll("\\D", "");
        if (cpf == null || cpf.length() != 11) return false;

        // Verifica dígitos repetidos
        if (cpf.matches("(\\d)\\1{10}")) return false;

        // Calcula primeiro dígito verificador
        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += Integer.parseInt(cpf.substring(i, i + 1)) * PESO_CPF[i];
        }
        int digito1 = (soma1 * 10) % 11;
        if (digito1 == 10) digito1 = 0;

        // Calcula segundo dígito verificador
        int soma2 = soma1 + (digito1 * 2);
        int digito2 = (soma2 * 10) % 11;
        if (digito2 == 10) digito2 = 0;

        // Compara com os dígitos informados
        return cpf.substring(9, 11).equals(digito1 + "" + digito2);
    }
}
