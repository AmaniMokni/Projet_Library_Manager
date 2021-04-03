package com.ensta.librarymanager.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;

public class LivreDaoImpl implements LivreDao{
    private static LivreDaoImpl instance;
    private LivreDaoImpl() { }
    public static LivreDaoImpl getInstance() {
        if(instance == null) {
            instance = new LivreDaoImpl();
        }
        return instance;
    }
    private static final String SELECT_ALL_QUERY = "SELECT id, titre, auteur, isbn FROM livre;";
    private static final String SELECT_ONE_QUERY = "SELECT id, titre, auteur, isbn FROM livre WHERE id=?;";
    private static final String CREATE_QUERY = "INSERT INTO livre (titre, auteur, isbn) VALUES (?, ?,?);";
    private static final String UPDATE_QUERY = "UPDATE livre SET titre=?, auteur=?, isbn=? WHERE id=?;";
    private static final String DELETE_QUERY = "DELETE FROM livre WHERE id=?;";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM livre;";

    @Override
    public List<Livre> getList() throws DaoException{
        List<Livre> livres = new ArrayList<Livre>();
        try (Connection connection =ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ){
            while(res.next()) {
                Livre l = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
                livres.add(l);
            }
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des livres", e);
        }
        return livres;
    }
    @Override
    public Livre getById(int id) throws DaoException{
        Livre livre = new Livre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                livre.setId(res.getInt("id"));
                livre.setTitre(res.getString("titre"));
                livre.setAuteur(res.getString("auteur"));
                livre.setIsbn(res.getString("isbn"));
            }
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation du livre: id=" + id, e);
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
        return livre;


    }

    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException{
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, isbn);
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if(res.next()){
                id = res.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la creation du livre: ", e);
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
    public void update(Livre livre) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la mise a jour du livre: ", e);
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
            throw new DaoException("Un probleme est survenu lors de la suppression du livre: ", e);
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
    public int count() throws DaoException {
        int nb = -1;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY);
             ResultSet res = preparedStatement.executeQuery();
        ) {
            while (res.next()) {
                nb = res.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Un probleme est survenu lors de la recuperation du nombre total de livres", e);
        }
        return nb;
    }
}
