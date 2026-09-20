package dao;

import database.ConexaoBanco;
import exception.BancoDadosException;
import exception.RegistroNaoEncontradoException;
import model.EquipeManutencao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeManutencaoDAO {
    private static final String INSERT = "INSERT INTO EQUIPE_MANUTENCAO (NOME, ESPECIALIDADE) VALUES (?, ?)";
    private static final String SELECT = "SELECT ID, NOME, ESPECIALIDADE FROM EQUIPE_MANUTENCAO WHERE ID = ?";
    private static final String LIST = "SELECT ID, NOME, ESPECIALIDADE FROM EQUIPE_MANUTENCAO ORDER BY ID";
    private static final String UPDATE = "UPDATE EQUIPE_MANUTENCAO SET NOME = ?, ESPECIALIDADE = ? WHERE ID = ?";
    private static final String DELETE = "DELETE FROM EQUIPE_MANUTENCAO WHERE ID = ?";

    public int inserir(EquipeManutencao e) { try (Connection c=ConexaoBanco.getConexao(); PreparedStatement s=c.prepareStatement(INSERT, new String[]{"ID"})) { s.setString(1,e.nome()); s.setString(2,e.especialidade()); s.executeUpdate(); try(ResultSet r=s.getGeneratedKeys()){ if(r.next()) return r.getInt(1); } return 0; } catch(SQLException x){throw erro("inserir equipe",x);} }
    public EquipeManutencao buscarPorId(int id) { try(Connection c=ConexaoBanco.getConexao(); PreparedStatement s=c.prepareStatement(SELECT)){s.setInt(1,id);try(ResultSet r=s.executeQuery()){if(!r.next())throw new RegistroNaoEncontradoException("Equipe não encontrada: "+id);return map(r);}}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw erro("buscar equipe",x);} }
    public List<EquipeManutencao> listarTodas(){List<EquipeManutencao> l=new ArrayList<>();try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(LIST);ResultSet r=s.executeQuery()){while(r.next())l.add(map(r));return l;}catch(SQLException x){throw erro("listar equipes",x);}}
    public void atualizar(EquipeManutencao e){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(UPDATE)){s.setString(1,e.nome());s.setString(2,e.especialidade());s.setInt(3,e.id());if(s.executeUpdate()==0)throw new RegistroNaoEncontradoException("Equipe não encontrada: "+e.id());}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw erro("atualizar equipe",x);}}
    public void deletar(int id){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(DELETE)){s.setInt(1,id);if(s.executeUpdate()==0)throw new RegistroNaoEncontradoException("Equipe não encontrada: "+id);}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw erro("deletar equipe",x);}}
    private EquipeManutencao map(ResultSet r)throws SQLException{return new EquipeManutencao(r.getInt("ID"),r.getString("NOME"),r.getString("ESPECIALIDADE"));}
    private BancoDadosException erro(String op,SQLException x){return new BancoDadosException("Falha ao "+op+" (Oracle "+x.getErrorCode()+")",x);}
}
