package videoteca.backend.gRPC;

import io.grpc.stub.StreamObserver;
import videoteca.backend.database.FilmDAO;
import videoteca.backend.database.FilmDAODatabase;
import videoteca.backend.model.Genere;
import videoteca.backend.model.StatoVisione;

import java.util.ArrayList;
import java.util.List;


public class FilmServiceImpl extends FilmServiceGrpc.FilmServiceImplBase {

    private final FilmDAO filmDAO;

    public FilmServiceImpl(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }


    @Override
    public void addFilm(AddFilmRequest request, StreamObserver<GestioneFilmResponse> responseObserver) {


        videoteca.backend.gRPC.Film filmGrpc = request.getFilm();
        GestioneFilmResponse response;

        try {
            videoteca.backend.model.Film mioFilmDaSalvare = new videoteca.backend.model.Film.Builder(filmGrpc.getTitolo(), filmGrpc.getRegista())
                    .id(filmGrpc.getId())
                    .annoUscita(filmGrpc.getAnnoUscita())
                    .genere(videoteca.backend.model.Genere.valueOf(filmGrpc.getGenere().toUpperCase()))
                    .valutazione(filmGrpc.getValutazione())
                    .statoVisione(videoteca.backend.model.StatoVisione.valueOf(filmGrpc.getStatoVisione().toUpperCase()))
                    .build();
            videoteca.backend.model.Film filmSalvato = filmDAO.aggiungi(mioFilmDaSalvare);



            System.out.println("Server: Film '" + filmSalvato.getTitolo() + "' salvato nel DB!");

            response = GestioneFilmResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Film salvato con successo! ID assegnato: " + filmSalvato.getId())
                    .build();

        } catch (Exception e) {
            System.err.println("Server: Errore durante il salvataggio: " + e.getMessage());
            response = GestioneFilmResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Errore DB: " + e.getMessage())
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void deleteFilm(DeleteFilmRequest request, StreamObserver<GestioneFilmResponse> responseObserver) {
        int id = request.getId();
        GestioneFilmResponse response;

        try{
            filmDAO.eliminaFilm(id);

            response = GestioneFilmResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("il film con id"+ id+ "è stato rimosso con successo" )
                    .build();
        }catch (Exception e){
            System.err.println("Server: Errore durante l'eliminazione del  film: " + e.getMessage());
            response = GestioneFilmResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Errore DB: "+e.getMessage())
                    .build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateFilm(UpdateFilmRequest request, StreamObserver<GestioneFilmResponse> responseObserver) {
        Film filmGrpc = request.getFilm();
        GestioneFilmResponse response;
        try{
            videoteca.backend.model.Film filmDaModificare = new videoteca.backend.model.Film.Builder(filmGrpc.getTitolo(), filmGrpc.getRegista())
                    .id(filmGrpc.getId())
                    .annoUscita(filmGrpc.getAnnoUscita())
                    .genere(videoteca.backend.model.Genere.valueOf(filmGrpc.getGenere().toUpperCase()))
                    .valutazione(filmGrpc.getValutazione())
                    .statoVisione(videoteca.backend.model.StatoVisione.valueOf(filmGrpc.getStatoVisione().toUpperCase()))
                    .build();
            filmDAO.updateFilm(filmDaModificare);

            response =  GestioneFilmResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("il film "+ filmDaModificare.getTitolo()+ "è stato aggiornato")
                    .build();
        } catch (Exception e) {
            System.out.println(" errore nel DB"+ e.getMessage());
            response = GestioneFilmResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(" il film non è stato aggiornato!")
                    .build();

        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



    @Override
    public void searchFilms(SearchFilmsRequest request, StreamObserver<SearchFilmsResponse> responseObserver) {


        String titolo = request.getQueryTitolo();
        String regista = request.getQueryRegista();


        List<videoteca.backend.model.Film> filmTrovatiDalDao= new ArrayList<>();

        try {

            if (titolo != null && !titolo.isEmpty()) {
                filmTrovatiDalDao.add(filmDAO.cercaPerTitolo(titolo));

            } else if (regista != null && !regista.isEmpty()) {
                filmTrovatiDalDao = filmDAO.cercaPerRegista(regista);

            } else {
                filmTrovatiDalDao = filmDAO.tuttiIfilm();
            }


            SearchFilmsResponse.Builder responseBuilder = SearchFilmsResponse.newBuilder();

            for (videoteca.backend.model.Film mioFilm : filmTrovatiDalDao) {


                videoteca.backend.gRPC.Film filmGrpc = videoteca.backend.gRPC.Film.newBuilder()
                        .setId(mioFilm.getId())
                        .setTitolo(mioFilm.getTitolo())
                        .setRegista(mioFilm.getRegista())
                        .setAnnoUscita(mioFilm.getAnnoDiUscita())
                        .setGenere(mioFilm.getGenere().name())
                        .setValutazione(mioFilm.getValutazionePersonale())
                        .setStatoVisione(mioFilm.getStatoVisione().name())
                        .build();

                responseBuilder.addFilms(filmGrpc);
            }


            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            System.err.println("Server: Errore durante la ricerca dei film: " + e.getMessage());


            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Errore DB durante la ricerca: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}