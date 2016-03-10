package com.example.zjb.bamin.models.about_order;

import java.util.List;

/**
 * Created by zjb on 2016/2/26.
 * 用户的所有订单号
 */
public class AccountOrder {

    /**
     * orders : [{"id":136,"date":1457574001000,"bookLogAID":"2016-03-10-3ddc2096-8345-4f17-b8b5-898bf886290d","account_id":4,"redEnvelope_id":null,"phone":"15871105320"},{"id":135,"date":1457571087000,"bookLogAID":"2016-03-10-6b7b4092-acc0-4521-ab4f-42db1689b880","account_id":4,"redEnvelope_id":null,"phone":"15871105320"},{"id":134,"date":1457571059000,"bookLogAID":"2016-03-10-c1e22807-61f8-480b-8796-ef602ea23bb7","account_id":4,"redEnvelope_id":null,"phone":"15871105320"},{"id":133,"date":1457571034000,"bookLogAID":"2016-03-10-7d2f40ad-a0bd-47b9-93f1-4ede9ab255b7","account_id":4,"redEnvelope_id":null,"phone":"15871105320"},{"id":132,"date":1457570987000,"bookLogAID":"2016-03-10-e6e5c426-d5ca-4619-a977-fc4c0bbd44f8","account_id":4,"redEnvelope_id":null,"phone":"15871105320"},{"id":131,"date":1457570596000,"bookLogAID":"2016-03-10-3826ef81-98a9-4342-9f4c-785eec7ee759","account_id":4,"redEnvelope_id":null,"phone":"15871105320"},{"id":117,"date":1457403305000,"bookLogAID":"2016-03-08-90dc104c-2bd6-4c48-af0f-aa1a6c3f31c9","account_id":4,"redEnvelope_id":null,"phone":"15871105320"},{"id":115,"date":1457402156000,"bookLogAID":"2016-03-08-4a330f61-e8e5-4211-ac49-22bdbe9e48e3","account_id":4,"redEnvelope_id":null,"phone":"15871105320"}]
     * pages : 6
     */

    private int pages;
    /**
     * id : 136
     * date : 1457574001000
     * bookLogAID : 2016-03-10-3ddc2096-8345-4f17-b8b5-898bf886290d
     * account_id : 4
     * redEnvelope_id : null
     * phone : 15871105320
     */

    private List<OrdersEntity> orders;

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setOrders(List<OrdersEntity> orders) {
        this.orders = orders;
    }

    public int getPages() {
        return pages;
    }

    public List<OrdersEntity> getOrders() {
        return orders;
    }

    public static class OrdersEntity {
        private int id;
        private long date;
        private String bookLogAID;
        private int account_id;
        private Object redEnvelope_id;
        private String phone;

        public void setId(int id) {
            this.id = id;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public void setBookLogAID(String bookLogAID) {
            this.bookLogAID = bookLogAID;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public void setRedEnvelope_id(Object redEnvelope_id) {
            this.redEnvelope_id = redEnvelope_id;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getId() {
            return id;
        }

        public long getDate() {
            return date;
        }

        public String getBookLogAID() {
            return bookLogAID;
        }

        public int getAccount_id() {
            return account_id;
        }

        public Object getRedEnvelope_id() {
            return redEnvelope_id;
        }

        public String getPhone() {
            return phone;
        }
    }
}
