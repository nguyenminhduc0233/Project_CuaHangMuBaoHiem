package vn.edu.hcmuaf.fit.model;

public class Comment {
    private String id_customer;
    private String id_product;
    private String avatar;
    private String content;
    private String star;
    private String date;

    private int id_comt;

    public Comment(String id_customer, String id_product, String content, String star, String date, String avatar, int id_comt) {
        this.id_customer = id_customer;
        this.id_product = id_product;
        this.content = content;
        this.star = star;
        this.date = date;
        this.avatar = avatar;
        this.id_comt = id_comt;
    }

    public int getId_comt() {
        return id_comt;
    }

    public void setId_comt(int id_comt) {
        this.id_comt = id_comt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }
}
