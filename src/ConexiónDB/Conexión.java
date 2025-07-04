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
			System.out.println("Conexión exitosa con la DB");

			//Consulta de todos los datos de la tabla
			System.out.println("Consultando tabla: ");
			String sqlSelectAll = "SELECT id, nombre, correo, contraseña, tipo_de_licencia FROM usuario";
			try (Statement stmt = conexion.createStatement();
					ResultSet rs = stmt.executeQuery (sqlSelectAll)){
				while (rs.next()) {
					int id = rs.getInt ("id");
					String nombre = rs.getString ("nombre");
					String correo = rs.getString ("correo");
					String contraseña = rs.getString ("contraseña");
					String tipo_de_licencia = rs.getString ("tipo_de_licencia");
					System.out.println("ID: "+id+",\nNombre: "+nombre+",\nCorreo: "+correo+"\nContraseña: "+contraseña+",\nTipo de licencia: "+tipo_de_licencia);
				}
			} catch (SQLException e) {
				System.err.println("Error al ejecutar la consulta: "+e.getLocalizedMessage());
			}
		} catch (ClassNotFoundException e) {
			
			System.err.print("Error: El driver no se cargo correctamente");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.print("Error de conexión con la base de datos, revisa tus credenciales");
			e.printStackTrace();
		}
	} 
}
			