package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.ImportProduct;
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

@WebServlet(name = "find-product-import", value = "/find-product-import")
public class FindProductImport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer = null;
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() != 0 && !CustomerService.allow_service(CustomerService.id_access("quản lý nhập hàng", customer.getPermission(), "VIEW"))) {
                if(customer.getPermission()==3){
                    String message = "Bạn không đủ quyền để truy cập";
                    session.setAttribute("message",message);
                    response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
                    return;
                }else{
                    String previousURL = request.getHeader("referer");
                    if(!CustomerService.allow_service(CustomerService.id_access_link(previousURL,customer.getPermission(),"VIEW"))){
                        String message = "Bạn không đủ quyền để truy cập";
                        session.setAttribute("message",message);
                        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
                        return;
                    }else{
                        session.setAttribute("previousURL", previousURL);
                        String message = "Bạn không đủ quyền để truy cập";
                        session.setAttribute("message",message);
                        response.sendRedirect(previousURL);
                        return;
                    }
                }
            }
            String text = request.getParameter("text");
            request.setAttribute("text", text);

            String indexPage = request.getParameter("index");
            if (indexPage == null) {
                indexPage = "1";
            }
            int index = Integer.parseInt(indexPage);
            int pre = index - 1;
            int next = index + 1;

            List<ImportProduct> list_product = ProductService.findProductImport(text);
            List<ImportProduct> list = new ArrayList<ImportProduct>();
            int begin = (index-1)*10;
            int end = Math.min(list_product.size(),begin+10);
            for(int i=begin;i<end;i++){
                list.add(list_product.get(i));
            }

            int n = list_product.size();
            int endPage = n / 10;
            if (n % 10 != 0) {
                endPage++;
            }

            request.setAttribute("index", index);
            request.setAttribute("pre", pre);
            request.setAttribute("next", next);
            request.setAttribute("endP", endPage);

            request.setAttribute("list", list);
            request.getRequestDispatcher("ImportProduct.jsp").forward(request, response);

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
