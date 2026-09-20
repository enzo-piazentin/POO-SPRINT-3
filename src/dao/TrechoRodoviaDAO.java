package dao;

import Model.TrechoRodovia;
import database.ConexaoBanco;
import exception.BancoDadosException;
import exception.RegistroNaoEncontradoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TrechoRodoviaDAO {
    private static final String INSERT =
            "INSERT INTO TRECHO_RODOVIA "
                    + "(KM, ALTURA_VEGETACAO, TIPO_TERRENO, EQUIPE_ID) "
                    + "VALUES (?, ?, ?, ?)";
    private static final String SELECT =
            "SELECT KM, ALTURA_VEGETACAO, TIPO_TERRENO, EQUIPE_ID "
                    + "FROM TRECHO_RODOVIA WHERE KM = ?";
    private static final String LIST =
            "SELECT KM, ALTURA_VEGETACAO, TIPO_TERRENO, EQUIPE_ID "
                    + "FROM TRECHO_RODOVIA ORDER BY KM";
    private static final String UPDATE =
            "UPDATE TRECHO_RODOVIA SET ALTURA_VEGETACAO = ?, "
                    + "TIPO_TERRENO = ?, EQUIPE_ID = ? WHERE KM = ?";
    private static final String DELETE =
            "DELETE FROM TRECHO_RODOVIA WHERE KM = ?";

    public void inserir(TrechoRodovia trecho) {
        executarPersistencia(INSERT, trecho, false);
    }

    public TrechoRodovia buscarPorId(int km) {
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(SELECT)) {
            stmt.setInt(1, km);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new RegistroNaoEncontradoException(
                            "Trecho não encontrado: " + km);
                }
                return mapear(rs);
            }
        } catch (RegistroNaoEncontradoException e) {
            throw e;
        } catch (SQLException e) {
            throw erro("buscar trecho", e);
        }
    }

    public List<TrechoRodovia> listarTodas() {
        List<TrechoRodovia> trechos = new ArrayList<>();
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(LIST);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                trechos.add(mapear(rs));
            }
            return trechos;
        } catch (SQLException e) {
            throw erro("listar trechos", e);
        }
    }

    public void atualizar(TrechoRodovia trecho) {
        executarPersistencia(UPDATE, trecho, true);
    }

    public void deletar(int km) {
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(DELETE)) {
            stmt.setInt(1, km);
            if (stmt.executeUpdate() == 0) {
                throw new RegistroNaoEncontradoException(
                        "Trecho não encontrado: " + km);
            }
        } catch (RegistroNaoEncontradoException e) {
            throw e;
        } catch (SQLException e) {
            throw erro("deletar trecho", e);
        }
    }

    private void executarPersistencia(String sql, TrechoRodovia trecho,
                                      boolean update) {
        try (Connection conexao = ConexaoBanco.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            if (update) {
                stmt.setDouble(1, trecho.getAlturaVegetacao());
                stmt.setString(2, trecho.getTipoTerreno());
                definirEquipe(stmt, 3, trecho.getEquipeId());
                stmt.setInt(4, trecho.getKm());
            } else {
                stmt.setInt(1, trecho.getKm());
                stmt.setDouble(2, trecho.getAlturaVegetacao());
                stmt.setString(3, trecho.getTipoTerreno());
                definirEquipe(stmt, 4, trecho.getEquipeId());
            }

            if (stmt.executeUpdate() == 0 && update) {
                throw new RegistroNaoEncontradoException(
                        "Trecho não encontrado: " + trecho.getKm());
            }
        } catch (RegistroNaoEncontradoException e) {
            throw e;
        } catch (SQLException e) {
            throw erro("persistir trecho", e);
        }
    }

    private void definirEquipe(PreparedStatement stmt, int indice,
                               Integer equipeId) throws SQLException {
        if (equipeId == null) {
            stmt.setNull(indice, Types.NUMERIC);
        } else {
            stmt.setInt(indice, equipeId);
        }
    }

    private TrechoRodovia mapear(ResultSet rs) throws SQLException {
        /* Oracle NUMBER é retornado pelo JDBC como BigDecimal.
           Por isso não fazemos cast direto para Integer. */
        Number equipeBanco = (Number) rs.getObject("EQUIPE_ID");
        Integer equipeId = equipeBanco == null ? null : equipeBanco.intValue();

        return new TrechoRodovia(
                rs.getInt("KM"),
                rs.getDouble("ALTURA_VEGETACAO"),
                rs.getString("TIPO_TERRENO"),
                equipeId
        );
    }

    private BancoDadosException erro(String operacao, SQLException e) {
        return new BancoDadosException(
                "Falha ao " + operacao + " (Oracle " + e.getErrorCode() + ")",
                e
        );
    }
}
