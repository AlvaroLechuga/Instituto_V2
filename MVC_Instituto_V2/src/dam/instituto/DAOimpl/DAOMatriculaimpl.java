/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.instituto.DAOimpl;

import dam.instituto.IDAO.IDAOMatricula;
import dam.instituto.recursos.Intermediario;
import dam.instituto.recursos.Matricula;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOMatriculaimpl implements IDAOMatricula {

    List<Intermediario> falsaBD2;
    List<Matricula> falsaBD;
    List<Integer> CodigoAsignatura;
    private static IDAOMatricula dao = null;
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;

    public DAOMatriculaimpl() {
        conn = Conexion.getConnection();
    }

    public static IDAOMatricula getInstance() {
        if (dao == null) {
            dao = new DAOMatriculaimpl();
        }

        return dao;
    }

    @Override
    public boolean InsertarMatricula(Matricula matricula) {
        String dni = matricula.getDniAlumno();
        String fecha = matricula.getFecha();
        int codAsignatura = matricula.getCodigoAsignatura();
        double nota = matricula.getNota();

        try {

            String consulta = "INSERT INTO `matricula` (`DNI_Alumno`, `Codigo_Asignatura`, `Nota`, `Fecha`) VALUES ('" + dni + "', '" + codAsignatura + "', '" + nota + "', '" + fecha + "')";
            st = conn.createStatement();
            st.executeUpdate(consulta);
            st.close();
            return true;
        } catch (SQLException sqle) {
            return false;
        }
    }

    @Override
    public boolean ModificarMatricula(Matricula matricula) {
        try {

            String dni = matricula.getDniAlumno();
            int codigo = matricula.getCodigoAsignatura();
            String fecha = matricula.getFecha();

            String consulta = "UPDATE matricula " + "SET Fecha = '" + fecha + "' WHERE DNI_Alumno = '" + dni + "' AND " + "Codigo_Asignatura =" + codigo;

            st = conn.createStatement();
            st.executeUpdate(consulta);

            st.close();

            return true;

        } catch (SQLException ex) {
            System.out.println("Error al modificar");
            return false;
        }

    }

    @Override
    public boolean EliminarMatricula(Matricula matricula) {
        String dni = matricula.getDniAlumno();
        int codigo = matricula.getCodigoAsignatura();

        try {
            String consulta = "DELETE FROM matricula WHERE DNI_Alumno = '" + dni + "' AND Codigo_Asignatura =  '" + codigo + "'";
            System.out.println("");
            st = conn.createStatement();
            int confirmar = st.executeUpdate(consulta);
            if (confirmar == 1) {

                return true;

            } else {

                return false;
            }
        } catch (SQLException sqle) {
            return false;
        }
    }

    @Override
    public List<Integer> SacarCodigoAsignatura(String nombreAsignatura) {
        CodigoAsignatura = new ArrayList<>();

        try {

            String consulta = "SELECT Codigo from asignatura WHERE Nombre_A = '" + nombreAsignatura + "'";
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            Sacar(rs);

        } catch (SQLException sqle) {
            Logger.getLogger(DAOMatriculaimpl.class
                    .getName()).log(Level.SEVERE, null, sqle);
        }
        return CodigoAsignatura;
    }

    private void Sacar(ResultSet rs) {
        try {
            this.CodigoAsignatura = new ArrayList<>();
            while (rs.next()) {
                CodigoAsignatura.add(rs.getInt("Codigo"));
            }
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
        }
    }

    @Override
    public List<Matricula> GetMatriculas(String dni) {
        try {

            String consulta = "SELECT * FROM matricula WHERE DNI_Alumno = '" + dni + "'";
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
            this.falsaBD = new ArrayList<Matricula>();
            while (rs.next()) {
                falsaBD.add(new Matricula(rs.getString("DNI_Alumno"), rs.getInt("Codigo_Asignatura"), rs.getDouble("Nota"), rs.getString("Fecha")));
            }
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
        }
    }

    @Override
    public List<Intermediario> GetNotasAlumno(String dni) {
        
    try {
            String consulta = "SELECT alumno.Nombre, asignatura.Nombre_A, matricula.Nota, matricula.Fecha FROM ((alumno INNER JOIN matricula ON alumno.DNI = matricula.DNI_Alumno) INNER JOIN asignatura ON matricula.Codigo_Asignatura = asignatura.Codigo) WHERE DNI = '" + dni + "'";
            
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            imprimirConsulta2(rs);
            st.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAOAlumnoimpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return falsaBD2;
    }

    private void imprimirConsulta2(ResultSet rs) {
        try {
            this.falsaBD2 = new ArrayList<Intermediario>();
            while (rs.next()) {
                falsaBD2.add(new Intermediario(rs.getString("Nombre"), rs.getString("Nombre_A"), rs.getDouble("Nota"), rs.getString("Fecha")));
            }
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
        }
    }

    @Override
    public void SacarCodigo(String nombre, String nombre_A, double nota, String fecha) {

        try {
            int codigo = 0;
            String dni = "";
            String consulta = "SELECT DNI FROM alumno WHERE Nombre = '" + nombre + "'";
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            dni = Nombre(rs);
            consulta = "SELECT Codigo FROM asignatura WHERE Nombre_A = '" + nombre_A + "'";
            rs = st.executeQuery(consulta);
            codigo = Codigo(rs);
            consulta = "UPDATE matricula " + "SET Fecha = '" + fecha + "', " + "Nota =" + nota + " WHERE DNI_Alumno = '" + dni + "' AND " + "Codigo_Asignatura =" + codigo;
            st.executeUpdate(consulta);
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOMatriculaimpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String Nombre(ResultSet rs) {
        try {
            String dni = "";
            while (rs.next()) {
                dni = rs.getString("DNI");
            }
            return dni;
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
            return "";
        }

    }

    public int Codigo(ResultSet rs) {
        try {
            int codigo = 0;
            while (rs.next()) {
                codigo = rs.getInt("Codigo");

            }
            return codigo;
        } catch (SQLException ex) {
            System.out.println("Error en el resultset");
            ex.printStackTrace();
            return -1;
        }
    }
}
