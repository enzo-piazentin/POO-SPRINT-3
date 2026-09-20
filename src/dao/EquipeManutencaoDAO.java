package dao;

import database.ConexaoBanco;
import exception.BancoDadosException;
import exception.RegistroNaoEncontradoException;
import exception.TabelaNaoEncontradaException;
import model.EquipeManutencao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeManutencaoDAO {
    private static final String INSERT = "INSERT INTO EQUIPE_MANUTENCAO (NOME, ESPECIALIDADE, DIRECAO) VALUES (?, ?, ?)";
    private static final String SELECT = "SELECT ID, NOME, ESPECIALIDADE, DIRECAO FROM EQUIPE_MANUTENCAO WHERE ID = ?";
    private static final String LIST = "SELECT ID, NOME, ESPECIALIDADE, DIRECAO FROM EQUIPE_MANUTENCAO ORDER BY ID";
    private static final String UPDATE = "UPDATE EQUIPE_MANUTENCAO SET NOME = ?, ESPECIALIDADE = ?, DIRECAO = ? WHERE ID = ?";
    private static final String DELETE = "DELETE FROM EQUIPE_MANUTENCAO WHERE ID = ?";

    public int inserir(EquipeManutencao equipe) {
        try (Connection c = ConexaoBanco.getConexao(); PreparedStatement s = c.prepareStatement(INSERT, new String[]{"ID"})) {
            s.setString(1, equipe.nome()); s.setString(2, equipe.especialidade()); s.setString(3, equipe.direcao()); s.executeUpdate();
            try (ResultSet r = s.getGeneratedKeys()) { return r.next() ? r.getInt(1) : 0; }
        } catch (SQLException e) { throw erro("inserir equipe", e); }
    }

    public EquipeManutencao buscarPorId(int id) {
        try (Connection c = ConexaoBanco.getConexao(); PreparedStatement s = c.prepareStatement(SELECT)) {
            s.setInt(1, id); try (ResultSet r = s.executeQuery()) {
                if (!r.next()) throw new RegistroNaoEncontradoException("Equipe não encontrada: " + id); return map(r);
            }
        } catch (RegistroNaoEncontradoException e) { throw e; } catch (SQLException e) { throw erro("buscar equipe", e); }
    }

    public List<EquipeManutencao> listarTodas() {
        List<EquipeManutencao> equipes = new ArrayList<>();
        try (Connection c = ConexaoBanco.getConexao(); PreparedStatement s = c.prepareStatement(LIST); ResultSet r = s.executeQuery()) {
            while (r.next()) equipes.add(map(r)); return equipes;
        } catch (SQLException e) { throw erro("listar equipes", e); }
    }

    public void atualizar(EquipeManutencao equipe) {
        try (Connection c = ConexaoBanco.getConexao(); PreparedStatement s = c.prepareStatement(UPDATE)) {
            s.setString(1, equipe.nome()); s.setString(2, equipe.especialidade()); s.setString(3, equipe.direcao()); s.setInt(4, equipe.id());
            if (s.executeUpdate() == 0) throw new RegistroNaoEncontradoException("Equipe não encontrada: " + equipe.id());
        } catch (RegistroNaoEncontradoException e) { throw e; } catch (SQLException e) { throw erro("atualizar equipe", e); }
    }

    public void deletar(int id) {
        try (Connection c = ConexaoBanco.getConexao(); PreparedStatement s = c.prepareStatement(DELETE)) {
            s.setInt(1, id); if (s.executeUpdate() == 0) throw new RegistroNaoEncontradoException("Equipe não encontrada: " + id);
        } catch (RegistroNaoEncontradoException e) { throw e; } catch (SQLException e) { throw erro("deletar equipe", e); }
    }

    private EquipeManutencao map(ResultSet r) throws SQLException {
        return new EquipeManutencao(r.getInt("ID"), r.getString("NOME"), r.getString("ESPECIALIDADE"), r.getString("DIRECAO"));
    }

    private RuntimeException erro(String operacao, SQLException e) {
        if (e.getErrorCode() == 942 || e.getErrorCode() == 4043) return new TabelaNaoEncontradaException("EQUIPE_MANUTENCAO", e);
        return new BancoDadosException("Falha ao " + operacao + " (Oracle " + e.getErrorCode() + ")", e);
    }
}
