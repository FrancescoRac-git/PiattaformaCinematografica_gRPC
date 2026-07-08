package videoteca.backend.model;



public class Film {

    private final String titolo;
    private final String regista;
    private final int annoDiUscita;
    private final int valutazionePersonale;
    private final StatoVisione statoVisione;
    private final Genere genere;
    private final int id;

    private Film(Builder builder) {
        this.titolo = builder.titolo;
        this.regista = builder.regista;
        this.annoDiUscita = builder.annoDiUscita;
        this.valutazionePersonale= builder.valutazione;
        this.statoVisione = builder.statoVisione;
        this.genere = builder.genere;
        this.id = builder.id;

    }

    public static class Builder {
        private final String titolo;
        private final String regista;

        private int id =0;
        private int annoDiUscita=0;
        private Genere genere = null;
        private int valutazione = 0;
        private StatoVisione statoVisione = null;

        public Builder(String titolo, String regista) {
            this.titolo = titolo;
            this.regista = regista;
        }

        public Builder id(int val) {
            this.id = val;
            return this;
        }

        public Builder annoUscita(int val) {
            this.annoDiUscita = val;
            return this;
        }

        public Builder genere(Genere val) {
            this.genere = val;
            return this;
        }

        public Builder valutazione(int val) {
            this.valutazione = val;
            return this;
        }

        public Builder statoVisione(StatoVisione val) {
            this.statoVisione = val;
            return this;
        }

        public Film build() {
            if (valutazione < 0 || valutazione > 5) {
                throw new IllegalArgumentException("Errore: La valutazione deve essere compresa tra 0 e 5");
            }
            return new Film(this);
        }
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

    public StatoVisione getStatoVisione() {
        return statoVisione;
    }

    public Genere getGenere() {
        return genere;
    }

    public int getId() {
        return id;
    }
}