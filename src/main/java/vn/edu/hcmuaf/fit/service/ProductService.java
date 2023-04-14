package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.model.*;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class ProductService {
    public static List<Product> getData() {
        List<Product> list = new ArrayList<Product>();
        DBConnect dbConnect = DBConnect.getInstance();
        Statement statement = dbConnect.get();
        try {
            ResultSet rs = statement.executeQuery("select id_product from product");
            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> findProduct(String para) {
        List<Product> list = new ArrayList<Product>();
        DBConnect dbConnect = DBConnect.getInstance();
        Statement statement = dbConnect.get();
        try {
            ResultSet rs = statement.executeQuery("select id_product from product where name like '%" + para + "%'");
            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> filterProduct(String[] price, String[] star) {
        List<Product> result = new ArrayList<Product>();
        List<Integer> priceProduct = new ArrayList<Integer>();
        List<Integer> starProduct = new ArrayList<Integer>();
        DBConnect dbConnect = DBConnect.getInstance();
        Statement statement = dbConnect.get();
        if (price == null) {
            try {
                ResultSet rs = statement.executeQuery("select id_product from product");
                while (rs.next()) {
                    priceProduct.add(rs.getInt("id_product"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            for (String key : price) {
                switch (key) {
                    case "price-1": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from product where (price-price*discount)>=0 and (price-price*discount)<=500000");
                            while (rs.next()) {
                                priceProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "price-2": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from product where (price-price*discount)>=500000 and (price-price*discount)<=1000000");
                            while (rs.next()) {
                                priceProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "price-3": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from product where (price-price*discount)>=1000000 and (price-price*discount)<=2000000");
                            while (rs.next()) {
                                priceProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "price-4": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from product where (price-price*discount)>=2000000 and (price-price*discount)<=5000000");
                            while (rs.next()) {
                                priceProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "price-5": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from product where (price-price*discount)>=5000000");
                            while (rs.next()) {
                                priceProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    default:
                }
            }
        }
        if (star == null) {
            try {
                ResultSet rs = statement.executeQuery("select id_product from product");
                while (rs.next()) {
                    starProduct.add(rs.getInt("id_product"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            for (String key : star) {
                switch (key) {
                    case "star-1": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from star_vote where star>=0 and star<=1");
                            while (rs.next()) {
                                starProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "star-2": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from star_vote where star>1 and star<=2");
                            while (rs.next()) {
                                starProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "star-3": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from star_vote where star>2 and star<=3");
                            while (rs.next()) {
                                starProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "star-4": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from star_vote where star>3 and star<=4");
                            while (rs.next()) {
                                starProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "star-5": {
                        try {
                            ResultSet rs = statement.executeQuery("select id_product from star_vote where star>4 and star<=5");
                            while (rs.next()) {
                                starProduct.add(rs.getInt("id_product"));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    default:
                }
            }
        }
        for (int st : priceProduct) {
            if (starProduct.contains(st)) {
                try {
                    result.add(getProduct(st));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    public static Product getProduct(int idp) throws SQLException {
        Product p = new Product();
        p.setId(idp);
        p.setName(getname(idp));
        p.setPrice(getprice(idp));
        p.setBrand(getbrand(idp));
        p.setType(gettype(idp));
        p.setDiscount(getdiscount(idp));
        p.setImg(getimg(idp));
        p.setStar(getstar(idp));
        p.setAmount(getamount(idp));
        p.setRelease(getrelease(idp));
        p.setDecrispe(getdecrispe(idp));
        p.setDetail(getfirst(idp));
        return p;
    }

    public static List<ImageProduct> getimg(int id) throws SQLException {
        List<ImageProduct> img = new ArrayList<ImageProduct>();
        ImageProduct imgP = new ImageProduct();
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select id_img,id_product ,link_image, allow from image where id_product=? and allow=?");
        prs.setInt(1, id);
        prs.setString(2, "1");
        ResultSet rs = prs.executeQuery();
        while (rs.next()) {
            imgP = new ImageProduct(rs.getInt("id_img"), rs.getInt("id_product"), rs.getString("link_image"), rs.getString("allow"));
            img.add(imgP);
        }
        return img;
    }

    public static String getname(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select name from product where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getString("name");
        }
        return null;
    }

    public static long getprice(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select price from product where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getLong("price");
        }
        return 0;
    }

    public static String getbrand(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select brand from product where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getString("brand");
        }
        return null;
    }

    public static String gettype(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select type from product where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getString("type");
        }
        return null;
    }

    public static double getdiscount(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select discount from product where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getDouble("discount");
        }
        return 0;
    }

    public static Double getstar(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select star from star_vote where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getDouble("star");
        }
        return 0.0;
    }

    public static int getamount(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select amount from star_vote where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getInt("amount");
        }
        return 0;
    }

    public static String getdecrispe(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select decrispe from product where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getString("decrispe");
        }
        return null;
    }

    public static Date getrelease(int id) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select p.date from product p where p.id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            return rs.getDate("date");
        }
        return null;
    }

    public static void addComment(int id_Cus, int id_Pro, String mess, int star) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            Date date = new Date();
            ResultSet resultSet = DBConnect.getInstance().get().executeQuery("select curdate()");
            if (resultSet.next()) {
                date = resultSet.getDate(1);
            }
            PreparedStatement prs = dbConnect.getConnection().prepareStatement("insert into comment(id_customer,id_product,comment,star,date,display) values (?,?,?,?,?,?)");
            prs.setInt(1, id_Cus);
            prs.setInt(2, id_Pro);
            prs.setString(3, mess);
            prs.setInt(4, star);
            prs.setDate(5, (java.sql.Date) date);
            prs.setInt(6, 1);
            prs.executeUpdate();
            Product p = getProduct(id_Pro);
            int newAmount = p.getAmount() + 1;
            double newStar = (p.getStar() * p.getAmount() + star) / newAmount;
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update star_vote set amount =?, star =?  where id_product=?");
            ps.setInt(3, id_Pro);
            ps.setInt(1, newAmount);
            ps.setDouble(2, newStar);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> sort(String s) {
        List<Product> result = new ArrayList<Product>();
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            switch (s) {
                case "new": {
                    ResultSet rs = dbConnect.get().executeQuery("select p.id_product from product p order by p.date desc ");
                    while (rs.next()) {
                        result.add(getProduct(rs.getInt("id_product")));
                    }
                    break;
                }
                case "popular": {
                    ResultSet rs = dbConnect.get().executeQuery("select dp.id_product, count(dp.id_product) from bill b join detail_bill db on b.id = db.id_bill join detail_product dp on db.id_dp = dp.id_dp group by dp.id_product order by count(dp.id_product) desc");
                    while (rs.next()) {
                        result.add(getProduct(rs.getInt("id_product")));
                    }
                    break;
                }
                case "rating": {
                    ResultSet rs = dbConnect.get().executeQuery("select p.id_product from product p join star_vote s on p.id_product = s.id_product order by s.star desc");
                    while (rs.next()) {
                        result.add(getProduct(rs.getInt("id_product")));
                    }
                    break;
                }
                default:
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Product getDetailProduct(int idp) throws SQLException {
        Product p = new Product();
        p.setId(idp);
        p.setName(getname(idp));
        p.setPrice(getprice(idp));
        p.setBrand(getbrand(idp));
        p.setType(gettype(idp));
        p.setDiscount(getdiscount(idp));
        p.setImg(getimg(idp));
        p.setStar(getstar(idp));
        p.setAmount(getamount(idp));
        p.setRelease(getrelease(idp));
        p.setDecrispe(getdecrispe(idp));
        p.setDetail(getfirst(idp));
        return p;
    }

    public static List<DetailProduct> getfirst(int id) throws SQLException {
        List<DetailProduct> detail = new ArrayList<DetailProduct>();
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select id_dp, size, color, quantity from detail_product where id_product=? and quantity>0");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        while (rs.next()) {

            detail.add(new DetailProduct(rs.getInt("id_dp"), rs.getString("size"), rs.getString("color"), rs.getInt("quantity")));

        }
        return detail;
    }

    public static Product getDetailProduct(int idp, String size, String color) throws SQLException {
        Product p = new Product();
        p.setId(idp);
        p.setName(getname(idp));
        p.setPrice(getprice(idp));
        p.setBrand(getbrand(idp));
        p.setType(gettype(idp));
        p.setDiscount(getdiscount(idp));
        p.setImg(getimg(idp));
        p.setStar(getstar(idp));
        p.setAmount(getamount(idp));
        p.setRelease(getrelease(idp));
        p.setDecrispe(getdecrispe(idp));
        p.setDetail(getDetail(idp, size, color));
        return p;
    }

    public static List<DetailProduct> getDetail(int id, String size, String color) throws SQLException {
        List<DetailProduct> detail = new ArrayList<DetailProduct>();
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select id_dp, size, color, quantity from detail_product where id_product=? and size =? and color = ?");
        prs.setInt(1, id);
        prs.setString(2, size);
        prs.setString(3, color);
        ResultSet rs = prs.executeQuery();
        while (rs.next()) {
            detail.add(new DetailProduct(rs.getInt("id_dp"), rs.getString("size"), rs.getString("color"), rs.getInt("quantity")));
        }
        return detail;
    }

    public static Bill getBill(int id) throws SQLException {
        PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select id_dp from detail_bill where id_bill=?");
        prs.setInt(1, id);
        List<Integer> list_product = new ArrayList<Integer>();
        ResultSet rs = prs.executeQuery();
        while (rs.next()) {
            list_product.add(rs.getInt("id_dp"));
        }
        PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select id_customer, date, status, address, phone from bill where id=?");
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return new Bill(id, resultSet.getDate("date"), list_product, resultSet.getString("status"), resultSet.getInt("id_customer"), resultSet.getString("address"), resultSet.getString("phone"));
        }
        return null;
    }

    public static List<Customer> findCustomer(String para) {
        List<Customer> list = new ArrayList<Customer>();
        DBConnect dbConnect = DBConnect.getInstance();
        Statement statement = dbConnect.get();
        try {
            ResultSet rs = statement.executeQuery("select id_customer from customer where name like '%" + para + "%'");
            while (rs.next()) {
                list.add(getCustomer(rs.getInt("id_customer")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static Customer getCustomer(int idc) throws SQLException {
        Customer c = new Customer();
        PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select name,email,phone,address,username,password,permission from customer where id_customer=?");
        ps.setInt(1, idc);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            c.setId_customer(idc);
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phone"));
            c.setAddress(rs.getString("address"));
            c.setUsername(rs.getString("username"));
            c.setPassword(rs.getString("password"));
            c.setPermission(Integer.valueOf(rs.getString("permission")));
        }
        return c;
    }

    public static int addBill(int id_Customer, String status, List<Integer> id_dp, String address, String phone) {
        int id_bill = 0;
        try {

            Date date = new Date();
            ResultSet resultSet = DBConnect.getInstance().get().executeQuery("select curdate()");
            if (resultSet.next()) {
                date = resultSet.getDate(1);
            }
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("INSERT into bill(id_customer,date,status,address,phone) values(?,?,?,?,?)");
            prs.setInt(1, id_Customer);
            prs.setDate(2, (java.sql.Date) date);
            prs.setString(3, status);
            prs.setString(4, address);
            prs.setString(5, phone);
            prs.executeUpdate();

            ResultSet rs = DBConnect.getInstance().get().executeQuery("select id from bill");
            rs.last();
            id_bill = rs.getInt("id");

            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("INSERT into detail_bill(id_bill,id_dp) values(?,?)");
            for (int i : id_dp) {
                ps.setInt(1, id_bill);
                ps.setInt(2, i);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_bill;
    }

    public static void cancel_bill(int id_bill) {
        try {
            String status = "Đã hủy";
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("UPDATE bill set status = ? where id =?");
            ps.setString(1, status);
            ps.setInt(2, id_bill);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> listFullFace() {
        List<Product> list = new ArrayList<Product>();

        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product where type =?");
            ps.setString(1, "FULLFACE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Customer> getData_Customer() {
        List<Customer> list = new ArrayList<Customer>();
        DBConnect dbConnect = DBConnect.getInstance();
        Statement statement = dbConnect.get();
        try {
            ResultSet rs = statement.executeQuery("select id_customer from customer");
            while (rs.next()) {
                list.add(getCustomer(rs.getInt("id_customer")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> list3_4() {
        List<Product> list = new ArrayList<Product>();

        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product where type =? ");
            ps.setString(1, "3/4");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> listNuaDau() {
        List<Product> list = new ArrayList<Product>();

        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product where type =? ");
            ps.setString(1, "NUADAU");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Product> listChildren() {
        List<Product> list = new ArrayList<Product>();

        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product where type =? ");
            ps.setString(1, "CHILDREN");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static int countProduct() {
        int count = 0;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
        }
        return count;
    }

    public static int countDetailProduct() {
        int count = 0;
        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_dp from detail_product");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
        }
        return count;
    }

    public static int insertProduct(String name, String price, String brand, String type, String discount, String decrispe) {
        int id = 0;
        String typeDB = type;
        if (type.equals("3_4")) {
            typeDB = "3/4";
        }
        int priceDB = Integer.parseInt(price);
        double discounta = Double.parseDouble(discount);
        double discountDB = discounta / 100;
        int count = countProduct();

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int mont = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        String date = year + "-" + mont + "-" + day;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("insert into product(name,price,brand,type,discount,decrispe,date) values (?,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setInt(2, priceDB);
            ps.setString(3, brand);
            ps.setString(4, typeDB);
            ps.setDouble(5, discountDB);
            ps.setString(6, decrispe);
            ps.setString(7, date);
            ps.executeUpdate();
            ResultSet rs = DBConnect.getInstance().get().executeQuery("select id_product from product");
            rs.last();
            id = rs.getInt("id_product");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public static void insertDetailProduct(int id, String size, String color, String quantity) {
        int quantityDB = Integer.parseInt(quantity);

        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("insert into detail_product(id_product,size,color,quantity) values (?,?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, size.toUpperCase());

            ps.setString(3, color.toLowerCase());
            ps.setInt(4, quantityDB);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkDBContainSizeColor(int id, String size, String color) {
        boolean result = false;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select size, color from detail_product where id_product=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase(size) && rs.getString(2).equalsIgnoreCase(color)) {
                    return true;
                }
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public static int getIdDetailProductByCS(int id, String size, String color) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_dp, size, color from detail_product where id_product=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString(2).equalsIgnoreCase(size) && rs.getString(3).equalsIgnoreCase(color)) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public static void updateSizeColorById(int id, String quantity) {
        int quantityDB = Integer.parseInt(quantity);
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update detail_product set quantity = quantity+? where id_dp=?");
            ps.setInt(1, quantityDB);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("errp");
        }
    }

    public static void insertImg(int id, String img) {

        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("insert into image(id_product,link_image,allow) values (?,?,?)");

            ps.setInt(1, id);
            ps.setString(2, img);
            ps.setString(3, "1");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateProduct(int id, String name, String price, String brand, String type, String discount, String decrispe) {
        String typeDB = type;
        if (type.equals("3_4")) {
            typeDB = "3/4";
        }
        int priceDB = Integer.parseInt(price);
        double discounta = Double.parseDouble(discount);
        double discountDB = discounta / 100;

        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update product set name=?, price=?,brand=?,type=?,discount=?,decrispe=? where id_product=?");

            ps.setString(1, name);
            ps.setInt(2, priceDB);

            ps.setString(3, brand);
            ps.setString(4, typeDB);
            ps.setDouble(5, discountDB);
            ps.setString(6, decrispe);
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeProduct(int id) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("delete from product where id_product=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static void removeImage(int id) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("delete from image where id_img=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static void updateImage(int id, String allow) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update image set allow = ? where id_img= ?");

            ps.setString(1, allow);

            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static Map<String, Integer> getListBrand() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        try {
            ResultSet rs = DBConnect.getInstance().get().executeQuery("select brand,count(id_product)as SL from product group by brand");
            while (rs.next()) {
                result.put(rs.getString(1), Integer.parseInt(rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Product> getProductByBrand(String brand) {
        List<Product> result = new ArrayList<Product>();
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select id_product from product where brand =?");
            ps.setString(1, brand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(getProduct(rs.getInt("id_product")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getQuantityProduct(long starPrice, long endPrice) {
        int result = 0;
        try {
            if (endPrice == 0) {
                PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select count(price) from product where (price-price*discount)>=?");
                ps.setLong(1, starPrice);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    result += rs.getInt(1);
                }
            } else {
                PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select count(price) from product where (price-price*discount)>=? and (price-price*discount)<=?");
                prs.setLong(1, starPrice);
                prs.setLong(2, endPrice);
                ResultSet resultSet = prs.executeQuery();
                if (resultSet.next()) {
                    result += resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getQuantityStarProduct(int star) {
        int result = 0;
        try {
            if (star == 1) {
                PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select count(star) from star_vote where star>=0 and star<=1");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    result += rs.getInt(1);
                }
            } else {
                PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select count(star) from star_vote where star>?-1 and star<=?");
                prs.setInt(1, star);
                prs.setInt(2, star);
                ResultSet resultSet = prs.executeQuery();
                if (resultSet.next()) {
                    result += resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Product getProductFullImage(int idp) throws SQLException {
        Product p = new Product();
        p.setId(idp);
        p.setName(getname(idp));
        p.setPrice(getprice(idp));
        p.setBrand(getbrand(idp));
        p.setType(gettype(idp));
        p.setDiscount(getdiscount(idp));
        p.setImg(getimgAll(idp));
        p.setStar(getstar(idp));
        p.setAmount(getamount(idp));
        p.setRelease(getrelease(idp));
        p.setDecrispe(getdecrispe(idp));
        return p;
    }

    public static List<ImageProduct> getimgAll(int id) throws SQLException {
        List<ImageProduct> img = new ArrayList<ImageProduct>();
        ImageProduct imgP = new ImageProduct();
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select id_img,id_product ,link_image, allow from image where id_product=?");
        prs.setInt(1, id);
        ResultSet rs = prs.executeQuery();
        while (rs.next()) {
            imgP = new ImageProduct(rs.getInt("id_img"), rs.getInt("id_product"), rs.getString("link_image"), rs.getString("allow"));
            img.add(imgP);
        }
        return img;
    }

    public static void removeDetailProduct(int id) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("delete from detail_product where id_dp=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static void updateDetailPQuantity(int id, String quantity) {
        int quantityDB = Integer.parseInt(quantity);
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update detail_product set quantity = ? where id_dp=?");
            ps.setInt(1, quantityDB);
            ps.setInt(2, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("errp");
        }
    }

    public static List<Product> getData(int a, int b) {
        List<Product> list = new ArrayList<Product>();
        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product limit ?,?");
            ps.setInt(1, a);
            ps.setInt(2, b);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Product> pagination(int a, int b, List<Product> list) {
        List<Product> result = new ArrayList<Product>();
        for (int i = a; i < b + a; i++) {
            if (i < list.size()) {
                result.add(list.get(i));
            }
        }

        return result;
    }

    public static List<Product> listDiscount(String discount) {
        List<Product> list = new ArrayList<Product>();
        double discountDB = Double.parseDouble(discount);
        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product where discount =?");
            ps.setDouble(1, discountDB);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void delete_customer(int id) {
        try {
            PreparedStatement preparedStatement = DBConnect.getInstance().getConnection().prepareStatement("delete from customer where id_customer=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fix_customer(int id, String name, String phone, String email, String address) {
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("update customer set name=?,phone=?,email=?,address=? where id_customer=?");
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setString(4, address);
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Bill> getBillByDate(int month, int year) {
        List<Bill> result = new ArrayList<Bill>();
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select id from bill where month(date)=? and year(date)=?");
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(getBill(rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getIdProduct(int id_dp) {
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select p.id_product from product p join detail_product dp on p.id_product = dp.id_product where dp.id_dp=?");
            ps.setInt(1, id_dp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id_product");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Bill> getListBill() {
        List<Bill> result = new ArrayList<Bill>();
        try {
            ResultSet rs = DBConnect.getInstance().get().executeQuery("select id from bill");
            while (rs.next()) {
                result.add(getBill(rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Bill> findBill(String para) {
        List<Bill> list = new ArrayList<Bill>();
        DBConnect dbConnect = DBConnect.getInstance();
        Statement statement = dbConnect.get();
        try {
            ResultSet rs = statement.executeQuery("select id from bill where id like '%" + para + "%'");
            while (rs.next()) {
                list.add(getBill(rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Bill> getListBillByIdCustomer(int id_cus) {
        List<Bill> result = new ArrayList<Bill>();
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select id from bill where id_customer=?");
            ps.setInt(1, id_cus);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(getBill(rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Product> totalProductBill(int month, int year) throws SQLException {
        List<Product> list_product = new ArrayList<Product>();
        List<Bill> list = getBillByDate(month, year);
        for (Bill b : list) {
            for (int s : b.getProductList()) {
                list_product.add(getProduct(getIdProduct(s)));
            }
        }
        return list_product;
    }

    public static long totalPriceBill(int month, int year) {
        long result = 0;
        List<Product> list = new ArrayList<Product>();
        try {
            list = totalProductBill(month, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Product p : list) {
            result += (p.getPrice() * (1 - (long) p.getDiscount()));
        }
        return result;
    }

    public static String getSize(int id_dp) {
        String result = "";
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select size from detail_product where id_dp=?");
            ps.setInt(1, id_dp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result += rs.getString("size");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getColor(int id_dp) {
        String result = "";
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select color from detail_product where id_dp=?");
            ps.setInt(1, id_dp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result += rs.getString("color");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getQuantity(int id_bill, int id_dp) {
        int result = 0;
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select id_bill,count(id_dp) as quantity from detail_bill where id_dp=? and id_bill=? group by id_bill");
            ps.setInt(1, id_dp);
            ps.setInt(2, id_bill);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) result += rs.getInt("quantity");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void updateBill(int id, String address, String phone, String status) {
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("update bill set address=?,phone=?, status=? where id=? ");
            ps.setString(1, address);
            ps.setString(2, phone);
            ps.setString(3, status);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatus(int id, String status) {
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("update bill set status=? where id=? ");
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBill(int id_bill) {
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("delete from bill where id=?");
            ps.setInt(1, id_bill);
            ps.executeUpdate();
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("delete from detail_bill where id_bill=?");
            prs.setInt(1, id_bill);
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> listType(String type, int id) {
        List<Product> list = new ArrayList<Product>();

        DBConnect dbConnect = DBConnect.getInstance();

        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product where type =? and id_product not in(?)");
            ps.setString(1, type);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(getProduct(rs.getInt("id_product")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static boolean idProduct(int id) {
        boolean result = false;
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from product where id_product=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean idDetaiProduct(int id) {
        boolean result = false;
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_dp from detail_product where id_dp=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean idImg(int id) {
        boolean result = false;
        try {
            DBConnect dbConnect = DBConnect.getInstance();
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_img from image where id_img=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getIdProductByIddp(int id_dp) {
        int result = 0;
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select id_product from detail_product where id_dp=?");
            ps.setInt(1, id_dp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result += rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getIdCusByUserName(String username) {
        int result = 0;
        try {
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select id_customer from customer where username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result += rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Integer> getListIDCommentByProduct(int idpro) {
        List<Integer> list = new ArrayList<Integer>();
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select id from comment where id_product=?");
            prs.setInt(1, idpro);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getIdCustomerByIdComment(int id) {
        int result = 0;
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select id_customer from comment where id=?");
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                result += rs.getInt("id_customer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCommentByIdComment(int id) {
        String result = "";
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select comment from comment where id=?");
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                result += rs.getString("comment");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getStarByIdComment(int id) {
        int result = 0;
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select star from comment where id=?");
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                result = rs.getInt("star");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date getDateByIdComment(int id) {
        String result = "";
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select date from comment where id=?");
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery();
            if (rs.next()) {
                return rs.getDate("date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDisplayByIdComment(int id) {
        int result = 0;
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select display from comment where id=?");
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery();
            if (rs.next()) {
                return rs.getInt("display");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void changeDisplayComment(int id) {
        int dislay = getDisplayByIdComment(id);
        try {
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("update comment set display = ? where id =?");
            prs.setInt(2, id);
            if (dislay == 0) {
                prs.setInt(1, 1);
            } else {
                prs.setInt(1, 0);
            }
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertImportProduct(int id_product, String size, String color, String quantity, String price) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("insert into importproducts(id_product,size,color, quantity, price,date) values (?,?,?,?,?,?)");
            ps.setInt(1, id_product);
            ps.setString(2, size);
            ps.setString(3, color);
            ps.setLong(4, Long.parseLong(quantity));
            ps.setLong(5, Long.parseLong(price));
            ps.setString(6, LocalDateTime.now().toString());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long maxPrice(String id) {
        DBConnect dbConnect = DBConnect.getInstance();
        long priceMax = 0;
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select max(price) from  importproducts where id_product= ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                priceMax = rs.getLong(1);
            }
        } catch (SQLException e) {
        }
        return priceMax;
    }

    public static void updatePriceMax(String id) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("update product set price = ? where id_product= ?");
            ps.setInt(1, (int) (maxPrice(id)));
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public static void getProductImport() {
        DBConnect dbConnect = DBConnect.getInstance();
        long priceMax = 0;
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product from  importproducts");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                priceMax = rs.getLong(1);
            }
        } catch (SQLException e) {
        }
    }

    public static long totalQuantity(int id) {
        DBConnect dbConnect = DBConnect.getInstance();
        long total = 0;
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select sum(quantity) from  detail_product where id_product= ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total += rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return total;
    }

    public static long totalPriceImport(int id) {
        long result = 0;
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select sum(price*quantity) from  importproducts where id_product=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (SQLException e) {
        }
        return result;
    }

    public static long totalQuantityImport(int id) {
        DBConnect dbConnect = DBConnect.getInstance();
        long total = 0;
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select sum(quantity) from  importproducts where id_product= ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total += rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return total;
    }

    public static List<ImportProduct> getProductInImport() {
        List<ImportProduct> list = new ArrayList<ImportProduct>();
        ImportProduct im = new ImportProduct();
        DBConnect dbConnect = DBConnect.getInstance();
        long priceMax = 0;
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select distinct i.id_product, p.name from  importproducts i join product p on i.id_product = p.id_product");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                im = new ImportProduct(rs.getInt("id_product"), rs.getString("name"), totalQuantityImport(rs.getInt("id_product")), totalPriceImport(rs.getInt("id_product")), getimgFirst(rs.getInt("id_product")));
                list.add(im);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public static String getimgFirst(int id) throws SQLException {
        String img = "";
        DBConnect dbConnect = DBConnect.getInstance();
        PreparedStatement prs = dbConnect.getConnection().prepareStatement("select link_image from image where id_product=? and allow=?");
        prs.setInt(1, id);
        prs.setString(2, "1");
        ResultSet rs = prs.executeQuery();
        if (rs.next()) {
            img = rs.getString("link_image");
        }
        return img;
    }

    public static List<ImportProduct> getDetailImport(int id) {
        List<ImportProduct> list = new ArrayList<ImportProduct>();

        ImportProduct im = new ImportProduct();
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select size, color, quantity, price, date from importproducts where id_product=? ");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                im = new ImportProduct(rs.getString("size"), rs.getString("color"), rs.getLong("price"), rs.getLong("quantity"), rs.getDate("date"));
                list.add(im);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public static List<Product> checkInventory() {
        List<Product> list = new ArrayList<Product>();
        DBConnect dbConnect = DBConnect.getInstance();
        Product p = new Product();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id_product, name from product");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p = new Product(rs.getInt("id_product"), rs.getString("name"), totalQuantityImport(rs.getInt("id_product")) - amountSold(rs.getInt("id_product")));
                p.setImg(getimg(rs.getInt("id_product")));
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public static long amountSold(int id) {
        DBConnect dbConnect = DBConnect.getInstance();
        long count = 0;
        try {
            PreparedStatement ps = dbConnect.getConnection().
                    prepareStatement("select  SUM(db.quantitySold) sum from detail_product dp join detail_bill db on dp.id_dp = db.id_dp where dp.id_product=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("sum");
            }
        } catch (SQLException e) {
        }
        return count;
    }

    public static long amountSoldDetail(int id_product, String size, String color) {
        DBConnect dbConnect = DBConnect.getInstance();
        long count = 0;
        try {
            PreparedStatement ps = dbConnect.getConnection().
                    prepareStatement("select  SUM(db.quantitySold) sum from detail_product dp join detail_bill db on dp.id_dp = db.id_dp where dp.id_product=? and dp.size=? and dp.color=?");
            ps.setInt(1, id_product);
            ps.setString(2, size);
            ps.setString(3, color);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                count = rs.getInt("sum");
            }
        } catch (SQLException e) {
        }
        return count;
    }
    public static long amountImportDetail(int id_product, String size, String color) {
        DBConnect dbConnect = DBConnect.getInstance();
        long count = 0;
        try {
            PreparedStatement ps = dbConnect.getConnection().
                    prepareStatement("select  SUM(quantity) sum from importproducts  where id_product=? and size=? and color=?");
            ps.setInt(1, id_product);
            ps.setString(2, size);
            ps.setString(3, color);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                count = rs.getInt("sum");
            }
        } catch (SQLException e) {
        }
        return count;
    }
    public static List<ImportProduct> listInventoryDetail(String id_product){
        List<ImportProduct> list = new ArrayList<ImportProduct>();
        DBConnect dbConnect = DBConnect.getInstance();
        ImportProduct i = new ImportProduct();
        try {
            PreparedStatement ps = dbConnect.getConnection().
                    prepareStatement("select distinct id_product, size, color from importproducts where id_product=? ");
            ps.setString(1,id_product);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = new ImportProduct(rs.getString("size"), rs.getString("color"), amountImportDetail(Integer.parseInt(rs.getString("id_product")),rs.getString("size"),rs.getString("color"))
                        -amountSoldDetail(Integer.parseInt(rs.getString("id_product")),rs.getString("size"),rs.getString("color")));
                list.add(i);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    public static List<Product> getRecords(int start,int total){
        List<Product> list=new ArrayList<Product>();
        DBConnect dbConnect = DBConnect.getInstance();
        try{
            PreparedStatement ps=dbConnect.getConnection().prepareStatement(
                    "select id_product from product limit "+(start-1)+","+total);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                list.add(getProduct(rs.getInt("id_product")));
            }

        }catch(Exception e){System.out.println(e);}
        return list;
    }
    public static List<Product> getRecords(int start,int total, String text){
        List<Product> list=new ArrayList<Product>();
        DBConnect dbConnect = DBConnect.getInstance();
        try{
            PreparedStatement ps=dbConnect.getConnection().prepareStatement(
                    "select id_product from product  where name like '%" + text + "%' limit ?,?");
            ps.setInt(1,start-1);
            ps.setInt(2, total);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                list.add(getProduct(rs.getInt("id_product")));
            }

        }catch(Exception e){System.out.println(e);}
        return list;
    }
    public static int quantityFindProduct(String para) {
        int result=  0;
        DBConnect dbConnect = DBConnect.getInstance();
        Statement statement = dbConnect.get();
        try {
            ResultSet rs = statement.executeQuery("select  count(id_product) count from product where name like '%" + para + "%'");
            while (rs.next()) {
                result+=rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static void main(String[] args) throws SQLException {

    }
}
