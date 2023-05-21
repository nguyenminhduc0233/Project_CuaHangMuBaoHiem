package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "permission", value = "/permission")
public class Permission extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() != 0) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        String name = request.getParameter("name");
        List<Integer> list = CustomerService.getListIdServiceByName(name);
        List<String> listService = new ArrayList<String>();
        listService.addAll(CustomerService.getListService());
        listService.remove(name);
        request.setAttribute("list",list);
        request.setAttribute("listService",listService);
        request.setAttribute("name",name);
        request.getRequestDispatcher("manager-permission.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}