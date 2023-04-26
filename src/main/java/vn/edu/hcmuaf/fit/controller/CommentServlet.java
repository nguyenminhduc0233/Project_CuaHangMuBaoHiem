package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import vn.edu.hcmuaf.fit.model.Comment;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CommentServlet", value = "/CommentServlet")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = (String) request.getSession().getAttribute("tendangnhap");
        int id_cus = ProductService.getIdCusByUserName(username);
        int id_pro = Integer.parseInt(request.getParameter("id"));
        String content = request.getParameter("mess");
        int star =Integer.parseInt(request.getParameter("star"));
        long millis=System.currentTimeMillis();   java.sql.Date date=new java.sql.Date(millis);
        int id_comt = ProductService.getAllComment().size();


        if(username == null){
            request.setAttribute("error ", "Vui lòng đăng nhập để sử dụng chức năng này!");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } else{
            id_comt++;
            ProductService ps = new ProductService();
            ps.addComment(id_cus, id_pro, content, star, date, id_comt, 1);

            Gson gson = new Gson();
            List<Comment> comts = ProductService.getAllComment();

            JsonElement element = gson.toJsonTree(comts, new TypeToken<List<Comment>>(){}.getType());
            JsonArray jsonArray = element.getAsJsonArray();

//            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/detail?id="+id_pro);
            response.setContentType("application/json");
            response.getWriter().println(jsonArray);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public static void main(String[] args){
//        Gson gson = new Gson();
//
//        ArrayList<Comment> comts = (ArrayList<Comment>) ProductService.getAllComment();
//
//        String rs = gson.toJson(comts);
//        System.out.println(rs);
    }
}
