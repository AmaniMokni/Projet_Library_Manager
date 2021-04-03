package com.ensta.librarymanager.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.service.*;

public class EmpruntReturnServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try {
            request.setAttribute("emprunts",empruntService.getListCurrent());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            empruntService.returnBook(empruntService.getById(id).getLivre().getId());
            response.sendRedirect("emprunt_list");

        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();


        }

    }
}



