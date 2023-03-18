package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CommentService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CommentServlet", value = "/CommentServlet")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CommentService cs = new CommentService();

        String username = (String) request.getSession().getAttribute("tendangnhap");
        String id_cus = ProductService.getIdCusByUserName(username);
        String id_pro = request.getParameter("id");
        String content = request.getParameter("mess");
        int star =Integer.parseInt(request.getParameter("star"));
        long millis=System.currentTimeMillis();   java.sql.Date date=new java.sql.Date(millis);
        String avatar = request.getParameter("avatar");
        int id_comt = ProductService.getAllComment().size();
        if(username == null){
            request.setAttribute("error ", "Vui lòng đăng nhập để sử dụng chức năng này!");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } else{
            id_comt++;
            ProductService ps = new ProductService();
            ps.addComment(id_cus, id_pro, content, star, date, avatar, id_comt);
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/detail?id="+id_pro);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
