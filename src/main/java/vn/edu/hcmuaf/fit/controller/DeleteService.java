package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "delete-service", value = "/delete-service")
public class DeleteService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String service  =  request.getParameter("service");
        if(!CustomerService.checkContainService(service)){
            request.setAttribute("notification",CustomerService.deletePermissionManager(service));
            request.setAttribute("color","#FF0000");
        }else{
            String notification = CustomerService.deletePermissionManager(service);
            request.setAttribute("notification",notification);
            request.setAttribute("color","#00CC00");
        }
        List<Integer> list = CustomerService.getListIdPermission();
        request.setAttribute("list",list);
        request.getRequestDispatcher("manager-permission.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
