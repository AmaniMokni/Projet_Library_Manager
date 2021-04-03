package com.ensta.librarymanager.service;


import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;

public class LivreServiceImpl implements LivreService{

    /* pattern Singleton*/
    private static LivreServiceImpl instance = new LivreServiceImpl();
    private LivreServiceImpl() { }
    public static LivreServiceImpl getInstance() {
        return instance;
    }

    /* Methods */
    @Override
    public List<Livre> getList() throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livreDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livres;
    }

    @Override
    public List<Livre> getListDispo() throws ServiceException{
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> listeDispo = new ArrayList<>();
        List<Livre> listeLivres = getList() ;
        for (int i = 0; i<listeLivres.size(); i++) {
            if (empruntService.isLivreDispo(listeLivres.get(i).getId())) {
                listeDispo.add(listeLivres.get(i));
            }
        }
        return listeDispo;
    }

    @Override
    public Livre getById(int id) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        Livre livre = new Livre();
        try {
            livre = livreDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livre;

    }

    @Override
    public int create(String titre, String auteur, String isbn) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        int i = -1;
        try{
            if(titre != null && titre.length() != 0) {
                i = livreDao.create(titre, auteur, isbn);
            }
            else {
                throw new ServiceException("Vous devez choisir un titre avant de creer le livre !");
            }
        }catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return i;

    }

    @Override
    public void update(Livre livre) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        try{
            if(livre.getTitre() != null && livre.getTitre().length() != 0) {
                livreDao.update(livre);
            }
            else {
                throw new ServiceException("Vous devez choisir un titre avant de mettre à jour le livre !");
            }
        }catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }

    }

    @Override
    public void delete(int id) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        try {
            livreDao.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }

    }

    @Override
    public int count() throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        int c = -1;
        try {
            c = livreDao.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return c ;
    }

}



