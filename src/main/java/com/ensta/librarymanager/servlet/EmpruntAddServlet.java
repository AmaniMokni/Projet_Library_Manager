package com.ensta.librarymanager.servlet;
import com.ensta.librarymanager.service.*;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ensta.librarymanager.exception.*;


public class EmpruntAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();

        try {
            request.setAttribute("listeMembres", membreService.getListMembreEmpruntPossible());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("listeLivres", livreService.getListDispo());
        } catch (ServiceException e) {
            e.printStackTrace();
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try {
            empruntService.create(Integer.parseInt(request.getParameter("idMembre")),Integer.parseInt(request.getParameter("idLivre")),LocalDate.now());
            response.sendRedirect("emprunt_list");
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }
}


