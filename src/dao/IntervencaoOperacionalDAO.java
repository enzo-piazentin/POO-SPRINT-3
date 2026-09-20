package dao;

import database.ConexaoBanco;
import exception.BancoDadosException;
import exception.RegistroNaoEncontradoException;
import model.IntervencaoRegistro;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IntervencaoOperacionalDAO {
    private static final String INSERT="INSERT INTO INTERVENCAO_OPERACIONAL (KM_TRECHO, TIPO, RESPONSAVEL, DATA_INTERVENCAO) VALUES (?, ?, ?, ?)";
    private static final String SELECT="SELECT ID, KM_TRECHO, TIPO, RESPONSAVEL, DATA_INTERVENCAO FROM INTERVENCAO_OPERACIONAL WHERE ID=?";
    private static final String LIST="SELECT ID, KM_TRECHO, TIPO, RESPONSAVEL, DATA_INTERVENCAO FROM INTERVENCAO_OPERACIONAL ORDER BY ID";
    private static final String UPDATE="UPDATE INTERVENCAO_OPERACIONAL SET KM_TRECHO=?, TIPO=?, RESPONSAVEL=?, DATA_INTERVENCAO=? WHERE ID=?";
    private static final String DELETE="DELETE FROM INTERVENCAO_OPERACIONAL WHERE ID=?";
    public int inserir(IntervencaoRegistro e){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(INSERT,new String[]{"ID"})){s.setInt(1,e.kmTrecho());s.setString(2,e.tipo());s.setString(3,e.responsavel());s.setDate(4,Date.valueOf(e.dataIntervencao()));s.executeUpdate();try(ResultSet r=s.getGeneratedKeys()){return r.next()?r.getInt(1):0;}}catch(SQLException x){throw err("inserir intervenção",x);}}
    public IntervencaoRegistro buscarPorId(int id){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(SELECT)){s.setInt(1,id);try(ResultSet r=s.executeQuery()){if(!r.next())throw new RegistroNaoEncontradoException("Intervenção não encontrada: "+id);return map(r);}}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw err("buscar intervenção",x);}}
    public List<IntervencaoRegistro> listarTodas(){List<IntervencaoRegistro> l=new ArrayList<>();try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(LIST);ResultSet r=s.executeQuery()){while(r.next())l.add(map(r));return l;}catch(SQLException x){throw err("listar intervenções",x);}}
    public void atualizar(IntervencaoRegistro e){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(UPDATE)){s.setInt(1,e.kmTrecho());s.setString(2,e.tipo());s.setString(3,e.responsavel());s.setDate(4,Date.valueOf(e.dataIntervencao()));s.setInt(5,e.id());if(s.executeUpdate()==0)throw new RegistroNaoEncontradoException("Intervenção não encontrada: "+e.id());}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw err("atualizar intervenção",x);}}
    public void deletar(int id){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(DELETE)){s.setInt(1,id);if(s.executeUpdate()==0)throw new RegistroNaoEncontradoException("Intervenção não encontrada: "+id);}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw err("deletar intervenção",x);}}
    private IntervencaoRegistro map(ResultSet r)throws SQLException{return new IntervencaoRegistro(r.getInt(1),r.getInt(2),r.getString(3),r.getString(4),r.getDate(5).toLocalDate());}
    private BancoDadosException err(String op,SQLException x){return new BancoDadosException("Falha ao "+op+" (Oracle "+x.getErrorCode()+")",x);}
}
