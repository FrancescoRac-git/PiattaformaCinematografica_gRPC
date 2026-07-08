package videoteca.backend.database;

import videoteca.backend.model.Film;
import videoteca.backend.model.Genere;
import videoteca.backend.model.StatoVisione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAODatabase implements FilmDAO {

    private static FilmDAODatabase instance = null;
    private static Connection connection; // OGGETTO CHE METTE IN COMUNICAZIONE JAVA CON IL DATABASE


    private FilmDAODatabase(Connection connection) {
        this.connection = connection;
    }

    public static synchronized FilmDAODatabase getInstance(Connection connection) {
        if (instance == null) {
            instance = new FilmDAODatabase(connection);
        }
        return instance;
    }

    @Override
    public Film aggiungi(Film film) {
        String sql = "INSERT INTO film (titolo,regista,annoDiUscita,valutazionepersonale,statoVisione,genere) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){

                pstmt.setString(1, film.getTitolo());
                pstmt.setString(2, film.getRegista());
                pstmt.setInt(3,film.getAnnoDiUscita());
                pstmt.setInt(4,film.getValutazionePersonale());
                pstmt.setString(5,film.getStatoVisione().name());
                pstmt.setString(6,film.getGenere().name());
                pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerato = (rs.getInt(1));
                        return new Film.Builder(film.getTitolo(), film.getRegista())
                                .id(idGenerato)
                                .annoUscita(film.getAnnoDiUscita())
                                .valutazione(film.getValutazionePersonale())
                                .statoVisione(film.getStatoVisione())
                                .genere(film.getGenere())
                                .build();
                    }
                }
                return  film;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modificaFilm(Film film) {
        String sql = "UPDATE film SET regista =?, annoDiUscita =?, valutazionePersonale=?, statoVisione=?, genere=?,titolo=? WHERE id=?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql);){

            pstmt.setString(1, film.getRegista());
            pstmt.setInt(2,film.getAnnoDiUscita());
            pstmt.setInt(3,film.getValutazionePersonale());
            pstmt.setString(4,film.getStatoVisione().name());
            pstmt.setString(5,film.getGenere().name());
            pstmt.setString(6,film.getTitolo());

            pstmt.setInt(7,film.getId());

            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminaFilm(int id) {
        String sql = "DELETE FROM film WHERE id=?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql);){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        }catch (Exception e){
            throw new RuntimeException(e);

        }

    }

    @Override
    public Film cercaPerTitolo(String titolo) {
        String sql = "SELECT * FROM film WHERE titolo=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, titolo);

            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()) {
                    String tit = rs.getString("titolo");
                    String regista = rs.getString("regista");
                    int annoDiUscita = rs.getInt("annoDiUscita");
                    int valutazionePersonale = rs.getInt("valutazionePersonale");
                    Genere genere = Genere.valueOf(rs.getString("genere"));
                    StatoVisione statoVisione = StatoVisione.valueOf(rs.getString("statoVisione"));
                    int idFilm = rs.getInt("id");

                    return new Film.Builder(tit,regista)
                            .annoUscita(annoDiUscita)
                            .valutazione(valutazionePersonale)
                            .genere(genere)
                            .statoVisione(statoVisione)
                            .id(idFilm)
                            .build();
                }
            }


        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return null;

    }

    @Override
    public List<Film> cercaPerRegista(String regista) {
        String sql = " SELECT * FROM film WHERE regista=?";
        List<Film> ret = new ArrayList<Film>();
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, regista);
            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    String titolo = rs.getString("titolo");
                    String reg = rs.getString("regista");
                    int annoDiUscita = rs.getInt("annoDiUscita");
                    int valutazionePersonale = rs.getInt("valutazionePersonale");
                    Genere genere = Genere.valueOf(rs.getString("genere"));
                    StatoVisione statoVisione = StatoVisione.valueOf(rs.getString("statoVisione"));
                    int idFilm = rs.getInt("id");

                    Film filmTrovato = new Film.Builder(titolo,regista)
                            .annoUscita(annoDiUscita)
                            .valutazione(valutazionePersonale)
                            .genere(genere)
                            .statoVisione(statoVisione)
                            .id(idFilm)
                            .build();
                    ret.add(filmTrovato);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    @Override
    public List<Film> tuttiIfilm() {
        String sql = " SELECT * FROM film ";
        List<Film> ret = new ArrayList<Film>();
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    String titolo = rs.getString("titolo");
                    String reg = rs.getString("regista");
                    int annoDiUscita = rs.getInt("annoDiUscita");
                    int valutazionePersonale = rs.getInt("valutazionePersonale");
                    Genere genere = Genere.valueOf(rs.getString("genere"));
                    StatoVisione statoVisione = StatoVisione.valueOf(rs.getString("statoVisione"));
                    int idFilm = rs.getInt("id");

                    Film filmTrovato = new Film.Builder(titolo,reg)
                            .annoUscita(annoDiUscita)
                            .valutazione(valutazionePersonale)
                            .genere(genere)
                            .statoVisione(statoVisione)
                            .id(idFilm)
                            .build();
                    ret.add(filmTrovato);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
}
