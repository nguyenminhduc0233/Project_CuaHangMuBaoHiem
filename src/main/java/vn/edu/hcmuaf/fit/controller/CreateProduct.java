package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.LogService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "createProduct", value = "/createProduct")
public class CreateProduct extends HttpServlet {
    String name = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        Log log = new Log(Log.INFO, name, this.name, "", 0);
        try {
            customer = CustomerService.customer(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(customer==null||customer.getId_customer()!=0&&!CustomerService.allow_service(CustomerService.id_access("quản lý sản phẩm",customer.getPermission(),"CREATE"))){
            request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
            request.getRequestDispatcher("login.jsp").forward(request, response);

            log.setSrc(this.name + "INVALID ACCOUNT");
            log.setContent(username + " IS NOT ADMIN");
            log.setLevel(Log.WARNING);
            return;
        }else{
            response.sendRedirect("forms.jsp");

            log.setSrc(this.name + "CREATE PRODUCT");
            log.setContent("CREATE PRODUCT SUCCESS: Admin - " + username);
        }
        LogService.log(log);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
