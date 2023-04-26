<%@ page import="vn.edu.hcmuaf.fit.service.ProductService" %>
<%@ page import="vn.edu.hcmuaf.fit.model.Comment" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.model.NumberFormat" %>
<%@ page import="vn.edu.hcmuaf.fit.model.Product" %>

<% NumberFormat nf = new NumberFormat();
  Product p= (Product) request.getAttribute("product");
%>
<%List<Comment> listComment = ProductService.getListCommentById(p.getId());%>

<%for(Comment com:listComment){%>
    <div class="media-body" >
      <h6><%=ProductService.getCustomer(com.getId_customer()).getName()%><small> - <i><%=ProductService.getDateComment(com.getId())%></i></small></h6>
      <div class="text-primary mb-2">
        <%int star = ProductService.getStarComment(com.getId());
          for(int a=0;a<star;a++){%>
        <i class="fas fa-star"></i>
        <%}%>
      </div>
      <p><%=com.getContent()%></p>
    </div>
<%}%>
