package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DetailLog", value = "/detail-log")
public class DetailLog extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() == 0) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else if (!CustomerService.allow_access("Quản lý bình luận", customer.getPermission())) {
                request.setAttribute("error", "Bạn không có chức vụ trong trang web này. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            String user = request.getParameter("userName");
            String indexPage = request.getParameter("index");
            int index = Integer.parseInt(indexPage);
            int pre = index - 1;
            int next = index + 1;

//            List<Integer> list = ProductService.getListCommentByProduct(id_product, index);

            int n = ProductService.getTotalLog(user);
            int endPage = n/8;
            if(n % 8 != 0){
                endPage++;
            }

            request.setAttribute("index", index);
            request.setAttribute("pre", pre);
            request.setAttribute("next", next);
            request.setAttribute("endP", endPage);

            request.setAttribute("detail_log", ProductService.getListLogtByCustomer(user, index));
            request.setAttribute("username", user);
            request.getRequestDispatcher("detailLog.jsp").forward(request,response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
