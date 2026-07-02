package videoteca.backend.model;

public class Film {

    private String titolo;
    private String regista;
    private int annoDiUscita;
    private int valutazionePersonale;
    private StatoVisione StatoVisione;
    private Genere Genere;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film(String titolo, String regista, int annoDiUscita, int valutazionePersonale, StatoVisione statoVisione,
                Genere genere) {
        this.titolo = titolo;
        this.regista = regista;
        this.annoDiUscita = annoDiUscita;
        setValutazionePersonale(valutazionePersonale);
        this.StatoVisione = statoVisione;
        this.Genere = genere;

    }

    public String getTitolo() {
        return titolo;
    }
    public String getRegista() {
        return regista;
    }

    public int getAnnoDiUscita() {
        return annoDiUscita;
    }



    public int getValutazionePersonale() {
        return valutazionePersonale;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public void setAnnoDiUscita(int annoDiUscita) {
        this.annoDiUscita = annoDiUscita;
    }

    public void setValutazionePersonale(int valutazionePersonale) {
        if (valutazionePersonale>1 && valutazionePersonale<5)
            this.valutazionePersonale = valutazionePersonale;
        else
            throw new IllegalArgumentException("la valutazione deve essere compresa tra 1 e 5");

    }

    public StatoVisione getStatoVisione() {
        return StatoVisione;
    }

    public void setStatoVisione(StatoVisione statoVisione) {
        StatoVisione = statoVisione;
    }

    public Genere getGenere() {
        return Genere;
    }

    public void setGenere(Genere genere) {
        Genere = genere;
    }
}
