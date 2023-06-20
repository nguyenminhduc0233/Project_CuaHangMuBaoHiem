package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.LogService;
import vn.edu.hcmuaf.fit.service.SlideShowService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RemoveBanner", value = "/RemoveBanner")
public class RemoveBanner extends HttpServlet {
    String namee = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        Log log = new Log(Log.INFO, username, this.namee, "", 0);
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() != 0 && !CustomerService.allow_service(CustomerService.id_access("quản lý trang chủ", customer.getPermission(), "DELETE"))) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);

                log.setSrc(this.namee + "DELETE BANNER FALSE");
                log.setContent("DELETE BANNER FALSE: Username - " + username);
                log.setLevel(Log.ERROR);
                return;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        int id = Integer.parseInt(request.getParameter("id"));
        SlideShowService.getInstance().removeBanner(id);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ManageHome");

        log.setSrc(this.namee + "DELETE BANNER");
        log.setContent("DELETE BANNER: ID - " + id + " SUCCESS: Admin - "  + username);
        log.setLevel(Log.DANGER);
        LogService.log(log);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
