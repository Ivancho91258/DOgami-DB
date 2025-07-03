package ConexiónDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Conexión {
	private static final String URL= "jdbc:postgresql://localhost:5432/DOgami";
	private static final String USUARIO = "IvanMolina";
	private static final String CONTRASENA = "Maorintak1.";
	
	public static void main(String[] args) {
	// Se carga el driver JDBC de PostgreSQL
		
		Connection conexion = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver cargado correctamente");
			
			// Se realiza la conexión con la base de datos
			
			conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
			System.out.println("Conexión exitosa");
			
		} catch (ClassNotFoundException e) {
			
			System.err.print("Error: El driver no se cargo correctamente");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.print("Error de conexión con la base de datos, revisa tus credenciales");
			e.printStackTrace();
		}
	}
}
			