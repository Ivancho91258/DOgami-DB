package ConexiónDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt; 

public class Conexión {
	private static final String URL= "jdbc:postgresql://localhost:5432/DOgami";
	private static final String USUARIO_DB = "IvanMolina";
	private static final String CONTRASENA_DB = "Maorintak1.";
	
	public static void main(String[] args) {
		Connection conexion = null;
		Statement sentencia = null;
		ResultSet resultado = null;
		
		try {
			
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver JDBC de PostgreSQL cargado correctamente");
			
			conexion = DriverManager.getConnection(URL, USUARIO_DB, CONTRASENA_DB);
			System.out.println("Conexión exitosa a PostgreSQL");
			
			sentencia = conexion.createStatement();
			
			String sqlCreateTable = "CREATE TABLE IF NOT EXISTS usuario (" +
									"id SERIAL PRIMARY KEY," +
									"nombre VARCHAR(45) NOT NULL," +
									"email VARCHAR(25)," +
									"contrasena_hash VARCHAR(20) NOT NULL" +
									")";
			sentencia.executeUpdate(sqlCreateTable);
			System.out.println("Tabla 'personas' creada (o ya existía).");
			
			/* Ejemplo de consulta de datos */
			
			String sqlSelect = "SELECT id, nombre, email, contrasena_hash FROM usuario";
			resultado = sentencia.executeQuery(sqlSelect);
			
			System.out.println("Datos en la tabla 'usuario':");
			if (!resultado.isBeforeFirst()){
				System.out.println("No hay usuarios registrados en la tabla 'usuario'");
			} else {
				while (resultado.next()) {
					int id = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    String email = resultado.getString("email");
                    String contrasenaHash = resultado.getString("contrasena_hash");
                    
                    System.out.println("ID: "+ id + ", Nombre: " + nombre + ", Email: " + email + ", Hash Contraseña: " + contrasenaHash);
					}
				
				}
		} catch (ClassNotFoundException e) {
			System.err.println("Error: El driver JDBC de PostgreSQL no se encontró. Asegúrate de que el JAR esté en el classpath.");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.err.println("Error de conexión a la base de datos o SQL. Revisa tus credenciales y configuración de DB.");
            e.printStackTrace();
		} finally {
			try {
				if (resultado != null) resultado.close();
				if (sentencia != null) sentencia.close();
				if (conexion != null) conexion.close();
				System.out.println("Conexión a PostgreSQL cerrada.");
				
			} catch (SQLException e) {
				System.err.println("Error al cerrar los recursos de la base de datos:");
				e.printStackTrace();
			}
		}
	}
}
