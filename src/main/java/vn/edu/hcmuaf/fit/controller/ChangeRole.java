package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

@WebServlet(name = "change_role", value = "/change_role")
public class ChangeRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession[] session = HttpSession.class.getEnumConstants();
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            // Xóa phiên đăng nhập của người dùng
//            session.invalidate();
//        }
//        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
//        String permission = request.getParameter("name");
//        int per = CustomerService.getPermissonByRole(permission);
//        int id = Integer.parseInt(request.getParameter("id"));
//        CustomerService.updatePermission(id,per);
//
//        Object active = session.getAttribute("dmanager");
//        PrintWriter printWriter = response.getWriter();
//        if(active!=null){
//            printWriter.print(true);
//        }else{
//            printWriter.print(false);
//        }
//            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/user_hierarchy");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
