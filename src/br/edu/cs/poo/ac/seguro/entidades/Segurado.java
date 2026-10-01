package br.edu.cs.poo.ac.seguro.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Segurado {
    private String nome;
    private Endereco endereco;
    private LocalDate dataCriacao;
    private BigDecimal bonus;

    public String getNome() {
        return nome;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    protected LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    protected void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    private void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public Segurado(String nome, Endereco endereco,  LocalDate dataCriacao, BigDecimal bonus) {
        this.nome = nome;
        this.endereco = endereco;
        this.dataCriacao = dataCriacao;
        this.bonus = bonus;
    }

    public int getIdade(){
        int anoSegurado =  this.dataCriacao.getYear();
        int anoAtual = LocalDate.now().getYear();
        return anoAtual - anoSegurado;
    }

    public void creditarBonus(BigDecimal bonus){
        this.bonus = this.bonus.add(bonus);
    }

    public void debitarBonus(BigDecimal bonus){
        this.bonus = this.bonus.subtract(bonus);
    }
}
