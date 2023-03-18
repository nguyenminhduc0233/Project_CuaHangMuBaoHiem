package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.model.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentService {
    ResultSet rs = null;
    public static void addComment(String id_customer, String id_product, String content, int star, Date date, String avatar, int id_comt) {
        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement prs = dbConnect.getConnection().prepareStatement("insert into comment values(?, ?, ?, ?, ?, ?, ?)");
            prs.setString(1,id_customer);
            prs.setString(2,id_product);
            prs.setString(3,content);
            prs.setInt(4,star);
            prs.setDate(5, (java.sql.Date) date);
            prs.setString(6, avatar);
            prs.setInt(7, id_comt);
            prs.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public List<Comment> getComment(String id_pro){
        String query = "select * from comment where id_product = ? order by date desc";
        List<Comment> list = new ArrayList<>();
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Comment(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7)));
            }
        } catch (Exception e){

        }
        return null;
    }
}
