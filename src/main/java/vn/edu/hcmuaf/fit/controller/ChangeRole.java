package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet(name = "change_role", value = "/change_role")
public class ChangeRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_cus = Integer.parseInt(request.getParameter("id"));
        CustomerService.change_role(id_cus);
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String username = (String) session.getAttribute("username");
                if (username != null && username.equals(ProductService.getCustomer(id_cus).getUsername())) {
                    session.invalidate();
                    String currentPage = (String) session.getAttribute("currentPage");
                    if (currentPage != null && currentPage.equals("page_to_find")) {
                        // Session is active on the desired page
                    } else {
                        // Session is not active on the desired page
                    }
                } else {
                    // Session is not active
                }
            } else {
                // Session is not active
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/roles");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
