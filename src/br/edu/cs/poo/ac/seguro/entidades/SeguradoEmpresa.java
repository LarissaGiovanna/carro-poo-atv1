package br.edu.cs.poo.ac.seguro.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SeguradoEmpresa extends Segurado{
    private String cnpj;
    private double faturamento;
    private boolean ehLocadoraDeVeiculos;

    public String getCnpj() {
        return cnpj;
    }

    public double getFaturamento() {
        return faturamento;
    }

    public boolean isEhLocadoraDeVeiculos() {
        return ehLocadoraDeVeiculos;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setFaturamento(double faturamento) {
        this.faturamento = faturamento;
    }

    public void setEhLocadoraDeVeiculos(boolean ehLocadoraDeVeiculos) {
        this.ehLocadoraDeVeiculos = ehLocadoraDeVeiculos;
    }

    public SeguradoEmpresa(String cnpj, double faturamento, boolean ehLocadoraDeVeiculos, String nome, Endereco endereco, LocalDate dataAbertura, BigDecimal bonus) {
        super(nome, endereco, dataAbertura, bonus);
        this.cnpj = cnpj;
        this.faturamento = faturamento;
        this.ehLocadoraDeVeiculos=ehLocadoraDeVeiculos;
    }

    public LocalDate getDataAbertura() {
        return getDataCriacao();
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        setDataCriacao(dataAbertura);
    }
}
