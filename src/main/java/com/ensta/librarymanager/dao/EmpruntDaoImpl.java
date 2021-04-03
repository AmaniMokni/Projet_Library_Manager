package com.ensta.librarymanager.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao {

    private static EmpruntDaoImpl instance;

    private EmpruntDaoImpl() { }

    public static EmpruntDaoImpl getInstance() {
        if(instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }
    private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour \n"
            + "FROM emprunt AS e \n"
            + "INNER JOIN membre ON membre.id = e.idMembre \n "
            + "INNER JOIN livre ON livre.id = e.idLivre \n"
            + "ORDER BY dateRetour DESC;";
    private static final String SELECT_ALL_Current_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" +
            "dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "WHERE dateRetour IS null;";
    private static final String SELECT_ALL_CurrentByMembre_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" +
            "dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "WHERE dateRetour IS null AND membre.id = ?;";
    private static final String SELECT_ALL_CurrentByLivre_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" +
            "dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "WHERE dateRetour IS null AND livre.id = ?;";
    private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,\r\n" +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\r\n" +
            "dateRetour\r\n" +
            "FROM emprunt AS e\r\n" +
            "INNER JOIN membre ON membre.id = e.idMembre\r\n" +
            "INNER JOIN livre ON livre.id = e.idLivre\r\n" +
            "WHERE e.id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";
    @Override
    public List<Emprunt> getList() throws DaoException{
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        try (Connection connection =ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                Abonnement a = Abonnement.fromString(res.getString("abonnement"));

                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
                        res.getString("email"), res.getString("telephone"), a);

                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

                LocalDate dateE = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateR = (res.getDate("dateRetour") == null) ? null : res.getDate("dateRetour").toLocalDate();

                Emprunt e = new Emprunt(res.getInt("id"),m, l, dateE, dateR);

                emprunts.add(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
        }
        return emprunts;

    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException{
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        try (Connection connection =ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_Current_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                Abonnement a = Abonnement.fromString(res.getString("abonnement"));

                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
                        res.getString("email"), res.getString("telephone"), a);

                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

                LocalDate dateE = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateR = (res.getDate("dateRetour") == null) ? null : res.getDate("dateRetour").toLocalDate();

                Emprunt e = new Emprunt(res.getInt("id"),m, l, dateE, dateR);

                emprunts.add(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts pas encore rendus", e);
        }
        return emprunts;

    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection =ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_CurrentByMembre_QUERY);
            preparedStatement.setInt(1, idMembre);
            res = preparedStatement.executeQuery();
            while(res.next()) {
                Abonnement a = Abonnement.fromString(res.getString("abonnement"));

                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
                        res.getString("email"), res.getString("telephone"), a);

                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

                LocalDate dateE = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateR = (res.getDate("dateRetour") == null) ? null : res.getDate("dateRetour").toLocalDate();

                Emprunt e = new Emprunt(res.getInt("id"),m, l, dateE, dateR);

                emprunts.add(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts pas encore rendus pour un membre donne", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;

    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection =ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_CurrentByLivre_QUERY);
            preparedStatement.setInt(1, idLivre);
            res = preparedStatement.executeQuery();
            while(res.next()) {
                Abonnement a = Abonnement.fromString(res.getString("abonnement"));

                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
                        res.getString("email"), res.getString("telephone"), a);

                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

                LocalDate dateE = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateR = (res.getDate("dateRetour") == null) ? null : res.getDate("dateRetour").toLocalDate();

                Emprunt e = new Emprunt(res.getInt("id"),m, l, dateE, dateR);
                emprunts.add(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts pas encore rendus pour un livre donne", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;

    }

    @Override
    public Emprunt getById(int id) throws DaoException{

        Emprunt emprunt = new Emprunt();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                Abonnement a = Abonnement.fromString(res.getString("abonnement"));
                Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
                        res.getString("email"), res.getString("telephone"), a);
                Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
                LocalDate dateE = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateR = (res.getDate("dateRetour") == null) ? null : res.getDate("dateRetour").toLocalDate();
                emprunt.setId(res.getInt("idEmprunt"));
                emprunt.setMembre(m);
                emprunt.setLivre(l);
                emprunt.setDateEmprunt(dateE);
                emprunt.setDateRetour(dateR);
            }
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de l'emprunt: id=" + id, e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunt;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);

            preparedStatement.setInt(1, idMembre);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.setObject(3, dateEmprunt);
            preparedStatement.setObject(4, null);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la création" ,e);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Emprunt emprunt) throws DaoException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, emprunt.getMembre().getId());
            preparedStatement.setInt(2, emprunt.getLivre().getId());
            preparedStatement.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, (emprunt.getDateRetour() == null) ? null : Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5, emprunt.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la mise a jour de l'emprunt: ", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int count() throws DaoException{
        int nb = -1;
        try (Connection connection =ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                nb = res.getInt("count") ;
            }
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la recuperation du nombre total des emprunts", e);
        }
        return nb ;

    }

}
