package modelos;

import dao.MateriaDAO;
import java.util.List;

/**
 * Nicolas sallei
 */
public class Materia {

    private int codMateria;
    private String nombreMateria;
    private MateriaDAO materiaDAO = new MateriaDAO();

    public Materia() {
    }

    public int getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(int codMateria) {
        this.codMateria = codMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    //CRUD
    //CREATE
    public boolean createMateria(Materia materia) {
        return materiaDAO.create(materia);
    }

    //READ
    public List<Materia> readMaterias() {
        return materiaDAO.read();
    }

    //UPDATE
    public boolean updateMateria(Materia materia) {
        return materiaDAO.update(materia);
    }

    //DELETE
    public boolean deleteMateria(int idMateria) {
        return materiaDAO.delete(idMateria);
    }

    public boolean materiaExist(int codMate) {
        return materiaDAO.exist(codMate);
    }

}
