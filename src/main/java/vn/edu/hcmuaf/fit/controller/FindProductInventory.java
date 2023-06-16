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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "find-product-inventory", value = "/find-product-inventory")
public class FindProductInventory extends HttpServlet {
    String name = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            Log log = new Log(Log.INFO, username, this.name, "", 0);
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() != 0&&!CustomerService.allow_service(CustomerService.id_access("quản lý hàng tông kho",customer.getPermission(),"VIEW"))) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);

                log.setSrc(this.name + "INVALID ACCOUNT");
                log.setContent(username + " IS NOT ADMIN");
                log.setLevel(Log.WARNING);
                return;
            }
            String text = request.getParameter("text");
            request.setAttribute("text",text);
            String indexPage = request.getParameter("index");
            if(indexPage==null){
                indexPage="1";
            }
            int index = Integer.parseInt(indexPage);
            int pre = index - 1;
            int next = index + 1;

            List<Product> list_product = ProductService.findProduct(text);
            int begin = (index-1)*10;
            int end = Math.min(begin+10,list_product.size());
            List<Product> list = new ArrayList<Product>();
            for (int i= begin;i<end;i++){
                list.add(list_product.get(i));
            }
            int n = ProductService.findProduct(text).size();
            int endPage = n/10;
            if(n % 10 != 0){
                endPage++;
            }

            request.setAttribute("index", index);
            request.setAttribute("pre", pre);
            request.setAttribute("next", next);
            request.setAttribute("endP", endPage);

//        List<Product> list = ProductService.checkInventory();
            request.setAttribute("list",list);
            request.getRequestDispatcher("inventory.jsp").forward(request,response);

            log.setSrc(this.name + " FIND PRODUCT");
            log.setContent("FIND PRODUCT " + text + " SUCCESS: Admin - " + username);
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
