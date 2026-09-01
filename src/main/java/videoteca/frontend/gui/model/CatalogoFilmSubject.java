package videoteca.frontend.gui.model;

import videoteca.backend.gRPC.Film;

import java.util.ArrayList;
import java.util.List;

public class CatalogoFilmSubject {

    private List<Film> films;
    private List<Observer> observers;

    public CatalogoFilmSubject() {
        this.films = new ArrayList<>();
        this.observers = new ArrayList<>();

    }

    public void attach(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public void detach(Observer o) {
        observers.remove(o);
    }


    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> nuovaLista){
        this.films = nuovaLista;
        this.notifyObservers();
    }

    public void printObs(){
        System.out.println(observers);
    }

    public void aggiungiFilmLocale(Film film) {
        this.films.add(film);
        notifyObservers();
    }
}
