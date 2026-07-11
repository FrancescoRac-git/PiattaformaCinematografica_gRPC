package videoteca.backend.database;

import videoteca.backend.model.Film;

import java.util.List;

public interface FilmDAO {
    Film aggiungi(Film film);
    void updateFilm(Film film);
    void eliminaFilm(int id);

    Film cercaPerTitolo(String titolo);
    List<Film> cercaPerRegista(String regista);
    List<Film> tuttiIfilm();


}
