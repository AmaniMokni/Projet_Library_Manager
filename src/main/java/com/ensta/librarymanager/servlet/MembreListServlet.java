package com.ensta.librarymanager.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.MembreServiceImpl;
import java.io.IOException;

public class MembreListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            request.setAttribute("listeMembres",membreService.getList());
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_list.jsp");
        dispatcher.forward(request, response);
    }

}


