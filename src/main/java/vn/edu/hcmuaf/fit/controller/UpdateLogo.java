package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.LogService;
import vn.edu.hcmuaf.fit.service.SlideShowService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpdateLogo", value = "/UpdateLogo")
public class UpdateLogo extends HttpServlet {
    String namee = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            Log log = new Log(Log.INFO, username, this.namee, "", 0);
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() == 0) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);

                log.setSrc(this.namee + "LOGIN FALSE");
                log.setContent("THIS ACCOUNT is INVALID: Username - " + username);
                log.setLevel(Log.WARNING);
                return;
            } else if (customer.getPermission() > 2) {
                request.setAttribute("error", "Bạn không có chức vụ trong trang web này. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);

                log.setSrc(this.namee + "LOGIN FALSE");
                log.setContent("THIS ACCOUNT is INVALID: Username - " + username);
                log.setLevel(Log.WARNING);
                return;
            }
            int id = Integer.parseInt(request.getParameter("id"));
            String allow = request.getParameter("allow");
            String name = request.getParameter("name");
            SlideShowService.getInstance().updateLogo(id, allow, name);
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ManageHome");

            log.setSrc(this.namee + "UPDATE LOGO");
            log.setContent("UPDATE LOGO " + name + " AT: Username - "  + username);
            LogService.log(log);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
