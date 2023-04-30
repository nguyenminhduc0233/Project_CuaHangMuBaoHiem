package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "fix-bill", value = "/fix-bill")
public class FixBill extends HttpServlet {
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
            } else if (!CustomerService.allow_access("Sửa hóa đơn", customer.getPermission())) {
                response.sendRedirect("/Project_CuaHangMuBaoHiem_war/list-bill");;
                return;
            }
            int id = Integer.parseInt(request.getParameter("id"));
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String status = request.getParameter("status");
            ProductService.updateBill(id,address,phone,status);
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/list-bill");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
