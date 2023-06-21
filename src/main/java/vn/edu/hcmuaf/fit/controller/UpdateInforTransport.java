package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.api.Transport;
import vn.edu.hcmuaf.fit.service.TransportService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateInforTransport", value = "/UpdateInforTransport")
public class UpdateInforTransport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        TransportService.getInstance().update(Integer.parseInt(district),Integer.parseInt(ward));
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ManageTransport.jsp?province="+province+"&district="+district+"&ward="+ward);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
