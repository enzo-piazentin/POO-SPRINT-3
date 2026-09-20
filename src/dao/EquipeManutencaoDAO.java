package dao;

import database.ConexaoBanco;
import exception.BancoDadosException;
import exception.RegistroNaoEncontradoException;
import exception.TabelaNaoEncontradaException;
import model.EquipeManutencao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipeManutencaoDAO {
    private static final String TABELA = "EQUIPE_MANUTENCAO";
    private static final String INSERT = "INSERT INTO EQUIPE_MANUTENCAO (NOME, ESPECIALIDADE) VALUES (?, ?)";
    private static final String SELECT = "SELECT ID, NOME, ESPECIALIDADE FROM EQUIPE_MANUTENCAO WHERE ID = ?";
    private static final String LIST = "SELECT ID, NOME, ESPECIALIDADE FROM EQUIPE_MANUTENCAO ORDER BY ID";
    private static final String UPDATE = "UPDATE EQUIPE_MANUTENCAO SET NOME = ?, ESPECIALIDADE = ? WHERE ID = ?";
    private static final String DELETE = "DELETE FROM EQUIPE_MANUTENCAO WHERE ID = ?";

    public int inserir(EquipeManutencao equipe) {
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(INSERT, new String[]{"ID"})) {
            stmt.setString(1, equipe.nome());
            stmt.setString(2, equipe.especialidade());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw tratarErro("inserir equipe", e);
        }
    }

    public EquipeManutencao buscarPorId(int id) {
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(SELECT)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new RegistroNaoEncontradoException("Equipe não encontrada: " + id);
                }
                return mapear(rs);
            }
        } catch (RegistroNaoEncontradoException e) {
            throw e;
        } catch (SQLException e) {
            throw tratarErro("buscar equipe", e);
        }
    }

    public List<EquipeManutencao> listarTodas() {
        List<EquipeManutencao> equipes = new ArrayList<>();
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(LIST);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                equipes.add(mapear(rs));
            }
            return equipes;
        } catch (SQLException e) {
            throw tratarErro("listar equipes", e);
        }
    }

    public void atualizar(EquipeManutencao equipe) {
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(UPDATE)) {
            stmt.setString(1, equipe.nome());
            stmt.setString(2, equipe.especialidade());
            stmt.setInt(3, equipe.id());
            if (stmt.executeUpdate() == 0) {
                throw new RegistroNaoEncontradoException("Equipe não encontrada: " + equipe.id());
            }
        } catch (RegistroNaoEncontradoException e) {
            throw e;
        } catch (SQLException e) {
            throw tratarErro("atualizar equipe", e);
        }
    }

    public void deletar(int id) {
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            if (stmt.executeUpdate() == 0) {
                throw new RegistroNaoEncontradoException("Equipe não encontrada: " + id);
            }
        } catch (RegistroNaoEncontradoException e) {
            throw e;
        } catch (SQLException e) {
            throw tratarErro("deletar equipe", e);
        }
    }

    private EquipeManutencao mapear(ResultSet rs) throws SQLException {
        return new EquipeManutencao(
                rs.getInt("ID"),
                rs.getString("NOME"),
                rs.getString("ESPECIALIDADE")
        );
    }

    private RuntimeException tratarErro(String operacao, SQLException e) {
        if (e.getErrorCode() == 4043) {
            return new TabelaNaoEncontradaException(TABELA, e);
        }
        return new BancoDadosException(
                "Falha ao " + operacao + " (Oracle " + e.getErrorCode() + ")",
                e
        );
    }
}
