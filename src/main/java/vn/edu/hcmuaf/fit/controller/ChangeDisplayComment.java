package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangeDisplayComment", value = "/change-display-comment")
public class ChangeDisplayComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String idpro = request.getParameter("idpro");
        String indexPage = request.getParameter("index");
        int index = Integer.parseInt(indexPage);
        ProductService.changeDisplayComment(id);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/detail-comment?id="+idpro+"&index="+index);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
