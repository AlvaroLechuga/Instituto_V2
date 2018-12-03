package dam.instituto.test;

import dam.instituto.DAOimpl.Conexion;
import dam.instituto.vista.FormPrincipal;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class test {

    
    public static void main(String[] args) {
        FormPrincipal window = new FormPrincipal();
        
        window.setVisible(true);

        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                  
                Conexion.cerrarSesion();
                System.exit(0);

            }
        });
    }
    
}
