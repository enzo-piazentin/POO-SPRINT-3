package database;

import exception.BancoDadosException;
import exception.CredenciaisInvalidasException;
import java.sql.*;

/** Conexão Oracle centralizada. Nunca versiona credenciais reais. */
public final class ConexaoBanco {
    private static final ConexaoBanco INSTANCE=new ConexaoBanco();
    private final String url=env("DB_URL","jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL");
    private final String user=env("DB_USER","");
    private final String password=env("DB_PASSWORD","");
    private ConexaoBanco(){}
    public static ConexaoBanco getInstancia(){return INSTANCE;}
    public static Connection getConexao(){return INSTANCE.conectar();}
    public Connection conectar(){try{Class.forName("oracle.jdbc.driver.OracleDriver");if(user.isBlank()||password.isBlank())throw new CredenciaisInvalidasException("Defina DB_USER e DB_PASSWORD antes de executar.");return DriverManager.getConnection(url,user,password);}catch(CredenciaisInvalidasException e){throw e;}catch(ClassNotFoundException e){throw new BancoDadosException("Driver ojdbc17.jar não encontrado no classpath",e);}catch(SQLException e){if(e.getErrorCode()==1017)throw new CredenciaisInvalidasException("Usuário ou senha Oracle inválidos",e);throw new BancoDadosException("Não foi possível conectar ao Oracle",e);}}
    public void desconectar(){/* conexões são fechadas por try-with-resources nos DAOs */}
    public static void fechar(Connection c){if(c!=null)try{c.close();}catch(SQLException e){throw new BancoDadosException("Erro ao fechar conexão",e);}}
    private static String env(String key,String fallback){String value=System.getenv(key);return value==null||value.isBlank()?fallback:value;}
}
