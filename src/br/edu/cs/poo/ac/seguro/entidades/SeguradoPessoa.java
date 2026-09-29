package br.edu.cs.poo.ac.seguro.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SeguradoPessoa extends Segurado{
    private String cpf;
    private double renda;

    public String getCpf() {
        return cpf;
    }

    public double getRenda() {
        return renda;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRenda(double renda) {
        this.renda = renda;
    }

    public SeguradoPessoa(String cpf, double renda, String nome, Endereco endereco, LocalDate dataCriacao,  BigDecimal bonus) {
        super(nome, endereco, dataCriacao, bonus);
        this.cpf = cpf;
        this.renda = renda;
    }
}
