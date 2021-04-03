package com.ensta.librarymanager.modele;
import java.time.LocalDate;


public class Emprunt {
    private int id;
    private Membre membre;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
public Emprunt(){}
public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour){
    this.id = id;
    this.membre = membre;
    this.livre = livre;
    this.dateEmprunt = dateEmprunt;
    this.dateRetour = dateRetour;
}

/*Les accesseurs*/

    public int getId() {
        return id;
    }

    public Membre getMembre() {
        return membre;
    }

    public Livre getLivre() {
        return livre;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }
    /*Les mutateurs*/

    public void setId(int id) {
        this.id = id;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    @Override
    public String toString() {
        return "Emprunt{" +
                "id=" + id +
                ", idMembre=" + membre +
                ", idLivre=" + livre +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + dateRetour +
                '}';
    }
}
