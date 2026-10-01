package br.edu.cs.poo.ac.seguro.entidades;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Sinistro {
    @Getter @Setter
    private String numero;
    @Getter @Setter
    private Veiculo veiculo;
    @Getter @Setter
    private LocalDateTime dataHoraSinistro;
    @Getter @Setter
    private LocalDateTime dataHoraRegistro;
    @Getter @Setter
    private String usuarioRegistro;
    @Getter @Setter
    private BigDecimal valorSinistro;
    @Getter @Setter
    private TipoSinistro tipo;

    public Sinistro(Veiculo veiculo, LocalDateTime dataHoraSinistro, LocalDateTime dataHoraRegistro, String usuarioRegistro, BigDecimal valorSinistro, TipoSinistro tipo) {
        this.veiculo = veiculo;
        this.dataHoraSinistro = dataHoraSinistro;
        this.dataHoraRegistro = dataHoraRegistro;
        this.usuarioRegistro = usuarioRegistro;
        this.valorSinistro = valorSinistro;
        this.tipo = tipo;
    }
}