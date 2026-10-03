package br.edu.cs.poo.ac.seguro.mediators;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean ehNuloOuBranco(String str) {
        if (str == null) {
            return true;
        }
        return false;
    }

    public static boolean temSomenteNumeros(String input) {
        //percorrer string para ver se a string possui somente numeros
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}

