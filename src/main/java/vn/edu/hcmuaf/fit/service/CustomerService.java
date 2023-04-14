package vn.edu.hcmuaf.fit.service;

import org.apache.commons.codec.digest.DigestUtils;
import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.model.Customer;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class CustomerService {
    public static List<Customer> getData() throws SQLException {
        List<Customer> list = new ArrayList<>();

        return list;
    }

    public static String toMD5(String password) {
        return DigestUtils.md5Hex(password).toLowerCase();
    }


    public static String GetRandom() {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        sb.append(rd.nextLong(100000, 999999));
        return sb.toString();
    }

    public static void changePassword(String username, String pass_old, String pass_new) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username, password from customer where username = ? and password = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, pass_old);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            StringBuilder sb = new StringBuilder("Xin chào " + rs.getString("username") + ", \n");
            sb.append("Mật khẩu của bạn đã được thay đổi thành công vào " + LocalDate.now() + " lúc " + LocalTime.now() + ". \n\n\n");
            sb.append("Trân trọng cảm ơn! \n");
            sb.append("Đội ngũ bảo mật HelmetShop.");
            MailService.sendMail(rs.getString("email"), "Thay đổi mật khẩu - HelmetShop", sb.toString());
            String change = "update customer set password = '" + pass_new + "' where username = '" + username + "';";
            pre.executeUpdate(change);
        }
    }

    public static void addCustomer(String username, String password, String name, String email) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement ps = dbConnect.getConnection().prepareStatement("insert into customer(name,email,phone,address,username,password,permission,active,create_date,countLock) values (?,?,?,?,?,?,0,1,?,0)");
        ps.setString(1,name);
        ps.setString(2,email);
        ps.setString(3,"");
        ps.setString(4,"");
        ps.setString(5,username);
        ps.setString(6,password);
        ps.setString(7,LocalDateTime.now().toString());
        ps.executeUpdate();

    }

    public static void resetPassword(String email) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        String password = GetRandom();
        String sql = "select email, password from customer where email = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, email);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
//            String content = "Xin chào " + rs.getString("username") + ", \n" + "Chúng tôi đã nhận được thông báo cấp lại mật khẩu từ bạn. " + password + " là mật khẩu đặt lại HelmetShop của bạn.";
//            sb.append("Mật khẩu của bạn đã được thay đổi vào " + LocalDate.now() + " lúc " + LocalTime.now() +". \n");
            StringBuilder sb = new StringBuilder("Xin chào " + rs.getString("username") + ", \n");
            sb.append("Chúng tôi đã nhận được thông báo cấp lại mật khẩu từ bạn. ");
            sb.append(password + " là mật khẩu đặt lại HelmetShop của bạn. \n\n\n");
            sb.append("Trân trọng cảm ơn! \n");
            sb.append("Đội ngũ bảo mật HelmetShop.");
            MailService.sendMail(email, "Đặt lại mật khẩu - HelmetShop", sb.toString());
            String reset = "update customer set password = '" + toMD5(password) + "' where email = '" + email + "';";
            pre.executeUpdate(reset);
        }
    }

    public static Customer customer(String username) throws SQLException {
        Customer customer = null;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select name, email, phone, address, permission from customer where username = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            customer = new Customer(rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), Integer.parseInt(rs.getString("permission")));
        }
        return customer;
    }

    public static boolean checkLogin(String username, String password) throws SQLException {
        boolean isLogin = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username, password from customer where username = ? and password = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, password);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            isLogin = true;
        }
        return isLogin;
    }
    public static boolean checkLoginLock(String username, String password) throws SQLException {
        boolean isLogin = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username, password from customer where username = ? and password = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, password);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            isLogin = true;
        }
        return isLogin;
    }
    public static boolean checkUsername(String username) throws SQLException {
        boolean isUsername = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username from customer where username = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            isUsername = true;
        }
        return isUsername;
    }

    public static boolean checkEmail(String email) throws SQLException {
        boolean isEmail = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select email from customer where email = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, email);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            isEmail = true;
        }
        return isEmail;
    }

    public static boolean emailValidate(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

    public static boolean pwValidate(String password, String confirm_pw) {
        boolean isPassword = false;
        if (password.equals(confirm_pw)) {
            isPassword = true;
        }
        return isPassword;
    }

    public static int checkActive(String username) throws SQLException {
        int isActive = 0;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username, active from customer where username = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            if (Integer.parseInt(rs.getString("active")) == 1) {
                isActive = 1;
            }
        }
        return isActive;
    }
    public static void checkLock(String username){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select countLock from customer where username=?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                if(rs.getInt("countLock")>=4){
                lock(username);
                }
            }
        } catch (SQLException e) {
        }
    }
    public static void lock(String username){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update customer set active=? where username=?");
            ps.setString(1,"0");
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public void countLock(String username){

        int count= 0;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select countLock from customer where username=?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                count= rs.getInt("countLock");
            }
        } catch (SQLException e) {
        }
    }
    public static void plusLock(String username){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update customer set countLock= countLock +1 where username=?");
            ps.setString(1,username);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public static void resetLock(String username){
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update customer set countLock= ? where username=?");
            ps.setInt(1,0);
            ps.setString(2,username);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
    public static boolean allow_service(int id){
        boolean check = false;
        int allows = 0;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement prs = dbConnect.getConnection().prepareStatement("select allow from manager_permissions where id = ?");
            prs.setInt(1,id);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                allows = rs.getInt("allow");
            }
            if(allows == 0){
                check = false;
            }else{
                check = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return check;
    }

    public static List<Integer> find_service(String name){
        List<Integer> list = new ArrayList<Integer>();
        DBConnect dbConnect = DBConnect.getInstance();
        Statement statement = dbConnect.get();
        try {
            ResultSet rs = statement.executeQuery("select id from manager_permissions where service like '%"+name+"%'");
            while (rs.next()) {
                list.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static String addPermissionManager(String service){
        if(checkContainService(service)){
            return "Dịch vụ đã tồn tại";
        }
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement prs = dbConnect.getConnection().prepareStatement("insert into manager_permissions (permission, service, allow) values (?,?,?)");
            prs.setInt(1,1);
            prs.setString(2, service);
            prs.setInt(3, 1);
            prs.executeUpdate();

            prs.setInt(1,0);
            prs.setString(2, service);
            prs.setInt(3, 0);
            prs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "Thêm chức năng thành công";
    }
    public static String deletePermissionManager(String service){
        if(!checkContainService(service)){
            return "Không tìm thấy chức năng";
        }
        try{
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement prs = dbConnect.getConnection().prepareStatement("delete from manager_permissions where service = ?");
            prs.setString(1, service);
            prs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "Xóa chức năng thành công";
    }

    public static void changeAllow(int id){
        try{
            int allow = 0;
            if(allow_service(id)){allow=0;}
            else{allow=1;}
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement prs = dbConnect.getConnection().prepareStatement("update manager_permissions set allow = ? where id = ?");
            prs.setInt(1, allow);
            prs.setInt(2,id);
            prs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static List<String> getListService(){
        List<String> list = new ArrayList<String>();
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            Statement statement = dbConnect.get();
            ResultSet rs = statement.executeQuery("select distinct service from manager_permissions");
            while (rs.next()){
                list.add(rs.getString("service"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public static String getServiceById(int id){
        String result = "";
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select service from manager_permissions where id = ?");
            prs.setInt(1,id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()){
                result = rs.getString("service");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    public static int getPermissionById(int id){
        int result = 0;
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select permission from manager_permissions where id = ?");
            prs.setInt(1,id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()){
                result = rs.getInt("permission");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    public static boolean checkContainService(String service){
        boolean result = false;
        for(String s:getListService()){
            if(s.equalsIgnoreCase(service)){
                result = true;
                break;
            }
        }
        return result;
    }
    public static String checkPermission(int permission){
        String result = "";
        if(permission==1){
            result+="Quản lý";
        }else if(permission==0){
            result+="Khách hàng";
        }
        return result;
    }
    public static List<Integer> getListIdPermission(){
        List<Integer> list = new ArrayList<Integer>();
        try {
            ResultSet rs = DBConnect.getInstance().get().executeQuery("select id from manager_permissions");
            while (rs.next()){
                list.add(rs.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public static boolean allow_access(String service, int permission){
        if(permission==2){return true;}
        int result = 0;
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select allow from manager_permissions where service=? and permission=?");
            prs.setString(1,service);
            prs.setInt(2,permission);
            ResultSet rs = prs.executeQuery();
            while (rs.next()){
                result = rs.getInt("allow");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(result==0){
            return false;
        }else{
            return true;
        }
    }

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        System.out.print(toMD5("712498390342654"));
    }
}
