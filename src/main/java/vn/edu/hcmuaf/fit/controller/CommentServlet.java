package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import vn.edu.hcmuaf.fit.model.Comment;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "CommentServlet", value = "/CommentServlet")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String username = (String) request.getSession().getAttribute("tendangnhap");
        int id_cus = ProductService.getIdCusByUserName(username);
        int id_pro = Integer.parseInt(request.getParameter("id"));
        String content = request.getParameter("mess");
        int star = Integer.parseInt(request.getParameter("star"));
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        int id_comt = ProductService.getAllComment().size();
        System.out.println(id_pro);
        System.out.println();
        if (username == null) {
            request.setAttribute("error ", "Vui lòng đăng nhập để sử dụng chức năng này!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        id_comt++;
        ProductService ps = new ProductService();
        ps.addComment(id_cus, id_pro, content, star, date, id_comt, 1);

        Gson gson = new Gson();
        List<Comment> comts =  ProductService.getAllComment();
        System.out.println(comts);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateStr = sdf.format(date);

        // Thêm tên người dùng vào đối tượng JSON được trả về
        String responseText = "{ \"comments\": " + gson.toJson(comts) + ", \"date\": \"" + dateStr + "\", \"username\": \"" + username + "\" }";
        out.print(responseText);
        out.flush();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
