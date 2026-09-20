package Main;

import Model.TrechoRodovia;
import dao.*;
import database.ConexaoBanco;
import model.*;
import service.GeradorRelatorio;

/** Demonstração completa do CRUD e da persistência do relatório. */
public class Main {
    public static void main(String[] args) {
        ConexaoBanco.getInstancia().conectar();
        EquipeManutencaoDAO equipes=new EquipeManutencaoDAO();
        int equipeId = equipes.inserir(new model.EquipeManutencao("Equipe Norte","Roçada"));
        System.out.println(equipes.buscarPorId(equipeId));
        equipes.atualizar(new model.EquipeManutencao(equipeId,"Equipe Norte","Roçada e poda"));

        TrechoRodoviaDAO trechos = new TrechoRodoviaDAO();
        TrechoRodovia trecho = new TrechoRodovia(10,120,"umido",equipeId);
        trechos.inserir(trecho);
        System.out.println(trechos.listarTodas());

        IntervencaoOperacionalDAO intervencoes=new IntervencaoOperacionalDAO();
        int intervencaoId=intervencoes.inserir(new model.IntervencaoRegistro(10,"ROÇADA_MECANIZADA","Equipe Norte"));
        System.out.println(intervencoes.buscarPorId(intervencaoId));
        new GeradorRelatorio().gerarRelatorio(trechos.listarTodas().toArray(TrechoRodovia[]::new));
        new RelatorioPrioridadeDAO().listarTodas().forEach(System.out::println);
        // Descomente após validar os registros: equipes.deletar(equipeId); trechos.deletar(10); intervencoes.deletar(intervencaoId);
        ConexaoBanco.getInstancia().desconectar();
    }
}
