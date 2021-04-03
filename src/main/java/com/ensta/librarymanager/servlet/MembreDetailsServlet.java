package com.ensta.librarymanager.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.*;


public class MembreDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            request.setAttribute("membre",membreService.getById(id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("listeEmprunts",empruntService.getListCurrentByMembre(id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        int id = Integer.parseInt(request.getParameter("id"));
        Membre membre = new Membre(id,request.getParameter("nom"),request.getParameter("prenom"),request.getParameter("adresse"),request.getParameter("email"),request.getParameter("telephone"),Abonnement.fromString(request.getParameter("abonnement")));
        try {
            membreService.update(membre);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        doGet(request, response);
    }
}


