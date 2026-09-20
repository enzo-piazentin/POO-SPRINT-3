package Main;

import Model.MotorPriorizacao;
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

import java.sql.Connection;

/** Demonstração de conexão, CRUD e persistência do relatório. */
public class Main {
    public static void main(String[] args) {
        try (Connection conexao = ConexaoBanco.getConexao()) {
            EquipeManutencaoDAO equipes = new EquipeManutencaoDAO();
            int equipeId = equipes.inserir(new EquipeManutencao("Equipe Norte", "Roçada"));
            System.out.println("Equipe inserida: " + equipes.buscarPorId(equipeId));

            equipes.atualizar(new EquipeManutencao(equipeId, "Equipe Norte", "Roçada e poda"));
            System.out.println("Equipe atualizada: " + equipes.buscarPorId(equipeId));

            TrechoRodoviaDAO trechos = new TrechoRodoviaDAO();
            int kmTrecho = 101;
            TrechoRodovia trecho = new TrechoRodovia(kmTrecho, 120, "umido", equipeId);
            trechos.inserir(trecho);
            System.out.println("Trechos cadastrados: " + trechos.listarTodas());

            IntervencaoOperacionalDAO intervencoes = new IntervencaoOperacionalDAO();
            int intervencaoId = intervencoes.inserir(
                    new IntervencaoRegistro(kmTrecho, "ROCADA_MECANIZADA", "Equipe Norte")
            );
            System.out.println("Intervenção inserida: " + intervencoes.buscarPorId(intervencaoId));

            TrechoRodovia[] trechosParaRelatorio = trechos.listarTodas().toArray(new TrechoRodovia[0]);
            new GeradorRelatorio().gerarRelatorio(trechosParaRelatorio);

            System.out.println("Histórico de relatórios:");
            new RelatorioPrioridadeDAO().listarTodas().forEach(System.out::println);

            // Excluir em ordem reversa para respeitar FKs:
            // intervencoes.deletar(intervencaoId);
            // trechos.deletar(kmTrecho);
            // equipes.deletar(equipeId);

        } catch (CredenciaisInvalidasException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getDicaCorrecao());
        } catch (RuntimeException e) {
            System.err.println("Erro durante a demonstração: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
