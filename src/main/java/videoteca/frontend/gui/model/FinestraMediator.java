package videoteca.frontend.gui.model;

import videoteca.backend.gRPC.Film;

public interface FinestraMediator {
    void eseguiricerca(String filmDaCercare);
    void deleteFilm(int id);
    void editFilm(Film f);
    void addFilm(Film f);
    void apriFinestra(Film f);
    void apriFinestraAdd();
};
