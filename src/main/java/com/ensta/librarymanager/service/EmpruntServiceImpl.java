package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;

public class EmpruntServiceImpl implements EmpruntService {

    /* pattern Singleton*/
    private static EmpruntServiceImpl instance = new EmpruntServiceImpl();
    private EmpruntServiceImpl() { }
    public static EmpruntServiceImpl getInstance() {
        return instance;
    }

    /* Methods */
    @Override
    public List<Emprunt> getList() throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;

    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrent();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public Emprunt getById(int id) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{

        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            if (!isLivreDispo(idLivre)) {
                throw new ServiceException("Livre pas disponible");
            }else if(!isEmpruntPossible(membreService.getById(idMembre))) {
                throw new ServiceException("Nombre d'emprunt max atteint");
            }else {
                empruntDao.create(idMembre,idLivre,dateEmprunt);
            }
        }catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }

    @Override
    public void returnBook(int id) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        try {
            Emprunt emprunt = empruntDao.getListCurrentByLivre(id).get(0);
            emprunt.setDateRetour(LocalDate.now());
            empruntDao.update(emprunt);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new ServiceException("Pas d'emprunt associe a ce livre");
        }

    }

    @Override
    public int count() throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        int c = -1;
        try {
            c = empruntDao.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return c ;

    }

    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        try {
            List<Emprunt> livre = empruntDao.getListCurrentByLivre(idLivre);
            if(livre==null || livre.size() == 0) {
                return true;
            }else {
                for(Emprunt EM:livre)
                {
                    if (EM.getDateRetour()==null)
                        return false;
                }
                return true;
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return false;

    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        try {
            List<Emprunt> l = empruntDao.getListCurrentByMembre(membre.getId());
            if(l==null || (membre.getAbonnement()!=null && l.size() < membre.getAbonnement().getValue())) {
                return true;
            }else {
                return false;
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return false;

    }

}


