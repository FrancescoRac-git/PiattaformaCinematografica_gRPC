package videoteca.backend.database;

import videoteca.backend.model.Film;

import java.util.List;

public interface FilmDAO {
    void aggiungi(Film film);
    void modificaFilm(Film film);
    void eliminaFilm(int id);

    Film cercaPerTitolo(String titolo);
    List<Film> cercaPerRegista(String regista);
    List<Film> tuttiIfilm();


}
