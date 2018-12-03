package dam.instituto.DAOimpl;

import dam.instituto.IDAO.IDAOAlumno;
import dam.instituto.recursos.Alumno;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAlumnoimpl implements IDAOAlumno {

    private List<Alumno> falsaBD;
    private List<String> bdDniAlumnos;
    private static IDAOAlumno dao = null;
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;

    public DAOAlumnoimpl() {
        conn = Conexion.getConnection();
    }

    public static IDAOAlumno getInstance() {
        if (dao == null) {
            dao = new DAOAlumnoimpl();
        }

        return dao;
    }

    @Override
    public boolean InsertarAlumno(Alumno alumno) {

        String dni = alumno.getDni();
        String nombre = alumno.getNombre();
        String direccion = alumno.getDireccion();

        if (ComprobarDNI(dni)) {

            try {

                String consulta = "INSERT INTO `alumno` (`DNI`, `Nombre`, `Direccion`) VALUES ('" + dni + "', '" + nombre + "', '" + direccion + "')";
                st = conn.createStatement();
                st.executeUpdate(consulta);
                st.close();

            } catch (SQLException sqle) {
                return false;
                
            }

            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean ModificarAlumno(Alumno alumno) {

        String dni = alumno.getDni();
        String nombre = alumno.getNombre();
        String direccion = alumno.getDireccion();

        try {
            String consulta = "UPDATE alumno SET Nombre='" + nombre + "', Direccion='" + direccion + "' WHERE DNI='" + dni + "'";
            st = conn.createStatement();
            st.executeUpdate(consulta);
            st.close();
            return true;
        } catch (SQLException sqle) {
            System.out.println("Error al modificar");
            return false;
        }
    }

    @Override
    public boolean EliminarAlumno(Alumno alumno) {
        String dni = alumno.getDni();

        try {

            String consulta = "DELETE FROM alumno WHERE DNI = '" + dni + "'";

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

    public boolean ComprobarDNI(String dni) {

        if (dni.length() < 9 || dni.length() > 9 || Character.isLetter(dni.charAt(8)) == false) {
            return false;
        } else {
            int numero;
            String letra;
            try {
                numero = Integer.parseInt(dni.substring(0, 7));
                letra = dni.substring(8);
                letra = letra.toUpperCase();

                String[] juegoCaracteres = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

                int modulo = numero % 23;
                String testLetra = juegoCaracteres[modulo];
                if (letra.equals(testLetra)) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }
        }

    }

    @Override
    public List<Alumno> GetAlumnos(String dni) {
        try {

            String consulta = "SELECT * FROM alumno WHERE DNI = '" + dni + "'";
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            imprimirConsulta(rs);
            st.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOAlumnoimpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.falsaBD;
    }

    private void imprimirConsulta(ResultSet rs) {

        try {
            this.falsaBD = new ArrayList<Alumno>();
            while (rs.next()) {
                falsaBD.add(new Alumno(rs.getString("DNI"), rs.getString("Nombre"), rs.getString("Direccion")));
            }
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
        }
    }

    @Override
    public List<String> GetDNIAlumnos() {
        try {

            String consulta = "SELECT DNI FROM alumno";
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            imprimirDNIAlumno(rs);
            st.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOAlumnoimpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.bdDniAlumnos;
    }

    private void imprimirDNIAlumno(ResultSet rs) {
        try {
            this.bdDniAlumnos = new ArrayList<>();
            while (rs.next()) {
                bdDniAlumnos.add(rs.getString("DNI"));
            }
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
        }
    }

    @Override
    public List<Alumno> GetAlumnosNombre(String texto) {
        try {
            String consulta = "";

            if (texto.equals("")) {
                consulta = "SELECT * FROM alumno WHERE Nombre LIKE  '%********%'";
            } else {
                consulta = "SELECT * FROM alumno WHERE Nombre LIKE  '%" + texto + "%'";
            }

            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            imprimirConsulta(rs);
            st.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOAlumnoimpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.falsaBD;
    }

}
