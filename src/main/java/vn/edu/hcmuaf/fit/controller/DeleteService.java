package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "delete-service", value = "/delete-service")
public class DeleteService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() == 0) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else if (customer.getPermission() <2) {
                request.setAttribute("error", "Bạn không có chức vụ trong trang web này. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
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
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
