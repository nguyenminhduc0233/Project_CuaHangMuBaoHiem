package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.model.Contact;
import vn.edu.hcmuaf.fit.model.SlideShow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SlideShowService {
    private static SlideShowService instance;
    public SlideShowService() {
    }
    public static SlideShowService getInstance() {
        if (instance == null) {
            instance = new SlideShowService();
        }
        return instance;
    }
    public List<SlideShow> getImgSlideShow(){
        List<SlideShow> list = new LinkedList<SlideShow>();
        SlideShow s = new SlideShow();
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps =dbConnect.getConnection().prepareStatement("select id_slideshow, img, allow from slideshow where allow = ?");
            ps.setString(1,"1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s = new SlideShow(rs.getInt(1),rs.getString(2),rs.getString(3));
                list.add(s);
            }

        } catch (SQLException e) {
        }
        return list;
    }
    public List<SlideShow> getImgSlideShowAll(){
        List<SlideShow> list = new LinkedList<SlideShow>();
        SlideShow s = new SlideShow();
        DBConnect dbConnect = DBConnect.getInstance();

        Contact contact = new Contact();
        try {
            PreparedStatement ps =dbConnect.getConnection().prepareStatement("select id_slideshow, img, allow from slideshow");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s = new SlideShow(rs.getInt(1),rs.getString(2),rs.getString(3));
                list.add(s);
            }

        } catch (SQLException e) {
        }
        return list;
    }
    public List<SlideShow> getImgBanner(){
        List<SlideShow> list = new LinkedList<SlideShow>();
        SlideShow s = new SlideShow();
        DBConnect dbConnect = DBConnect.getInstance();

        Contact contact = new Contact();
        try {
            PreparedStatement ps =dbConnect.getConnection().prepareStatement("select id_banner, img, allow, discount, content from banner where allow = ?");
            ps.setString(1,"1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s = new SlideShow(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5));
                list.add(s);
            }

        } catch (SQLException e) {
        }
        return list;
    }
    public List<SlideShow> getImgBannerAll(){
        List<SlideShow> list = new LinkedList<SlideShow>();
        SlideShow s = new SlideShow();
        DBConnect dbConnect = DBConnect.getInstance();

        Contact contact = new Contact();
        try {
            PreparedStatement ps =dbConnect.getConnection().prepareStatement("select id_banner, img, allow, discount, content from banner");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s = new SlideShow(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5));
                list.add(s);
            }

        } catch (SQLException e) {
        }
        return list;
    }
    public List<SlideShow> getImgLogo(){
        List<SlideShow> list = new LinkedList<SlideShow>();
        SlideShow s = new SlideShow();
        DBConnect dbConnect = DBConnect.getInstance();

        Contact contact = new Contact();
        try {
            PreparedStatement ps =dbConnect.getConnection().prepareStatement("select id_logo, img, allow,name from logo where allow = ?");
            ps.setString(1,"1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s = new SlideShow(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                list.add(s);
            }

        } catch (SQLException e) {
        }
        return list;
    }
    public List<SlideShow> getImgLogoAll(){
        List<SlideShow> list = new LinkedList<SlideShow>();
        SlideShow s = new SlideShow();
        DBConnect dbConnect = DBConnect.getInstance();

        Contact contact = new Contact();
        try {
            PreparedStatement ps =dbConnect.getConnection().prepareStatement("select id_logo, img, allow, name from logo");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s = new SlideShow(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                list.add(s);
            }

        } catch (SQLException e) {
        }
        return list;
    }

        public void removeSlideShow(int id)  {
            DBConnect dbConnect = DBConnect.getInstance();
            try {
                PreparedStatement ps = dbConnect.getConnection().prepareStatement("delete from slideshow where id_slideshow=?");
                ps.setInt(1,id);
                ps.executeUpdate();
            } catch (SQLException e) {
            }
    }
    public void removeBanner(int id)  {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("delete from banner where id_banner=?");
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void removeLogo(int id)  {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("delete from logo where id_logo=?");
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void updateBanner(int id, String allow, String discount,String content){
        DBConnect dbConnect = DBConnect.getInstance();
        double x= Double.parseDouble(discount);
        double discountDB = x/100;
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update banner set allow = ?,discount = ?, content = ? where id_banner= ?");

            ps.setString(1,allow);
            ps.setDouble(2,discountDB);
            ps.setString(3,content);
            ps.setInt(4,id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void updateLogo(int id, String allow, String name){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update logo set allow = ?, name=? where id_logo= ?");

            ps.setString(1,allow);
            ps.setString(2,name);
            ps.setInt(3,id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void updateSlideShow(int id, String allow){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update slideshow set allow = ? where id_slideshow= ?");

            ps.setString(1,allow);

            ps.setInt(2,id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void insertSlideShow(String image){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("insert into slideshow(img,allow) values (?,?)");
            ps.setString(1,image);
            ps.setString(2,"1");
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void insertBanner(String image){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("insert into banner(img,allow) values (?,?)");
            ps.setString(1,image);
            ps.setString(2,"1");
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void insertLogo(String image){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("insert into logo(img,allow,name) values (?,?,?)");
            ps.setString(1,image);
            ps.setString(2,"1");
            ps.setString(3,"T??n th????ng hi???u");
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public int quantitySlideShow(){
        int count =0;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_slideshow from slideshow");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
        }
        return count;
    }
    public int quantityLogo(){
        int count =0;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_logo from logo");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
        }
        return count;
    }
    public int quantityBanner(){
        int count =0;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_banner from banner");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
        }
        return count;
    }
    public String getImgBrand(String brand){
        String result="";
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select img from logo where name=?");
            ps.setString(1,brand);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result+=rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean idSlideShow(int id){
        boolean result=false;
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_slideshow from slideshow where id_slideshow=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result=true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean idBanner(int id){
        boolean result=false;
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_banner from banner where id_banner=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result=true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean idLogo(int id){
        boolean result=false;
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_logo from logo where id_logo=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result=true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        SlideShowService s = new SlideShowService();
        getInstance().insertSlideShow("ajjasja");
    }
}
