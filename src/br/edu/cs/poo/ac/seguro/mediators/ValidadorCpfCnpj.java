package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

    // Pesos aplicados da esquerda para a direita (padrão Receita Federal)
    private static final int[] PESO_CNPJ_1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESO_CNPJ_2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static final int[] PESO_CPF_1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESO_CPF_2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     * Calcula um dígito verificador somando str[i] * peso[i] (esquerda → direita),
     * então retorna 0 se resto < 2, senão 11 - resto.
     */
    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += Integer.parseInt(str.substring(i, i + 1)) * peso[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    public static boolean ehCnpjValido(String cnpj) {
        if (cnpj == null) return false;

        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14) return false;

        // Rejeita sequências com todos os dígitos iguais (ex: "00000000000000")
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int digito1 = calcularDigito(cnpj.substring(0, 12), PESO_CNPJ_1);
        int digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, PESO_CNPJ_2);

        return cnpj.endsWith(digito1 + "" + digito2);
    }

    public static boolean ehCpfValido(String cpf) {
        // Null check ANTES de qualquer operação na string
        if (cpf == null) return false;

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) return false;

        // Rejeita sequências com todos os dígitos iguais (ex: "00000000000")
        if (cpf.matches("(\\d)\\1{10}")) return false;

        // Primeiro dígito verificador
        int digito1 = calcularDigito(cpf.substring(0, 9), PESO_CPF_1);

        // Segundo dígito verificador (usa os 9 dígitos originais + digito1)
        int digito2 = calcularDigito(cpf.substring(0, 9) + digito1, PESO_CPF_2);

        return cpf.charAt(9) - '0' == digito1 && cpf.charAt(10) - '0' == digito2;
    }
}
