package vn.edu.hcmuaf.fit.controller;
import vn.edu.hcmuaf.fit.model.UserGoogle;
import vn.edu.hcmuaf.fit.google.GoogleUtils;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GoogleLogin", value = "/GoogleLogin")
public class GoogleLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            request.setAttribute("error", "Đăng nhập thất bại!");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        } else {
//            String accessToken = GoogleUtils.getToken(code);
//            UserGoogle userGoogle = GoogleUtils.getUserInfo(accessToken);
//            HttpSession session = request.getSession(true);
//            session.setAttribute("oAuth", userGoogle);
            response.sendRedirect("Home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
