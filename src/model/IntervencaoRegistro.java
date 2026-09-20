package model;

import java.time.LocalDate;

public record IntervencaoRegistro(int id, int kmTrecho, String tipo, String responsavel, LocalDate dataIntervencao) {
    public IntervencaoRegistro {
        if (kmTrecho < 0) throw new IllegalArgumentException("km do trecho não pode ser negativo");
        if (tipo == null || tipo.isBlank()) throw new IllegalArgumentException("tipo é obrigatório");
        if (responsavel == null || responsavel.isBlank()) throw new IllegalArgumentException("responsável é obrigatório");
        if (dataIntervencao == null) dataIntervencao = LocalDate.now();
    }
    public IntervencaoRegistro(int kmTrecho, String tipo, String responsavel) {
        this(0, kmTrecho, tipo, responsavel, LocalDate.now());
    }
}
