package database;

import exception.CredenciaisInvalidasException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    private static final String HOST = getEnv("DB_HOST", "oracle.fiap.com.br");
    private static final String PORT = getEnv("DB_PORT", "1521");
    private static final String SID = getEnv("DB_SID", "ORCL");
    private static final String USER = getEnv("DB_USER", "");
    private static final String PASSWORD = getEnv("DB_PASSWORD", "");

    public static Connection getConexao() {
        try {
            if (USER.isBlank() || PASSWORD.isBlank()) {
                throw new CredenciaisInvalidasException(
                        "Credenciais ausentes. Defina DB_USER e DB_PASSWORD antes de executar o projeto."
                );
            }

            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" + HOST + ":" + PORT + ":" + SID;
            Connection conexao = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("✅ Conexão criada com sucesso!");
            return conexao;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver Oracle não encontrado: " + e.getMessage(), e);
        } catch (CredenciaisInvalidasException e) {
            throw e;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1017) {
                String usuario = USER.isBlank() ? "<usuario não informado>" : USER.substring(0, Math.min(3, USER.length())) + "***";
                throw new CredenciaisInvalidasException(
                        "Credenciais inválidas para o usuário '" + usuario + "'. Verifique DB_USER e DB_PASSWORD.",
                        e
                );
            }
            throw new RuntimeException("Erro ao conectar ao Oracle: " + e.getMessage(), e);
        }
    }

    public static void fechar(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("🔌 Conexão fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }

    private static String getEnv(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }
}
