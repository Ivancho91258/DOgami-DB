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
					String id = rs.getString ("id");
					String nombre = rs.getString ("nombre");
					String correo = rs.getString ("correo");
					String contraseña = rs.getString ("contraseña");
					String tipo_de_licencia = rs.getString ("tipo_de_licencia");
					System.out.println("\nID: "+id+",\nNombre: "+nombre+",\nCorreo: "+correo+"\nContraseña: "+contraseña+",\nTipo de licencia: "+tipo_de_licencia);
				}
			} catch (SQLException e) {
				System.err.println("Error al ejecutar la consulta: "+e.getLocalizedMessage());
			}
			
			//Agregar un nuevo usuario
			
			System.out.println("\nAgregando datos de usuario nuevo:");
			String idNuevo = "3";
			String nombreNuevo = "Juan Andres Arcilla Morales";
			String correoNuevo = "juan.andres@gmail.com";
			String contraseñaNueva = "Juanarcila.23";
			String licenciaNueva = "Mensual";
			
			//Verificación de usuario
			String sqlCheckEmail = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
			boolean correoExists = false;
			try (PreparedStatement pstmtCheck = conexion.prepareStatement (sqlCheckEmail)) {
				pstmtCheck.setString(1, correoNuevo);
				try (ResultSet rsCheck = pstmtCheck.executeQuery()){
					if (rsCheck.next()) {
						if (rsCheck.getInt(1) > 0) {
							correoExists = true;
						}
					}
				}				
			}catch (SQLException e) {
				System.out.println("Error al verificar el usuario" + e.getMessage());
			}
			if (correoExists) {
				System.out.println("Error: El correo ya existe" + correoNuevo);
				
				//Se agrega el usuario
				
			}else {
				String sqlInsert = "INSERT INTO usuario (id, nombre, correo, contraseña, tipo_de_licencia) VALUES (?, ?, ?, ?, ?)";
				try (PreparedStatement pstmtInsert = conexion.prepareStatement(sqlInsert)) {
				
					pstmtInsert.setString (1, idNuevo);
					pstmtInsert.setString (2, nombreNuevo);
					pstmtInsert.setString(3, correoNuevo);
					pstmtInsert.setString(4, contraseñaNueva);
					pstmtInsert.setString(5, licenciaNueva);
					
					int filasAfectadas = pstmtInsert.executeUpdate ();
					if (filasAfectadas > 0) {
						System.out.print("\nID: "+idNuevo+"\nUsuario: "+nombreNuevo+"\nCorreo: "+correoNuevo+"\nContraseña: "+contraseñaNueva+"\nLicencia: "+licenciaNueva);
					}else {
						System.out.println("No se pudo agregar al usuario nuevo");
						}
					}
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
			