package com.ensta.librarymanager.service;


import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

public class MembreServiceImpl implements MembreService {


    /* pattern Singleton*/
    private static MembreServiceImpl instance = new MembreServiceImpl();
    private MembreServiceImpl() { }
    public static MembreServiceImpl getInstance() {
        return instance;
    }

    /* Methods */
    @Override
    public List<Membre> getList() throws ServiceException{

        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = membreDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membres;

    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        List<Membre> listeMembresEmpruntPossible = new ArrayList<>();
        List<Membre> listeMembres = getList() ;
        for (int i = 0; i<listeMembres.size(); i++) {
            if (empruntService.isEmpruntPossible(listeMembres.get(i))) {
                listeMembresEmpruntPossible.add(listeMembres.get(i));
            }
        }
        return listeMembresEmpruntPossible;
    }

    @Override
    public Membre getById(int id) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        Membre membre = new Membre();
        try {
            membre = membreDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membre;

    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{

        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        int i = -1;
        try{
            if(nom != null && prenom != null && nom.length() != 0 && prenom.length() != 0) {

                i = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
            }
            else {
                throw new ServiceException("La creation d'un membre necessite son nom et son prenom !");
            }
        }catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return i;

    }

    @Override
    public void update(Membre membre) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        try {
            if(membre.getNom() != null && membre.getPrenom() != null && membre.getNom().length() != 0 && membre.getPrenom().length() != 0) {
                membre.setNom(membre.getNom().toUpperCase());
                membreDao.update(membre);
            }
            else {
                throw new ServiceException("La mise a jour d'un membre necessite son nom et son prenom !");
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }

    @Override
    public void delete(int id) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        try {
            membreDao.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }

    @Override
    public int count() throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        int c = -1;
        try {
            c = membreDao.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return c ;
    }

}


