package com.ensta.librarymanager.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import java.io.IOException;

public class EmpruntListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        if(request.getParameter("show")==null || !request.getParameter("show").equals("all")) {
            try {
                request.setAttribute("listeEmprunts",empruntService.getListCurrent());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }else {
            try {
                request.setAttribute("listeEmprunts",empruntService.getList());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
        dispatcher.forward(request, response);
    }

}


