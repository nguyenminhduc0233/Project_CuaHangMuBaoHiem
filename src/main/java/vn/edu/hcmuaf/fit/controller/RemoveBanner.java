package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Log;
import vn.edu.hcmuaf.fit.service.LogService;
import vn.edu.hcmuaf.fit.service.SlideShowService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveBanner", value = "/RemoveBanner")
public class RemoveBanner extends HttpServlet {
    String namee = "AUTH ";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = (String) request.getSession().getAttribute("tendangnhap");
        SlideShowService.getInstance().removeBanner(id);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ManageHome");

        Log log = new Log(Log.INFO, username, this.namee, "", 0);
        log.setSrc(this.namee + "REMOVE BANNER");
        log.setContent("REMOVE BANNER AT: Username - "  + username);
        LogService.log(log);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
