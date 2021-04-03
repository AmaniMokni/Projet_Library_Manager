package com.ensta.librarymanager.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao{
    private static MembreDaoImpl instance;

    private MembreDaoImpl() { }

    public static MembreDaoImpl getInstance() {
        if(instance == null) {
            instance = new MembreDaoImpl();
        }
        return instance;
    }
    private static final String SELECT_ALL_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
    private static final String SELECT_ONE_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM membre WHERE id=?;";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM membre;";
    @Override
    public List<Membre> getList() throws DaoException {
        List<Membre> membres = new ArrayList<Membre>();
        try (Connection connection =ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                Abonnement a = Abonnement.fromString(res.getString("abonnement"));
                Membre l = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), a);
                membres.add(l);
            }
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la recuperation de la liste des membres", e);
        }
        return membres;
    }

    @Override
    public Membre getById(int id) throws DaoException {
        Membre membre = new Membre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                membre.setId(res.getInt("id"));
                membre.setNom(res.getString("nom"));
                membre.setPrenom(res.getString("prenom"));
                membre.setAdresse(res.getString("adresse"));
                membre.setEmail(res.getString("email"));
                membre.setTelephone(res.getString("telephone"));
                membre.setAbonnement(Abonnement.fromString(res.getString("abonnement")));

            }
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la recuperation du membre: id=" + id, e);
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
        return membre;

    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        return create(nom, prenom, adresse, email, telephone, "BASIC");

    }
    public int create(String nom, String prenom, String adresse, String email, String telephone, String abonnement) throws DaoException {
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telephone);
            preparedStatement.setString(6, abonnement);


            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if(res.next()){
                id = res.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la creation du membre: ", e);
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
        return id;
    }

    @Override
    public void update(Membre membre) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setString(6,membre.getAbonnement() !=null ? membre.getAbonnement().toString() : "null");
            preparedStatement.setInt(7, membre.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la mise a jour du membre: ", e);
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
    public void delete(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la suppression du membre: ", e);
        }  finally {
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
             ResultSet res = preparedStatement.executeQuery();)
        {
            while(res.next()) {
                nb = res.getInt("count") ;
            }
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la recuperation du nombre total des membres", e);
        }
        return nb ;

    }
}
