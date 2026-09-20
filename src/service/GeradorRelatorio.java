package service;

import Model.TrechoRodovia;
import Model.MotorPriorizacao;
import dao.RelatorioPrioridadeDAO;
import java.util.List;

public class GeradorRelatorio {
    public void gerarRelatorio(TrechoRodovia[] trechos){
        List<String> linhas=new MotorPriorizacao().gerarRelatorio(trechos);
        int urgente=0,critico=0,atencao=0,normal=0;
        StringBuilder resumo=new StringBuilder();
        System.out.println("=== RELATÓRIO DE PRIORIDADE ===");
        for(TrechoRodovia t:trechos){double h=t.getAlturaVegetacao();if(h>=100)urgente++;else if(h>=60)critico++;else if(h>=30)atencao++;else normal++;}
        linhas.forEach(l->{System.out.println(l);resumo.append(l).append(System.lineSeparator());});
        new RelatorioPrioridadeDAO().salvarRelatorio(urgente,critico,atencao,normal,resumo.toString());
    }
}
