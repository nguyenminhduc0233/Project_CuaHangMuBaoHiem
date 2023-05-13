package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.LogService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DetailProduct", value = "/DetailProduct")
public class DetailProduct extends HttpServlet {
    String name = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        String nameProduct = request.getParameter("nameProduct");
        Customer customer = null;
        try {
            Log log = new Log(Log.INFO, username, this.name, "", 0);
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() == 0) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);

                log.setSrc(this.name + "LOGIN FALSE");
                log.setContent("THIS ACCOUNT is INVALID: Username - " + username);
                log.setLevel(Log.WARNING);
                return;
            } else if (!CustomerService.allow_access("Chỉnh sửa sản phẩm",customer.getPermission())) {
                response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ManageProduct");
                return;
            }
            String id = request.getParameter("id");
            String pages = request.getParameter("pages");
            if (id != null) {
                Product product = null;
                try {
                    product = ProductService.getProductFullImage(Integer.parseInt(id));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                request.setAttribute("product", product);
                request.setAttribute("pages", pages);
                request.getRequestDispatcher("detailProduct.jsp").forward(request, response);

                log.setSrc(this.name + "EDIT PRODUCT");
                log.setContent("EDIT PRODUCT " + nameProduct + " AT: Username - "  + username);
            }
//            else
//                response.sendError(404, "Product not found");
            LogService.log(log);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
