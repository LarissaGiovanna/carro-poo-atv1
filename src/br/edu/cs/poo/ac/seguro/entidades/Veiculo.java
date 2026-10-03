package br.edu.cs.poo.ac.seguro.entidades;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;
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
}