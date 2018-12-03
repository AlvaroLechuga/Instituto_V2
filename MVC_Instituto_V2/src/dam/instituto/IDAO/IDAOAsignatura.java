package dam.instituto.IDAO;

import dam.instituto.recursos.Asignatura;
import java.util.List;

public interface IDAOAsignatura {
    
    public boolean InsertarAsignatura(Asignatura asignatura);
    public boolean ModificarAsignatura(Asignatura asignatura);
    public boolean EliminarAsignatura(Asignatura asignatura);
    public List<Asignatura> CargarDatos(String toString);
    public List<String> GetNombreAsignatura();

    public List<Asignatura> CargarAsignatura(String texto);

    
}
