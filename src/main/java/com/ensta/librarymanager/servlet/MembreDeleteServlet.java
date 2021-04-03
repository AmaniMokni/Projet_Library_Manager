package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.*;

public class MembreDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            request.setAttribute("membre",membreService.getById(id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isDeleted = false;
        try {
            membreService.delete(id);
            isDeleted = true;
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
        if(isDeleted) {
            response.sendRedirect("membre_list");
        }else {
            response.sendRedirect("error_500");
        }


    }

}


