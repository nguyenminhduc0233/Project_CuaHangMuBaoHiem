package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChangeDisplayComment", value = "/change-display-comment")
public class ChangeDisplayComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() != 0||!CustomerService.allow_service(CustomerService.id_access("quản lý bình luận",customer.getPermission(),"EDIT"))) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        int id = Integer.parseInt(request.getParameter("id"));
        String idpro = request.getParameter("idpro");
        ProductService.changeDisplayComment(id);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/detail-comment?id="+idpro);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
