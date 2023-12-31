package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Alumno;
import modelos.Cursado;
import modelos.Materia;

/**
 * Nicolas sallei
 */
public class CursadoDAO extends Conexion {

    private final String SQL_INSERT = "INSERT INTO cursado (cur_alu_dni, cur_mat_cod,cur_nota) VALUES (?,?,?)";
    private final String SQL_SELECT = "SELECT * FROM cursado";
    private final String SQL_DELETE = "DELETE FROM cursado WHERE cur_alu_dni=? AND cur_mat_cod=?";
    private final String SQL_UPDATE = "UPDATE cursado SET  cur_nota=? WHERE cur_alu_dni=? AND cur_mat_cod=?";
    private final String SQL_FIND = "SELECT * FROM cursado WHERE cur_alu_dni=?";//Falta Optimizar!

    public boolean create(Cursado cursado) {
        PreparedStatement ps = null;
        /*La interfaz PrepareStatement hereda de la interfaz Statement y puede almacenar
        de manera precompilada una sentencia SQL, por lo que es una opción más eficiente
        si es que necesitamos ejecutar una sentencia SQL en repetidas ocasiones, a su vez
        también podemos especificar parámetros a ejecutar en nuestro query.*/
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setInt(1, cursado.getAlumnoDni().getDni());
            ps.setInt(2, cursado.getCodigoMateria().getCodMateria());
            ps.setDouble(3, cursado.getNota());

            ps.executeUpdate();
            /*El método executeUpdate se utiliza para ejecutar sentencias DML (Data
            Manipulation Language) como son las sentencias insert, update y delete. También
            nos va a permitir ejecutar sentencias de tipo DDL (Data Definition Language) como
            son las sentencias create table, truncate table, entre otras. La función
            executeUpdate regresa un entero, indicando el número de registros afectados como
            resultado de ejecutar el query deseado.
             */

            System.out.println("Agregado Con Exito");

            return true;
        } catch (SQLException e) {
            System.out.println("Error al Crear : " + e);
            return false;

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
        }
    }

    public List<Cursado> read() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cursado cursado;
        List<Cursado> listaCursados = new ArrayList<>();

        try {
            conn = Conexion.getConnection();//Me conecto a la base de datos

            ps = getConnection().prepareStatement(SQL_SELECT);//ejecuto la query
            rs = ps.executeQuery();//igualo la respuesta de la query a un resultSet


            /*El método de executeQuery se utiliza para ejecutar sentencias de tipo select y por
            ello el método regresa un objeto ResultSet, el cual almacena el resultado en forma
            de una matriz de dos dimensiones, es decir, en renglones y columnas, así podemos
            procesar el resultado del query sin ningún problema. */
            while (rs.next()) {
                cursado = new Cursado();
                cursado.setCodigoMateria(new Materia());
                cursado.setAlumnoDni(new Alumno());
                
                cursado.getAlumnoDni().setDni(rs.getInt(1));
                cursado.getCodigoMateria().setCodMateria(rs.getInt(2));
                cursado.setNota(rs.getDouble(3));

                listaCursados.add(cursado);

            }

        } catch (SQLException e) {

            System.out.println(e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
        return listaCursados;
    }

    public boolean update(Cursado cursado) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(3, cursado.getCodigoMateria().getCodMateria());
            ps.setDouble(1, cursado.getNota());
            ps.setInt(2, cursado.getAlumnoDni().getDni());

            ps.executeUpdate();
            System.out.println("Actualizado Con Exito");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al Actualizar : " + e);
            return false;

        } finally {

            Conexion.close(conn);
            Conexion.close(ps);

        }
    }

    public boolean delete(int codCursado) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, codCursado);
            ps.executeUpdate();
            System.out.println("Eliminado con Exito");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al Eliminar : " + e);
            return false;

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);

        }
    }

    public boolean delete(int idDni, int idMat) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idDni);
            ps.setInt(2, idMat);
            ps.executeUpdate();
            System.out.println("Eliminado con Exito");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al Eliminar : " + e);
            return false;

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);

        }
    }

    public Cursado find(int codCursado) {

        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        Cursado cursado = new Cursado();
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_FIND);
            ps.setInt(1, codCursado);
            rs = ps.executeQuery();

            while (rs.next()) {
                cursado.getAlumnoDni().setDni(rs.getInt(1));
                cursado.getCodigoMateria().setCodMateria(rs.getInt(2));
                cursado.setNota(rs.getDouble(3));
            }

        } catch (SQLException e) {
            System.out.println("Error al Buscar : " + e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);

        }

        return cursado;
    }

}
