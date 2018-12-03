package dam.instituto.DAOimpl;

import dam.instituto.IDAO.IDAOAsignatura;
import dam.instituto.recursos.Asignatura;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAsignaturaimpl implements IDAOAsignatura {

    private List<Asignatura> falsaBD;
    private List<String> bdNombreAsignatura;
    private static IDAOAsignatura dao = null;
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;

    public DAOAsignaturaimpl() {
        conn = Conexion.getConnection();
    }

    public static IDAOAsignatura getInstance() {
        if (dao == null) {
            dao = new DAOAsignaturaimpl();
        }

        return dao;
    }

    @Override
    public boolean InsertarAsignatura(Asignatura asignatura) {
        int codigo = asignatura.getCodigo();
        String nombre = asignatura.getNombre();
        int horas = asignatura.getHoras();

        try {

            String consulta = "INSERT INTO `asignatura` (`Codigo`, `Nombre_A`, `Horas`) VALUES ('" + codigo + "', '" + nombre + "', '" + horas + "')";

            st = conn.createStatement();
            st.executeUpdate(consulta);
            st.close();

        } catch (SQLException sqle) {

        }

        return true;
    }

    @Override
    public boolean ModificarAsignatura(Asignatura asignatura) {
        int codigo = asignatura.getCodigo();
        String nombre = asignatura.getNombre();
        int horas = asignatura.getHoras();
        try {

            String consulta = "UPDATE asignatura SET Nombre_A='" + nombre + "', Horas= " + horas + "  WHERE Codigo=" + codigo;
            st = conn.createStatement();
            st.executeUpdate(consulta);
            st.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAsignaturaimpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean EliminarAsignatura(Asignatura asignatura) {
        String nombre = asignatura.getNombre();

        try {

            String consulta = "DELETE FROM asignatura WHERE Nombre = '" + nombre + "'";

            st = conn.createStatement();
            int confirmar = st.executeUpdate(consulta);
            if (confirmar == 1) {

                return true;

            } else {

                return false;
            }

        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public List<Asignatura> CargarDatos(String nombre) {

        try {

            //String consulta = "SELECT * FROM asignatura WHERE Nombre_A = '" + nombre + "'";
            String consulta = "SELECT * FROM asignatura WHERE Nombre_A LIKE '%" + nombre + "%'";
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            imprimirConsulta(rs);
            st.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOAsignaturaimpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.falsaBD;
    }

    private void imprimirConsulta(ResultSet rs) {
        try {
            this.falsaBD = new ArrayList<Asignatura>();
            while (rs.next()) {
                falsaBD.add(new Asignatura(rs.getInt("Codigo"), rs.getString("Nombre_A"), rs.getInt("Horas")));
            }
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
        }
    }

    @Override
    public List<String> GetNombreAsignatura() {

        try {

            String consulta = "SELECT Nombre_A FROM asignatura";
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            imprimirNombreAsignatura(rs);
            st.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOAlumnoimpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bdNombreAsignatura;
    }

    private void imprimirNombreAsignatura(ResultSet rs) {
        try {
            this.bdNombreAsignatura = new ArrayList<>();
            while (rs.next()) {
                bdNombreAsignatura.add(rs.getString("Nombre_A"));
            }
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
        }
    }

    @Override
    public List<Asignatura> CargarAsignatura(String texto) {
        try {
            String consulta = "";
            if(texto.equals("")){
                consulta = "SELECT * FROM asignatura WHERE Nombre_A LIKE '%*********%'";
            }else{
                consulta = "SELECT * FROM asignatura WHERE Nombre_A LIKE '%" + texto + "%'";
            }
            
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            imprimirConsulta(rs);
            st.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOAsignaturaimpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.falsaBD;
    }

}
