package model;

public record EquipeManutencao(int id, String nome, String especialidade, String direcao) {
    public EquipeManutencao {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome da equipe é obrigatório");
        if (especialidade == null || especialidade.isBlank()) throw new IllegalArgumentException("especialidade é obrigatória");
        direcao = normalizarDirecao(direcao);
    }

    public EquipeManutencao(int id, String nome, String especialidade) {
        this(id, nome, especialidade, "NAO_INFORMADA");
    }

    public EquipeManutencao(String nome, String especialidade, String direcao) {
        this(0, nome, especialidade, direcao);
    }

    private static String normalizarDirecao(String valor) {
        if (valor == null) throw new IllegalArgumentException("direção é obrigatória");
        String direcao = valor.trim().toUpperCase();
        if (!direcao.equals("NORTE") && !direcao.equals("SUL")
                && !direcao.equals("LESTE") && !direcao.equals("OESTE")) {
            throw new IllegalArgumentException("direção inválida. Use NORTE, SUL, LESTE ou OESTE");
        }
        return direcao;
    }
}
