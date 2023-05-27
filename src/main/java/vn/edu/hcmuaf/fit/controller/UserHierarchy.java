package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "user_hierarchy", value = "/user_hierarchy")
public class UserHierarchy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() != 0) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            String indexPage = request.getParameter("index");
            if(indexPage==null){
                indexPage = "1";
            }
            List<Customer> listCus = CustomerService.getListCustomerWithoutAdmin();
            int index = Integer.parseInt(indexPage);
            List<Customer> list = CustomerService.onePageLoadCus(index,listCus);
            int pre = index - 1;
            int next = index + 1;
            int n = listCus.size();
            int endPage = n/10;
            if(n % 10 != 0){
                endPage++;
            }

            request.setAttribute("index", index);
            request.setAttribute("pre", pre);
            request.setAttribute("next", next);
            request.setAttribute("endP", endPage);
        request.setAttribute("list",list);
        request.getRequestDispatcher("user_hierarchy.jsp").forward(request,response);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}