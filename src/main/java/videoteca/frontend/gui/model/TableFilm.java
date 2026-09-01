package videoteca.frontend.gui.model;

import videoteca.backend.gRPC.Film;
import videoteca.frontend.gui.model.button.DeleteButton;
import videoteca.frontend.gui.model.button.EditButton;
import videoteca.frontend.gui.model.button.TableButtonEditor;
import videoteca.frontend.gui.model.button.TableButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class TableFilm extends JPanel implements Observer {

    CatalogoFilmSubject catalogoFilmSubject;

    JTable table;
    DefaultTableModel model;
    private EditButton editButton;
    private DeleteButton deleteButton;

    public TableFilm(CatalogoFilmSubject catalogoFilmSubject,FinestraMediator mediator){
        this.catalogoFilmSubject = catalogoFilmSubject;
        catalogoFilmSubject.attach(this);
        this.editButton = new EditButton();
        this.deleteButton = new DeleteButton();

        String[] row = {"ID", "Titolo","Regista", "Anno Di Uscita","Genere","Valutazione","Stato Visione","Edit","Delete"};
        model = new DefaultTableModel(row,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7 || column == 8;
            }

        };




        this.table = new JTable(model);
        TableColumn colonnaEdit = table.getColumnModel().getColumn(7);
        TableColumn colonnaDelete = table.getColumnModel().getColumn(8);

        colonnaEdit.setCellRenderer(new TableButtonRenderer(this.editButton));
        colonnaEdit.setCellEditor(new TableButtonEditor(this.editButton,table,()->{
            int riga = table.getSelectedRow();
            if (riga != -1){
                int idFilm = (int) table.getValueAt(riga,0);
                String titolo = (String) table.getValueAt(riga,1);
                String regista = (String) table.getValueAt(riga,2);
                int annoDiUScita = (int) table.getValueAt(riga,3);
                String genere = (String) table.getValueAt(riga,4);
                int valutazionePersonale = (int) table.getValueAt(riga,5);
                String statoVisione = (String) table.getValueAt(riga,6);

                Film filmSelezionato = Film.newBuilder()
                        .setId(idFilm)
                        .setTitolo(titolo)
                        .setAnnoUscita(annoDiUScita)
                        .setRegista(regista)
                        .setGenere(genere)
                        .setStatoVisione(statoVisione)
                        .setValutazione(valutazionePersonale)
                        .build();


                mediator.apriFinestra(filmSelezionato);

            }
        }));
        colonnaDelete.setCellRenderer(new TableButtonRenderer(this.deleteButton));
        colonnaDelete.setCellEditor(new TableButtonEditor(this.deleteButton,table,()->{
            int riga = table.getSelectedRow();
            if (riga != -1){
                int idFilm = (int) table.getValueAt(riga,0);
                mediator.deleteFilm(idFilm);

            }



        }));




        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(table),BorderLayout.CENTER);





    }

    @Override
    public void update() {

        this.model.setRowCount(0);

        List<Film> films = catalogoFilmSubject.getFilms();
        if(model != null) {
            for (Film f : films) {
                Object[] o = {
                        f.getId(),
                        f.getTitolo(),
                        f.getRegista(),
                        f.getAnnoUscita(),
                        f.getGenere(),
                        f.getValutazione(),
                        f.getStatoVisione(),
                        null,
                        null

                };
                model.addRow(o);
            }
            model.fireTableDataChanged();
        }else{
            System.out.println("catalogo FIlm VUOTO");
        }




    }
}
