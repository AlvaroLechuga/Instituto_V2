package dam.instituto.controller;

import dam.instituto.DAOimpl.DAOAlumnoimpl;
import dam.instituto.recursos.Alumno;
import dam.instituto.vista.FormAddAlumno;
import dam.instituto.vista.FormAddMatricula;
import dam.instituto.vista.FormCalificarAlumno;
import dam.instituto.vista.FormConsultarNotas;
import dam.instituto.vista.FormModifyAlumno;
import dam.instituto.vista.FormModifyMatricula;
import dam.instituto.vista.FormMostrarAlumnos;
import dam.instituto.vista.FormVerNotasAlumno;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controller_Alumno {

    public static boolean InsertarAlumno(FormAddAlumno formaddAlumno) {

        Alumno alumno = new Alumno();

        alumno.setDni(formaddAlumno.getTxtDNIAlumno().getText());
        alumno.setNombre(formaddAlumno.getTxtNombreAlumno().getText());
        alumno.setDireccion(formaddAlumno.getTxtDireccionAlumno().getText());

        if (DAOAlumnoimpl.getInstance().InsertarAlumno(alumno)) {
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            formaddAlumno.getTxtDNIAlumno().setText("");
            formaddAlumno.getTxtNombreAlumno().setText("");
            formaddAlumno.getTxtDireccionAlumno().setText("");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error en el DNI introducido");
            return false;
        }
    }

    public static boolean ModificarAlumno(JTable tablaVehiculos) {

        int linea = tablaVehiculos.getSelectedRow();

        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();

        Alumno alumno = new Alumno();

        alumno.setDni(tablaVehiculos.getValueAt(linea, 0).toString());
        alumno.setNombre(tablaVehiculos.getValueAt(linea, 1).toString());
        alumno.setDireccion(tablaVehiculos.getValueAt(linea, 2).toString());

        if (DAOAlumnoimpl.getInstance().ModificarAlumno(alumno)) {
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            return false;
        }
    }

    public static boolean EliminarAlumno(FormModifyAlumno formModifyAlumno, JTable tablaVehiculos) {
        String dni = "";

        Alumno alumno = new Alumno();

        if (tablaVehiculos.getSelectedColumn() != -1) {
            dni = (String) tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 0);
            alumno.setDni(dni);
            alumno.setNombre("");
            alumno.setDireccion("");
        }

        if (DAOAlumnoimpl.getInstance().EliminarAlumno(alumno)) {
            CargarTabla(formModifyAlumno, tablaVehiculos);
            formModifyAlumno.getComboConsultarAlumno().removeItem(formModifyAlumno.getComboConsultarAlumno().getSelectedItem());
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            return false;
        }

    }

    public static void CargarTabla(FormModifyAlumno formModifyAlumno, JTable tablaVehiculos) {

        List<Alumno> lstAlumno = DAOAlumnoimpl.getInstance().GetAlumnos(formModifyAlumno.getComboConsultarAlumno().getSelectedItem().toString());

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 1 || column == 2) {
                    return true;
                }
                return false;
            }
        };

        modelo.addColumn("DNI");

        modelo.addColumn("Nombre");

        modelo.addColumn("Direccion");

        for (Alumno alumno : lstAlumno) {

            Object[] registroLeido
                    = {
                        alumno.getDni(),
                        alumno.getNombre(),
                        alumno.getDireccion()

                    };

            modelo.addRow(registroLeido);

        }

        tablaVehiculos.setModel(modelo);
    }

    public static void CargarDNIAlumno(FormModifyAlumno formModifyAlumno) {

        List<String> lstDNIAlumno = DAOAlumnoimpl.getInstance().GetDNIAlumnos();

        for (String dni : lstDNIAlumno) {
            formModifyAlumno.getComboConsultarAlumno().addItem(dni);
        }

    }

    public static void CargarDNIAlumnoMatricula(FormAddMatricula formAddMatricula) {

        List<String> lstDNIAlumno = DAOAlumnoimpl.getInstance().GetDNIAlumnos();

        for (String dni : lstDNIAlumno) {
            formAddMatricula.getComboDniAlumno().addItem(dni);
        }

    }

    public static void CargarDNIModificarMatricula(FormModifyMatricula formModifyMatricula, JTable tablaVehiculos) {
        List<String> lstDNIAlumno = DAOAlumnoimpl.getInstance().GetDNIAlumnos();

        for (String dni : lstDNIAlumno) {
            formModifyMatricula.getComboConsultarMatricula().addItem(dni);
        }
    }

    public static void CargarDniNotas(FormVerNotasAlumno formNotasAlumno, JTable tablaVehiculos) {
        List<String> lstDNIAlumno = DAOAlumnoimpl.getInstance().GetDNIAlumnos();

        for (String dni : lstDNIAlumno) {
            formNotasAlumno.getComboConsultarAlumno().addItem(dni);
        }
    }

    public static void CargarDniCalificar(FormCalificarAlumno formCalificarAlumno, JTable tablaVehiculos) {
        List<String> lstDNIAlumno = DAOAlumnoimpl.getInstance().GetDNIAlumnos();

        for (String dni : lstDNIAlumno) {
            formCalificarAlumno.getComboConsultarAlumno().addItem(dni);
        }
    }
    
    public static void CargarDNIVerNotas(FormConsultarNotas formCalificarNotas, JTable tablaVehiculos) {
        List<String> lstDNIAlumno = DAOAlumnoimpl.getInstance().GetDNIAlumnos();

        for (String dni : lstDNIAlumno) {
            formCalificarNotas.getComboDniAlumno().addItem(dni);
        }
    }
    
    public static void MostrarAlumnos(FormMostrarAlumnos formMostrarAlumno, JTable tablaVehiculos) {
        List<Alumno> lstAlumno = DAOAlumnoimpl.getInstance().GetAlumnosNombre(formMostrarAlumno.getTexto());

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
        };

        modelo.addColumn("DNI");

        modelo.addColumn("Nombre");

        modelo.addColumn("Direccion");

        for (Alumno alumno : lstAlumno) {

            Object[] registroLeido
                    = {
                        alumno.getDni(),
                        alumno.getNombre(),
                        alumno.getDireccion()

                    };

            modelo.addRow(registroLeido);

        }

        tablaVehiculos.setModel(modelo);
    }

}
