import org.junit.jupiter.api.Test;
import videoteca.backend.model.Film;
import videoteca.backend.model.Genere;
import videoteca.backend.model.StatoVisione;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmTest {

    @Test
    public void filmErroreValutazioneTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            Film filmErroreValutazione = new Film.Builder("il signore degli anelli","Peter Jackson")
                    .valutazione(6)
                    .genere(Genere.FANTASY)
                    .annoUscita(2001)
                    .statoVisione(StatoVisione.DA_VEDERE)
                    .build();
        });

    }
}
