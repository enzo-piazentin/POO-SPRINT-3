package model;

import java.time.LocalDateTime;

public record RelatorioPrioridade(int id, int urgentes, int criticos, int atencao, int normais,
                                  String resumo, LocalDateTime geradoEm) {
    public RelatorioPrioridade {
        if (urgentes < 0 || criticos < 0 || atencao < 0 || normais < 0)
            throw new IllegalArgumentException("quantidades do relatório não podem ser negativas");
        if (resumo == null) resumo = "";
    }
    public RelatorioPrioridade(int urgentes, int criticos, int atencao, int normais, String resumo) {
        this(0, urgentes, criticos, atencao, normais, resumo, null);
    }
}
