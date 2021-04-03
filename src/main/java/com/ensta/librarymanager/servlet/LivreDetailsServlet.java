package com.ensta.librarymanager.servlet;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;

public class LivreDetailsServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            request.setAttribute("livre",livreService.getById(id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("listeEmprunts",empruntService.getListCurrentByLivre(id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        int id = Integer.parseInt(request.getParameter("id"));
        Livre livre = new Livre(id,request.getParameter("titre"),request.getParameter("auteur"),request.getParameter("isbn"));
        try {
            livreService.update(livre);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        doGet(request, response);
    }

}



