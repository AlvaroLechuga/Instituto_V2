package dam.instituto.IDAO;

import dam.instituto.recursos.Alumno;
import java.util.List;

public interface IDAOAlumno {
    
    public boolean InsertarAlumno(Alumno alumno);
    public boolean ModificarAlumno(Alumno alumno);
    public boolean EliminarAlumno(Alumno alumno);
    public List<Alumno> GetAlumnos(String toString);

    public List<String> GetDNIAlumnos();

    public List<Alumno> GetAlumnosNombre(String texto);
    
}
