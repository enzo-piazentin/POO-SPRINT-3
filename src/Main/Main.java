package Main;

import Model.TrechoRodovia;
import dao.EquipeManutencaoDAO;
import dao.IntervencaoOperacionalDAO;
import dao.RelatorioPrioridadeDAO;
import dao.TrechoRodoviaDAO;
import database.ConexaoBanco;
import exception.CredenciaisInvalidasException;
import model.EquipeManutencao;
import model.IntervencaoRegistro;
import service.GeradorRelatorio;

/** Demonstração de conexão, inserção, CRUD e persistência do relatório. */
public class Main {
    public static void main(String[] args) {
        java.sql.Connection conexao = null;

        try {
            // A classe ConexaoBanco foi mantida no formato original.
            conexao = ConexaoBanco.getConexao();

            EquipeManutencaoDAO equipes = new EquipeManutencaoDAO();
            int equipeId = equipes.inserir(
                    new EquipeManutencao("Equipe Norte", "Roçada")
            );
            System.out.println("Equipe inserida: " + equipes.buscarPorId(equipeId));

            equipes.atualizar(new EquipeManutencao(
                    equipeId, "Equipe Norte", "Roçada e poda"
            ));
            System.out.println("Equipe atualizada: " + equipes.buscarPorId(equipeId));

            TrechoRodoviaDAO trechos = new TrechoRodoviaDAO();
            TrechoRodovia trecho = new TrechoRodovia(
                    10, 120, "umido", equipeId
            );
            trechos.inserir(trecho);
            System.out.println("Trechos cadastrados: " + trechos.listarTodas());

            IntervencaoOperacionalDAO intervencoes = new IntervencaoOperacionalDAO();
            int intervencaoId = intervencoes.inserir(new IntervencaoRegistro(
                    10, "ROCADA_MECANIZADA", "Equipe Norte"
            ));
            System.out.println("Intervenção inserida: "
                    + intervencoes.buscarPorId(intervencaoId));

            TrechoRodovia[] trechosParaRelatorio = trechos.listarTodas()
                    .toArray(new TrechoRodovia[0]);
            new GeradorRelatorio().gerarRelatorio(trechosParaRelatorio);

            System.out.println("Histórico de relatórios:");
            new RelatorioPrioridadeDAO().listarTodas()
                    .forEach(System.out::println);

            // Para testar as exclusões, execute-as nesta ordem devido às FKs:
            // intervencoes.deletar(intervencaoId);
            // trechos.deletar(trecho.getKm());
            // equipes.deletar(equipeId);

        } catch (CredenciaisInvalidasException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getDicaCorrecao());
        } catch (RuntimeException e) {
            System.err.println("Erro durante a demonstração: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConexaoBanco.fechar(conexao);
        }
    }
}
