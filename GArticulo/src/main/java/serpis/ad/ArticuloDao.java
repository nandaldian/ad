package serpis.ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDao {
	private static Connection connection;
	public static void init(Connection connection) {
		ArticuloDao.connection = connection;
	}
	private static PreparedStatement selectIdPreparedStatement;
	private static String selectIdSql = 
			"select * from articulo where id=?";
	public static Articulo get(long id) throws SQLException {
		if (selectIdPreparedStatement == null)
			selectIdPreparedStatement = connection.prepareStatement(selectIdSql);
		selectIdPreparedStatement.setObject(1, id);
		ResultSet resultSet=selectIdPreparedStatement.executeQuery();
		resultSet.next();
		Articulo articulo = new Articulo();
		articulo.setId(resultSet.getLong("id"));
		articulo.setNombre(resultSet.getString("nombre"));
		articulo.setPrecio(resultSet.getBigDecimal("precio"));
		articulo.setCategoria(resultSet.getLong("categoria"));
		return articulo;
	}
	
	private static String insertSql = 
		"insert into articulo (nombre, precio, categoria) values (?, ?, ?)";
	private static PreparedStatement insertPreparedStatement;
	private static void insert(Articulo articulo) throws SQLException {
		if (insertPreparedStatement == null)
			insertPreparedStatement = connection.prepareStatement(insertSql);
		insertPreparedStatement.setObject(1, articulo.getNombre());
		insertPreparedStatement.setObject(2, articulo.getPrecio());
		insertPreparedStatement.setObject(3, articulo.getCategoria());
		int affected=insertPreparedStatement.executeUpdate();
		System.out.println("\n Número de líneas afectadas: "+affected+"\n");
	}
	private static PreparedStatement updatePreparedStatement;
	private static String updateSql = 
			"update articulo set nombre= ?, precio= ?, categoria= ? where id= ?";
	private static void update(Articulo articulo) throws SQLException {
		if (updatePreparedStatement == null)
			updatePreparedStatement = connection.prepareStatement(updateSql);
		updatePreparedStatement.setObject(1, articulo.getNombre());
		updatePreparedStatement.setObject(2, articulo.getPrecio());
		updatePreparedStatement.setObject(3, articulo.getCategoria());
		updatePreparedStatement.setObject(4, articulo.getId());
		int affected= updatePreparedStatement.executeUpdate();
		System.out.println("\n Número de líneas afectadas: "+affected+"\n");
	}
	
	public static void save(Articulo articulo) throws SQLException {
		if (articulo.getId() == 0)
			insert(articulo);
		else
			update(articulo);
	}
	
	private static PreparedStatement deletePreparedStatement;
	private static String deleteSql = 
			"delete from articulo where id= ?";
	public static void delete(long id) throws SQLException {
		if (deletePreparedStatement == null)
			deletePreparedStatement = connection.prepareStatement(deleteSql);
		deletePreparedStatement.setObject(1, id);
		int affected= deletePreparedStatement.executeUpdate();
		System.out.println("\n Número de líneas afectadas: "+affected+"\n");
		
		
	}
	
	private static String selectSql = 
			"select * from articulo";
	public static List<Articulo> getList() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(selectSql);
		List<Articulo> articulos = new ArrayList<>();
		while (resultSet.next()){
			Articulo articulo = new Articulo();
			articulo.setId(resultSet.getLong("id"));
			articulo.setNombre(resultSet.getString("nombre"));
			articulo.setPrecio(resultSet.getBigDecimal("precio"));
			articulo.setCategoria(resultSet.getLong("categoria"));
			articulos.add(articulo);
		}
		return articulos;
	}
	
	public static void close() throws SQLException {
		if (insertPreparedStatement != null)
			insertPreparedStatement.close();
	}

}