package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.service.LogService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Delete", value = "/Delete")
public class Delete extends HttpServlet {
    String namee = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Log log = new Log(Log.INFO, username, this.namee, "", 0);
        String name = request.getParameter("name");

        String id = request.getParameter("delete");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.remove(id);
        request.getSession().setAttribute("cart",cart);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ListProductInCart");

        log.setSrc(this.namee + "REMOVE PRODUCT FROM CART");
        log.setContent("REMOVE " + name + " SUCCESS: Username - "  + username);
        LogService.log(log);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
