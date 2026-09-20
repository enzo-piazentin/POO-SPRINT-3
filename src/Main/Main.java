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

import java.sql.Connection;

/** Classe principal para demonstrar as operações da Sprint 3. */
public class Main {
    public static void main(String[] args) {
        Connection conexao = null;

        try {
            // Testa a conexão com o Oracle.
            conexao = ConexaoBanco.getConexao();

            // CRUD de equipe.
            EquipeManutencaoDAO equipeDAO = new EquipeManutencaoDAO();
            int equipeId = equipeDAO.inserir(
                    new EquipeManutencao("Equipe Norte", "Roçada")
            );
            System.out.println("Equipe inserida: " + equipeDAO.buscarPorId(equipeId));

            equipeDAO.atualizar(new EquipeManutencao(
                    equipeId,
                    "Equipe Norte",
                    "Roçada e poda"
            ));
            System.out.println("Equipe atualizada: " + equipeDAO.buscarPorId(equipeId));

            // CRUD de trecho.
            TrechoRodoviaDAO trechoDAO = new TrechoRodoviaDAO();
            int kmTrecho = 101;
            TrechoRodovia trecho = new TrechoRodovia(
                    kmTrecho,
                    120,
                    "umido",
                    equipeId
            );
            trechoDAO.inserir(trecho);
            System.out.println("Trechos cadastrados: " + trechoDAO.listarTodas());

            // CRUD de intervenção.
            IntervencaoOperacionalDAO intervencaoDAO = new IntervencaoOperacionalDAO();
            int intervencaoId = intervencaoDAO.inserir(
                    new IntervencaoRegistro(
                            kmTrecho,
                            "ROCADA_MECANIZADA",
                            "Equipe Norte"
                    )
            );
            System.out.println("Intervenção inserida: "
                    + intervencaoDAO.buscarPorId(intervencaoId));

            // Gera o relatório no console e salva o histórico no banco.
            TrechoRodovia[] trechos = trechoDAO.listarTodas()
                    .toArray(new TrechoRodovia[0]);
            new GeradorRelatorio().gerarRelatorio(trechos);

            // Consulta o histórico de relatórios persistidos.
            System.out.println("Histórico de relatórios:");
            new RelatorioPrioridadeDAO().listarTodas()
                    .forEach(System.out::println);

            // Para testar o DELETE, utilize a ordem abaixo por causa das FKs:
            // intervencaoDAO.deletar(intervencaoId);
            // trechoDAO.deletar(kmTrecho);
            // equipeDAO.deletar(equipeId);

        } catch (CredenciaisInvalidasException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getDicaCorrecao());
        } catch (RuntimeException e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConexaoBanco.fechar(conexao);
        }
    }
}
