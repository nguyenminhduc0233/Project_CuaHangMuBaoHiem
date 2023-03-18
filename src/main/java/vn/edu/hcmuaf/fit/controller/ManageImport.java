package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.ImportProduct;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageImport", value = "/ManageImport")
public class ManageImport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ImportProduct> list = ProductService.getProductInImport();
        request.setAttribute("list",list);
        request.getRequestDispatcher("ImportProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
