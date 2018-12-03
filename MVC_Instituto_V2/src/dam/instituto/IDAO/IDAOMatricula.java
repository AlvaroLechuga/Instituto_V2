package dam.instituto.IDAO;

import dam.instituto.recursos.Intermediario;
import dam.instituto.recursos.Matricula;
import java.util.List;

public interface IDAOMatricula {
    
    public boolean InsertarMatricula(Matricula matricula);
    public boolean ModificarMatricula(Matricula matricula);
    public boolean EliminarMatricula(Matricula matricula);
    public List<Integer> SacarCodigoAsignatura(String nombreAsignatura);

    public List<Matricula> GetMatriculas(String toString);

    public List<Intermediario> GetNotasAlumno(String toString);

    public void SacarCodigo(String nombre, String nombre_A, double nota, String fecha);
    
}
