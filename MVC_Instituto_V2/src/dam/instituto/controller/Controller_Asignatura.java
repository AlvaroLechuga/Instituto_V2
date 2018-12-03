package dam.instituto.controller;

import dam.instituto.DAOimpl.DAOAsignaturaimpl;
import dam.instituto.recursos.Asignatura;
import dam.instituto.vista.FormAddAsignatura;
import dam.instituto.vista.FormAddMatricula;
import dam.instituto.vista.FormModifyAsignatura;
import dam.instituto.vista.FormMostrarAsignatura;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controller_Asignatura {

    public static boolean InsertarAsignatura(FormAddAsignatura formaddAsignatura) {

        Asignatura asignatura = new Asignatura();

        asignatura.setCodigo(Integer.parseInt(formaddAsignatura.getTxtCodigoAsignatura().getText()));
        asignatura.setNombre(formaddAsignatura.getTxtNombreAsignatura().getText());
        asignatura.setHoras(Integer.parseInt(formaddAsignatura.getTxtHorasAsignatura().getValue().toString()));

        if (DAOAsignaturaimpl.getInstance().InsertarAsignatura(asignatura)) {
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            formaddAsignatura.getTxtCodigoAsignatura().setText("");
            formaddAsignatura.getTxtNombreAsignatura().setText("");
            formaddAsignatura.getTxtHorasAsignatura().setValue(0);

            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al añadir");
            return false;
        }

    }

    public static boolean ModificarAsignatura(JTable tablaVehiculos) {
        int linea = tablaVehiculos.getSelectedRow();

        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();

        Asignatura asignatura = new Asignatura();

        asignatura.setCodigo((int) tablaVehiculos.getValueAt(linea, 0));
        asignatura.setNombre(tablaVehiculos.getValueAt(linea, 1).toString());
        asignatura.setHoras((int) tablaVehiculos.getValueAt(linea, 2));

        if (DAOAsignaturaimpl.getInstance().ModificarAsignatura(asignatura)) {
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            return false;
        }
    }

    public static boolean EliminarAsignatura(FormModifyAsignatura formmodifyAsignatura, JTable tablaVehiculos) {
        String nombre = "";

        Asignatura asignatura = new Asignatura();

        if (tablaVehiculos.getSelectedColumn() != -1) {
            nombre = (String) tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 1);
            asignatura.setCodigo(0);
            asignatura.setNombre(nombre);
            asignatura.setHoras(0);
        }

        if (DAOAsignaturaimpl.getInstance().EliminarAsignatura(asignatura)) {
            CargarTabla(formmodifyAsignatura, tablaVehiculos);
            formmodifyAsignatura.getComboConsultarAsignatura().removeItem(formmodifyAsignatura.getComboConsultarAsignatura().getSelectedItem());
            JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            return true;
        } else {
            return false;
        }
    }

    public static void CargarTabla(FormModifyAsignatura formmodifyAsignatura, JTable tablaVehiculos) {
        List<Asignatura> lstAsignaturas = DAOAsignaturaimpl.getInstance().CargarDatos(formmodifyAsignatura.getComboConsultarAsignatura().getSelectedItem().toString());

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                if (column == 1 || column == 2) {
                    return true;
                }

                return false;
            }
        };

        modelo.addColumn("Codigo");

        modelo.addColumn("Nombre");

        modelo.addColumn("Horas");

        for (Asignatura asignatura : lstAsignaturas) {

            Object[] registroLeido
                    = {
                        asignatura.getCodigo(),
                        asignatura.getNombre(),
                        asignatura.getHoras()

                    };

            modelo.addRow(registroLeido);

        }

        tablaVehiculos.setModel(modelo);
    }

    public static void CargarCodigo(FormModifyAsignatura formmodifyAsignatura, JTable tablaVehiculos) {

        List<String> lstDNIAlumno = DAOAsignaturaimpl.getInstance().GetNombreAsignatura();

        for (String nombre : lstDNIAlumno) {
            formmodifyAsignatura.getComboConsultarAsignatura().addItem(nombre);
        }

    }

    public static void CargarCodigoMatricula(FormAddMatricula formAddMatricula) {

        List<String> lstDNIAlumno = DAOAsignaturaimpl.getInstance().GetNombreAsignatura();

        for (String dni : lstDNIAlumno) {
            formAddMatricula.getComboAsignatura().addItem(dni);
        }

    }

    public static void MostrarAsignaturas(FormMostrarAsignatura formMostrarAsignatura, JTable tablaVehiculos) {
        List<Asignatura> lstAsignaturas = DAOAsignaturaimpl.getInstance().CargarAsignatura(formMostrarAsignatura.getTexto());

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
        };

        modelo.addColumn("Codigo");

        modelo.addColumn("Nombre");

        modelo.addColumn("Horas");

        for (Asignatura asignatura : lstAsignaturas) {

            Object[] registroLeido
                    = {
                        asignatura.getCodigo(),
                        asignatura.getNombre(),
                        asignatura.getHoras()

                    };

            modelo.addRow(registroLeido);

        }

        tablaVehiculos.setModel(modelo);
    }
}
