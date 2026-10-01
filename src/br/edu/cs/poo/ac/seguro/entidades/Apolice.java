package br.edu.cs.poo.ac.seguro.entidades;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

public class Apolice {
    @Getter @Setter
    private Veiculo veiculo;
    @Getter @Setter
    private BigDecimal valorFranquia;
    @Getter @Setter
    private BigDecimal valorPremio;
    @Getter @Setter
    private BigDecimal valorMaximoSegurado;

    public Apolice(Veiculo veiculo,  BigDecimal valorFranquia, BigDecimal valorPremio) {
        this.veiculo = veiculo;
        this.valorFranquia = valorFranquia;
        this.valorPremio = valorPremio;
    }

}