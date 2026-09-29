package br.edu.cs.poo.ac.seguro.entidades;

/*
 * Implementar um enum com as seguintes constantes:
 * 
 * 	COLISAO(1,"Colisão"),
	INCENDIO(2,"Incêndio"),
	FURTO(3, "Furto"),
	ENCHENTE(4, "Enchente"),
	DEPREDACAO(5, "Depredação");
 * 
 * O enum deve ter construtor privado, métodos get públicos para os atributos codigo e nome,
 * e um método público e estático TipoSinistro getTipoSinistro(int codigo), que 
 * retorna o tipo de sinistro correspondente ao código recebido como parâmetro
 */
public enum TipoSinistro {

    COLISAO(1, "Colisão"), INCENDIO (2, "Incêndio"), FURTO (3, "Furto"), ENCHENTE(4, "Enchente"), DEPRADACAO(5, "Depradação");
    private int codigo;
    private String nome;
    TipoSinistro(int codigo,  String nome) {}

    public int getCodigo() {
        return codigo;
    }
    public String getNome() {
        return nome;
    }
    public TipoSinistro getTipoSinistro(int codigo) {
        return TipoSinistro.values()[codigo];
    }
}