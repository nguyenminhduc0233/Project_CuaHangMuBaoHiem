package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddDetailInDetailProduct", value = "/AddDetailInDetailProduct")
public class AddDetailInDetailProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() != 0||!CustomerService.allow_service(CustomerService.id_access("quản lý sản phẩm",customer.getPermission(),"CREATE"))) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            int id = Integer.parseInt(request.getParameter("id"));
            String size = request.getParameter("size");
            String color = request.getParameter("color");
            String quantity = request.getParameter("quantity");
            int iddp = 0;
            if (ProductService.checkDBContainSizeColor(id, size, color)) {
                iddp = ProductService.getIdDetailProductByCS(id, size, color);
                ProductService.updateSizeColorById(iddp, quantity);
            } else {
                ProductService.insertDetailProduct(id, size, color, quantity);
            }
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/DetailProduct?id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
