package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.MembreServiceImpl;

public class MembreAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        int id;
        try {
            id = membreService.create(request.getParameter("nom"),request.getParameter("prenom"),request.getParameter("adresse"),request.getParameter("email"),request.getParameter("telephone"));
            request.setAttribute("id", id);
            response.sendRedirect("membre_details?id=" + request.getAttribute("id"));
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }

}


