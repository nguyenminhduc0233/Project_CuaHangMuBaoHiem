package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.LogService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "detail-customer", value = "/detail-customer")
public class DetailCustomer extends HttpServlet {
    String name = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        String nameCus = request.getParameter("nameCus");
        Customer cus = null;
        try {
            Log log = new Log(Log.INFO, username, this.name, "", 0);
            cus = CustomerService.customer(username);
            if (cus == null || cus.getPermission() != 0&&!CustomerService.allow_service(CustomerService.id_access("quản lý khách hàng",cus.getPermission(),"EDIT"))) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);

                log.setSrc(this.name + "LOGIN FALSE");
                log.setContent("THIS ACCOUNT is INVALID: Username - " + username);
                log.setLevel(Log.WARNING);
                return;
            }
            int id_Cus = Integer.parseInt(request.getParameter("id"));
            try {
                Customer customer = ProductService.getCustomer(id_Cus);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("fix_customer.jsp").forward(request, response);

                log.setSrc(this.name + "EDIT CUSTOMER");
                log.setContent("EDIT CUSTOMER " + nameCus + " AT: Username - "  + username);
                LogService.log(log);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
