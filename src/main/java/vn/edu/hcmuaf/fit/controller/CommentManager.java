package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CommentManager", value = "/CommentManager")
public class CommentManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("tendangnhap");
        int id_pro = Integer.parseInt(request.getParameter("id"));
        int id_comt = Integer.parseInt(request.getParameter("id_comt"));

        if(username == null){
            request.setAttribute("error ", "Vui lòng đăng nhập để sử dụng chức năng này!");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } else{
            ProductService ps = new ProductService();
            ps.deleteComment(id_comt);

            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/detail?id="+id_pro);

    }}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
