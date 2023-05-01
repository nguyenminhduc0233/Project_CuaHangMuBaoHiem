package vn.edu.hcmuaf.fit.controller;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.UserGoogle;
import vn.edu.hcmuaf.fit.google.GoogleUtils;
import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "GoogleLogin", value = "/GoogleLogin")
public class GoogleLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "Đăng nhập thất bại!");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            UserGoogle userGoogle = GoogleUtils.getUserInfo(accessToken);

            try {
                if(!CustomerService.checkLogin(userGoogle.getId(), CustomerService.toMD5(userGoogle.getId()))) {
                    CustomerService.addCustomer(userGoogle.getId(), CustomerService.toMD5(userGoogle.getId()), userGoogle.getName(), userGoogle.getEmail(), 2);
                    session.setAttribute("tendangnhap", userGoogle.getId());
                    response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
                }
                else{
                    session.setAttribute("tendangnhap", userGoogle.getId());
                    response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
