package com.ensta.librarymanager.servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.*;

public class DashboardServlet extends HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            request.setAttribute("nombre_des_Membres",membreService.getList().size());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("nombre_de_Livres",livreService.getList().size());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("nombre_des_Emprunts",empruntService.getList().size());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("listeEmprunts",empruntService.getListCurrent());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
        dispatcher.forward(request, response);
    }

}


