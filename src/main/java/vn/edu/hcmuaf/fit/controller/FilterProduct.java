package vn.edu.hcmuaf.fit.controller;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.LogService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FilterProduct", value = "/filter-product")
public class FilterProduct extends HttpServlet {
    String name = "AUTH";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Log log = new Log(Log.INFO, username, this.name, "", 0);

        String[] price = request.getParameterValues("price");
        String[] star = request.getParameterValues("star");
        request.setAttribute("list",ProductService.filterProduct(price,star));
        request.getRequestDispatcher("shop.jsp").forward(request,response);

        log.setSrc(this.name + "Filter");
        log.setContent("FILTER PRODUCT BY" + price + "," + star + "SUCCESS: Username - "  + username);
        LogService.log(log);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
