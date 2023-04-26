package vn.edu.hcmuaf.fit.model;

public class Comment {
    private int id_customer;
    private int id_product;
    private String content;
    private String star;
    private String date;

    private int id;
    private int display;

    public Comment(int id_customer, int id_product, String content, String star, String date, int id, int display) {
        this.id_customer = id_customer;
        this.id_product = id_product;
        this.content = content;
        this.star = star;
        this.date = date;
        this.id = id;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }
}
