package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.model.Product;
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

@WebServlet(name = "ProductDetail", value = "/detail")
public class ProductDetail extends HttpServlet {
    String namee = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Log log = new Log(Log.INFO, username, this.namee, "", 0);
        String name = request.getParameter("name");

        String id = request.getParameter("id");
        if(id != null) {
            Product product = null;
            try {
                product = ProductService.getProduct(Integer.parseInt(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("product",product);
            request.getRequestDispatcher("detail.jsp").forward(request,response);
            System.out.println(product);

            log.setSrc(this.namee + "VIEW");
            log.setContent("VIEW PRODUCT " + name + ": Username - "  + username);

        }else
            response.sendError(404,"Product not found");

        LogService.log(log);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
