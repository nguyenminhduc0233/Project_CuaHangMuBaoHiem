package vn.edu.hcmuaf.fit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill {
    private int id;
    private Date date;
    private List<Integer> productList;
    private String status;
    private int id_cus;
    private String address;
    private String phone;
    private Date received;
    private int total_cost;

    public Bill(int id, Date date, List<Integer> list, String status, int id_cus, String address, String phone) {
        this.id = id;
        this.date = date;
        this.productList = list;
        this.status = status;
        this.id_cus = id_cus;
        this.address = address;
        this.phone = phone;
    }

    public Bill(int id, Date date, List<Integer> list, String status, int id_cus, String address, String phone, Date received, int total_cost) {
        this.id = id;
        this.date = date;
        this.productList = list;
        this.status = status;
        this.id_cus = id_cus;
        this.address = address;
        this.phone = phone;
        this.received = received;
        this.total_cost = total_cost;
    }

    public Bill() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Integer> getProductList() {
        return productList;
    }

    public void setProductList(List<Integer> productList) {
        this.productList = productList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_cus() {
        return id_cus;
    }

    public void setId_cus(int id_cus) {
        this.id_cus = id_cus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", productList=" + productList +
                ", status='" + status + '\'' +
                ", id_cus='" + id_cus + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
