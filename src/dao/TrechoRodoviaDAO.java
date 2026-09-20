package dao;

import Model.TrechoRodovia;
import database.ConexaoBanco;
import exception.BancoDadosException;
import exception.RegistroNaoEncontradoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrechoRodoviaDAO {
    private static final String INSERT="INSERT INTO TRECHO_RODOVIA (KM, ALTURA_VEGETACAO, TIPO_TERRENO, EQUIPE_ID) VALUES (?, ?, ?, ?)";
    private static final String SELECT="SELECT KM, ALTURA_VEGETACAO, TIPO_TERRENO, EQUIPE_ID FROM TRECHO_RODOVIA WHERE KM = ?";
    private static final String LIST="SELECT KM, ALTURA_VEGETACAO, TIPO_TERRENO, EQUIPE_ID FROM TRECHO_RODOVIA ORDER BY KM";
    private static final String UPDATE="UPDATE TRECHO_RODOVIA SET ALTURA_VEGETACAO=?, TIPO_TERRENO=?, EQUIPE_ID=? WHERE KM=?";
    private static final String DELETE="DELETE FROM TRECHO_RODOVIA WHERE KM=?";
    public void inserir(TrechoRodovia t){exec(INSERT,t,false);}
    public TrechoRodovia buscarPorId(int km){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(SELECT)){s.setInt(1,km);try(ResultSet r=s.executeQuery()){if(!r.next())throw new RegistroNaoEncontradoException("Trecho não encontrado: "+km);return map(r);}}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw err("buscar trecho",x);}}
    public List<TrechoRodovia> listarTodas(){List<TrechoRodovia> l=new ArrayList<>();try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(LIST);ResultSet r=s.executeQuery()){while(r.next())l.add(map(r));return l;}catch(SQLException x){throw err("listar trechos",x);}}
    public void atualizar(TrechoRodovia t){exec(UPDATE,t,true);}
    public void deletar(int km){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(DELETE)){s.setInt(1,km);if(s.executeUpdate()==0)throw new RegistroNaoEncontradoException("Trecho não encontrado: "+km);}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw err("deletar trecho",x);}}
    private void exec(String sql,TrechoRodovia t,boolean update){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(sql)){s.setInt(1,t.getKm());if(update){s.setDouble(2,t.getAlturaVegetacao());s.setString(3,t.getTipoTerreno());if(t.getEquipeId()==null)s.setNull(4,Types.INTEGER);else s.setInt(4,t.getEquipeId());s.setInt(5,t.getKm());}else{s.setDouble(2,t.getAlturaVegetacao());s.setString(3,t.getTipoTerreno());if(t.getEquipeId()==null)s.setNull(4,Types.INTEGER);else s.setInt(4,t.getEquipeId());}if(s.executeUpdate()==0&&update)throw new RegistroNaoEncontradoException("Trecho não encontrado: "+t.getKm());}catch(RegistroNaoEncontradoException x){throw x;}catch(SQLException x){throw err("persistir trecho",x);}}
    private TrechoRodovia map(ResultSet r)throws SQLException{return new TrechoRodovia(r.getInt(1),r.getDouble(2),r.getString(3),(Integer)r.getObject(4));}
    private BancoDadosException err(String op,SQLException x){return new BancoDadosException("Falha ao "+op+" (Oracle "+x.getErrorCode()+")",x);}
}
