package dam.instituto.controller;

import dam.instituto.DAOimpl.DAOMatriculaimpl;
import dam.instituto.recursos.Intermediario;
import dam.instituto.recursos.Matricula;
import dam.instituto.vista.FormAddMatricula;
import dam.instituto.vista.FormCalificarAlumno;
import dam.instituto.vista.FormModifyMatricula;
import dam.instituto.vista.FormVerNotasAlumno;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controller_Matricula {

    public static boolean InsertarMatricula(FormAddMatricula formAddMatricula) {
        if (formAddMatricula.getComboAsignatura().getSelectedIndex() != 0 || formAddMatricula.getComboDniAlumno().getSelectedIndex() != 0) {
            Matricula matricula = new Matricula();
            String nombreAsignatura = formAddMatricula.getComboAsignatura().getSelectedItem().toString();
            List<Integer> lstCodigo = DAOMatriculaimpl.getInstance().SacarCodigoAsignatura(nombreAsignatura);

            for (int codigo : lstCodigo) {
                matricula.setCodigoAsignatura(codigo);
            }

            matricula.setDniAlumno(formAddMatricula.getComboDniAlumno().getSelectedItem().toString());
            matricula.setFecha(formAddMatricula.getTxtFechaMatricula().getText());
            matricula.setNota(0);

            if (DAOMatriculaimpl.getInstance().InsertarMatricula(matricula)) {
                JOptionPane.showMessageDialog(null, "Operación realizada correctamente");

                formAddMatricula.getTxtFechaMatricula().setText("");
                formAddMatricula.getComboDniAlumno().setSelectedIndex(0);
                formAddMatricula.getComboAsignatura().setSelectedIndex(0);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al matricular");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una opcion valida");
            return false;
        }
    }

    public static boolean ModificarMatricula(JTable tablaVehiculos) {
        int linea = tablaVehiculos.getSelectedRow();
        
        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
        
        Matricula matricula = new Matricula();
        
        matricula.setDniAlumno(tablaVehiculos.getValueAt(linea, 0).toString());
        matricula.setCodigoAsignatura((int) tablaVehiculos.getValueAt(linea, 1));
        matricula.setNota((double) tablaVehiculos.getValueAt(linea, 2));
        matricula.setFecha(tablaVehiculos.getValueAt(linea, 3).toString());
        
        if(DAOMatriculaimpl.getInstance().ModificarMatricula(matricula)){
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            return false;
        }
    }

    public static boolean EliminarMatricula(FormModifyMatricula formModifyMatricula, JTable tablaVehiculos) {
        
        String dni = "";
        int codigo = 0;
        
        Matricula matricula = new Matricula();
        
        if (tablaVehiculos.getSelectedColumn() != -1) {
            dni = (String) tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 0);
            codigo = (int) tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 1);
            
            matricula.setCodigoAsignatura(codigo);
            matricula.setDniAlumno(dni);
            matricula.setNota(0);
            matricula.setFecha("");   
        }
        
        if(DAOMatriculaimpl.getInstance().EliminarMatricula(matricula)){
            CargarTabla(formModifyMatricula, tablaVehiculos);
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            return false;
        }
        
    }
    
    public static void CargarTabla(FormModifyMatricula formModifyMatricula, JTable tablaVehiculos){
    
        List<Matricula> lstMatricula = DAOMatriculaimpl.getInstance().GetMatriculas(formModifyMatricula.getComboConsultarMatricula().getSelectedItem().toString());
         
         DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                
                if(column == 3){
                    return true;
                }
                
                return false;
            }
        };

        modelo.addColumn("DNI_Alumno");

        modelo.addColumn("Codigo_Asignatura");
        
        modelo.addColumn("Nota");

        modelo.addColumn("Fecha");

        for (Matricula matricula : lstMatricula) {

            Object[] registroLeido
                    = {
                        matricula.getDniAlumno(),
                        matricula.getCodigoAsignatura(),
                        matricula.getNota(),
                        matricula.getFecha()

                    };

            modelo.addRow(registroLeido);

        }

        tablaVehiculos.setModel(modelo);
    }

    public static void CargarNotasAlumno(FormVerNotasAlumno formVerNotasAlumno, JTable tablaVehiculos) {
     
        List<Intermediario> lsIntermediario = DAOMatriculaimpl.getInstance().GetNotasAlumno(formVerNotasAlumno.getComboConsultarAlumno().getSelectedItem().toString());
        
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        modelo.addColumn("Nombre");

        modelo.addColumn("Asignatura");

        modelo.addColumn("Nota");

        modelo.addColumn("Fecha");

        for (Intermediario intermediario : lsIntermediario) {

            Object[] registroLeido
                    = {
                        intermediario.getNombreAlumno(),
                        intermediario.getNombreAsignatura(),
                        intermediario.getNota(),
                        intermediario.getFecha()

                    };

            modelo.addRow(registroLeido);

        }

        tablaVehiculos.setModel(modelo);
    }
    
    public static void CargarCalificarAlumno(FormCalificarAlumno formVerNotasAlumno, JTable tablaVehiculos) {
     
        List<Intermediario> lsIntermediario = DAOMatriculaimpl.getInstance().GetNotasAlumno(formVerNotasAlumno.getComboConsultarAlumno().getSelectedItem().toString());
        
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 2){
                    return true;
                }
                return false;
            }
        };

        modelo.addColumn("Nombre");

        modelo.addColumn("Asignatura");

        modelo.addColumn("Nota");

        modelo.addColumn("Fecha");

        for (Intermediario intermediario : lsIntermediario) {

            Object[] registroLeido
                    = {
                        intermediario.getNombreAlumno(),
                        intermediario.getNombreAsignatura(),
                        intermediario.getNota(),
                        intermediario.getFecha()

                    };

            modelo.addRow(registroLeido);

        }

        tablaVehiculos.setModel(modelo);
    }
    public static void ModificarCalificacion(FormCalificarAlumno frCalificarAlumno, JTable tablaVehiculos) {
        String nombre;
        String nombre_A;
        double nota;
        String fecha;

        if (tablaVehiculos.getSelectedColumn() != -1) {

            nombre = (String) tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 0);
            nombre_A = (String) tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 1);
            nota = Double.parseDouble((String) tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 2));
            fecha = (String) tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 3);

            DAOMatriculaimpl.getInstance().SacarCodigo(nombre, nombre_A, nota, fecha);
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
        }
    }
}
