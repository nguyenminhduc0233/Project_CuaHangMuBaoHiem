package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Bill;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;

@WebServlet(name = "sales", value = "/sales")
public class Sales extends HttpServlet {
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
            } else if (!CustomerService.allow_access("Kiểm tra doanh thu, doanh số",customer.getPermission())) {
                response.sendRedirect("/Project_CuaHangMuBaoHiem_war/list-bill");
                return;
            }
        String m = request.getParameter("month");
        String y = request.getParameter("year");
        List<Bill> list_bill = ProductService.getListBill();
        if(m=="" || y==""){
            request.setAttribute("error","error");
            request.setAttribute("list",list_bill);
            long sales = 0;
            int count = 0;
            request.setAttribute("sales",sales);
            request.setAttribute("count",count);
            request.getRequestDispatcher("bill_manager.jsp").forward(request,response);
        }else{

            int month = Integer.parseInt(m);
            int year = Integer.parseInt(y);
            List<Bill> list = ProductService.getBillByDate(month,year);
            long sales = ProductService.totalPriceBill(month,year);
            int count = list.size();
            request.setAttribute("list",list);
            request.setAttribute("sales",sales);
            request.setAttribute("count",count);
            request.getRequestDispatcher("bill_manager.jsp").forward(request,response);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
