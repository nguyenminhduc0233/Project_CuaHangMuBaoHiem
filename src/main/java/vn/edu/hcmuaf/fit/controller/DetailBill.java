package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.LogService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "detail-bill", value = "/detail-bill")
public class DetailBill extends HttpServlet {
    String name = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");

        Customer customer = null;
        try {
            Log log = new Log(Log.INFO, username, this.name, "", 0);
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() != 0&&!CustomerService.allow_service(CustomerService.id_access("quản lý hóa đơn",customer.getPermission(),"VIEW"))) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);

                log.setSrc(this.name + "LOGIN FALSE");
                log.setContent("THIS ACCOUNT is INVALID: Username - " + username);
                log.setLevel(Log.WARNING);
                return;
            }
            int id_bill = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("detail_bill", ProductService.getBill(id_bill));
            request.getRequestDispatcher("detail_bill_manager.jsp").forward(request,response);

            log.setSrc(this.name + "EDIT BILL");
            log.setContent("EDIT BILL " + id_bill + " AT: Username - "  + username);
            LogService.log(log);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
