package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Bill;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.LogService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "find-bill", value = "/find-bill")
public class FindBill extends HttpServlet {
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

                log.setSrc(this.name + "INVALID ACCOUNT");
                log.setContent(username + " IS NOT ADMIN");
                log.setLevel(Log.WARNING);
                return;
            }
            int id_bill = Integer.parseInt(request.getParameter("text"));
            request.setAttribute("id",id_bill);
            List<Bill> list = new ArrayList<Bill>();
            list.add(ProductService.getBill(id_bill));
            request.setAttribute("list", list);
            long sales = 0;
            int count = 0;
            request.setAttribute("sales", sales);
            request.setAttribute("count", count);
            request.setAttribute("error", null);

            String indexPage = request.getParameter("index");
            if((indexPage==null)){
                indexPage="1";
            }
            int index = Integer.parseInt(indexPage);
            int pre = index - 1;
            int next = index + 1;

            int n = 1;
            int endPage = n/10;
            if(n % 10 != 0){
                endPage++;
            }

            request.setAttribute("index", index);
            request.setAttribute("pre", pre);
            request.setAttribute("next", next);
            request.setAttribute("endP", endPage);

            request.getRequestDispatcher("bill_manager.jsp").forward(request, response);

            log.setSrc(this.name + " FIND BILL");
            log.setContent("FIND BILL: ID - " + id_bill + " SUCCESS: Admin - " + username);
            LogService.log(log);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
