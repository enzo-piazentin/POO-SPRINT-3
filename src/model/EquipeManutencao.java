package model;

public record EquipeManutencao(int id, String nome, String especialidade) {
    public EquipeManutencao {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome da equipe é obrigatório");
        if (especialidade == null || especialidade.isBlank()) throw new IllegalArgumentException("especialidade é obrigatória");
    }
    public EquipeManutencao(String nome, String especialidade) { this(0, nome, especialidade); }
}
