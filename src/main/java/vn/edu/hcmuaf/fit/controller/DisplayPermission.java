package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "permission", value = "/permission")
public class DisplayPermission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        List<Integer> list = CustomerService.getListIdServiceByName(name);
        List<String> listService = new ArrayList<String>();
        listService.add("Tất cả");
        listService.addAll(CustomerService.getListService());
        request.setAttribute("list",list);
        request.setAttribute("listService",listService);
        request.setAttribute("name",name);
        request.getRequestDispatcher("manager-permission.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
