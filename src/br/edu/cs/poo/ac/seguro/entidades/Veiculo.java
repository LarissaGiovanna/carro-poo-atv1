package br.edu.cs.poo.ac.seguro.entidades;

import lombok.Getter;
import lombok.Setter;

public class Veiculo {
    @Getter @Setter
    private String placa;
    @Getter @Setter
    private int ano;
    @Getter @Setter
    private SeguradoEmpresa proprietarioEmpresa;
    @Getter @Setter
    private SeguradoPessoa proprietarioPessoa;
    @Getter @Setter
    private CategoriaVeiculo categoria;

    public Veiculo(String placa, int ano, SeguradoEmpresa proprietarioEmpresa, SeguradoPessoa proprietarioPessoa, CategoriaVeiculo categoria) {
        this.placa = placa;
        this.ano = ano;
        this.proprietarioEmpresa = proprietarioEmpresa;
        this.proprietarioPessoa = proprietarioPessoa;
        this.categoria = categoria;

    }

}