package com.ensta.librarymanager.modele;

public class Livre {
        private int id;
        private String titre;
        private String auteur;
        private String isbn;

        public Livre(){}
        public Livre(int id, String titre, String auteur, String isbn){
                this.id = id;
                this.titre = titre;
                this.auteur = auteur;
                this.isbn = isbn;
        }
/*Les accesseurs*/
        public int getId() {
                return id;
        }

        public String getTitre() {
                return titre;
        }

        public String getAuteur() {
                return auteur;
        }

        public String getIsbn() {
                return isbn;
        }
  /* Les mutateurs */

        public void setId(int id) {
                this.id = id;
        }

        public void setTitre(String titre) {
                this.titre = titre;
        }

        public void setAuteur(String auteur) {
                this.auteur = auteur;
        }

        public void setIsbn(String isbn) {
                this.isbn = isbn;
        }

        @Override
        public String toString() {
                return "Livre{" +
                        "id=" + id +
                        ", titre='" + titre + '\'' +
                        ", auteur='" + auteur + '\'' +
                        ", isbn='" + isbn + '\'' +
                        '}';
        }
}
