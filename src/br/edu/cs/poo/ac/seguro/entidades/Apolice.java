package br.edu.cs.poo.ac.seguro.entidades;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;

public class Apolice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private Veiculo veiculo;
    @Getter @Setter
    private BigDecimal valorFranquia;
    @Getter @Setter
    private BigDecimal valorPremio;
    @Getter @Setter
    private BigDecimal valorMaximoSegurado;
    @Getter @Setter
    private String numero;

    public Apolice(Veiculo veiculo,  BigDecimal valorFranquia, BigDecimal valorPremio,  BigDecimal valorMaximoSegurado) {
        this.veiculo = veiculo;
        this.valorFranquia = valorFranquia;
        this.valorPremio = valorPremio;
        this.valorMaximoSegurado = valorMaximoSegurado;
    }

}