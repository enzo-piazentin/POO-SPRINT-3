package dao;

import database.ConexaoBanco;
import exception.BancoDadosException;
import model.RelatorioPrioridade;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RelatorioPrioridadeDAO {
    private static final String INSERT="INSERT INTO RELATORIO_PRIORIDADE (URGENTES, CRITICOS, ATENCAO, NORMAIS, RESUMO) VALUES (?, ?, ?, ?, ?)";
    private static final String LIST="SELECT ID, URGENTES, CRITICOS, ATENCAO, NORMAIS, RESUMO, GERADO_EM FROM RELATORIO_PRIORIDADE ORDER BY GERADO_EM DESC";
    public int salvarRelatorio(int urgentes,int criticos,int atencao,int normais,String resumo){return inserir(new RelatorioPrioridade(urgentes,criticos,atencao,normais,resumo));}
    public int inserir(RelatorioPrioridade r){try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(INSERT,new String[]{"ID"})){s.setInt(1,r.urgentes());s.setInt(2,r.criticos());s.setInt(3,r.atencao());s.setInt(4,r.normais());s.setString(5,r.resumo());s.executeUpdate();try(ResultSet k=s.getGeneratedKeys()){return k.next()?k.getInt(1):0;}}catch(SQLException x){throw new BancoDadosException("Falha ao salvar relatório",x);}}
    public List<RelatorioPrioridade> listarTodas(){List<RelatorioPrioridade> l=new ArrayList<>();try(Connection c=ConexaoBanco.getConexao();PreparedStatement s=c.prepareStatement(LIST);ResultSet r=s.executeQuery()){while(r.next())l.add(new RelatorioPrioridade(r.getInt(1),r.getInt(2),r.getInt(3),r.getInt(4),r.getInt(5),r.getString(6),r.getTimestamp(7).toLocalDateTime()));return l;}catch(SQLException x){throw new BancoDadosException("Falha ao listar relatórios",x);}}
}
