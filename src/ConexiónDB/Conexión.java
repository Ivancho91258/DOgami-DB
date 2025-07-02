package ConexiónDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Conexión {
	private static final String URL= "jdbc:postgresql://localhost:5432/DOgami";
	private static final String USUARIO = "IvanMolina";
	private static final String CONTRASENA = "Maorintak1.";
	
	public static void main(String[] args) {
		Connection conexion = null;
		
		try {
			
			// 1. Cargar el driver JDBC de PostgreSQL
			
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver JDBC de PostgreSQL cargado correctamente");
			
			
			// 2. Establecer la conexión con la base de datos
			
			conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
			System.out.println("Conexión exitosa a PostgreSQL");
			
		} catch (ClassNotFoundException e) {
			System.err.println("Error: El driver JDBC de PostgreSQL no se encontró. Asegúrate de que el JAR esté en el classpath.");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.err.println("Error de conexión a la base de datos. Revisa tus credenciales y configuración de DB.");
            e.printStackTrace();
		} finally {
			
			// 3. Cerrar la conexión y otros recursos (importante para liberar recursos)
			
			try {
				if (conexion != null) {
					conexion.close();
				}
				System.out.println("Conexión a PostgreSQL cerrada.");
				
			} catch (SQLException e) {
				System.err.println("Error al cerrar la conexión de la base de datos:");
				e.printStackTrace();
			}
		}
	}
}