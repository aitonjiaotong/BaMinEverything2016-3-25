package com.example.zjb.bamin.Cdachezuche.models;

import java.util.List;

/**
 * Created by zjb on 2016/3/19.
 */
public class CarInfoList {

    /**
     * contains : [{"model":"本田","type":"甲壳虫","box":"3","pailiang":"1.2","seat":4,"zidong":0,"plan_id":2,"image":null,"plan":{"id":2,"name":"bbbb","price":150,"unitMileage":3000,"outMileage":9.5,"outTime":2.5,"flag":0}},{"model":"奥迪","type":"A6","box":"3","pailiang":"1.4","seat":5,"zidong":1,"plan_id":1,"image":null,"plan":{"id":1,"name":"aaaa","price":200,"unitMileage":100,"outMileage":10,"outTime":3,"flag":0}},{"model":"bbb","type":"aaa","box":"3","pailiang":"1.2","seat":4,"zidong":0,"plan_id":1,"image":null,"plan":{"id":1,"name":"aaaa","price":200,"unitMileage":100,"outMileage":10,"outTime":3,"flag":0}},{"model":"红旗","type":"E6","box":"3","pailiang":"1.1","seat":5,"zidong":0,"plan_id":3,"image":null,"plan":{"id":3,"name":"cccc","price":100,"unitMileage":100,"outMileage":8.5,"outTime":2,"flag":0}}]
     * num : 1
     */

    private int num;
    /**
     * model : 本田
     * type : 甲壳虫
     * box : 3
     * pailiang : 1.2
     * seat : 4
     * zidong : 0
     * plan_id : 2
     * image : null
     * plan : {"id":2,"name":"bbbb","price":150,"unitMileage":3000,"outMileage":9.5,"outTime":2.5,"flag":0}
     */

    private List<ContainsEntity> contains;

    public void setNum(int num) {
        this.num = num;
    }

    public void setContains(List<ContainsEntity> contains) {
        this.contains = contains;
    }

    public int getNum() {
        return num;
    }

    public List<ContainsEntity> getContains() {
        return contains;
    }

    public static class ContainsEntity {
        private String model;
        private String type;
        private String box;
        private String pailiang;
        private int seat;
        private int zidong;
        private int plan_id;
        private String image;
        /**
         * id : 2
         * name : bbbb
         * price : 150.0
         * unitMileage : 3000.0
         * outMileage : 9.5
         * outTime : 2.5
         * flag : 0
         */

        private PlanEntity plan;

        public void setModel(String model) {
            this.model = model;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setBox(String box) {
            this.box = box;
        }

        public void setPailiang(String pailiang) {
            this.pailiang = pailiang;
        }

        public void setSeat(int seat) {
            this.seat = seat;
        }

        public void setZidong(int zidong) {
            this.zidong = zidong;
        }

        public void setPlan_id(int plan_id) {
            this.plan_id = plan_id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setPlan(PlanEntity plan) {
            this.plan = plan;
        }

        public String getModel() {
            return model;
        }

        public String getType() {
            return type;
        }

        public String getBox() {
            return box;
        }

        public String getPailiang() {
            return pailiang;
        }

        public int getSeat() {
            return seat;
        }

        public int getZidong() {
            return zidong;
        }

        public int getPlan_id() {
            return plan_id;
        }

        public String getImage() {
            return image;
        }

        public PlanEntity getPlan() {
            return plan;
        }

        public static class PlanEntity {
            private int id;
            private String name;
            private double price;
            private double unitMileage;
            private double outMileage;
            private double outTime;
            private int flag;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public void setUnitMileage(double unitMileage) {
                this.unitMileage = unitMileage;
            }

            public void setOutMileage(double outMileage) {
                this.outMileage = outMileage;
            }

            public void setOutTime(double outTime) {
                this.outTime = outTime;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public double getPrice() {
                return price;
            }

            public double getUnitMileage() {
                return unitMileage;
            }

            public double getOutMileage() {
                return outMileage;
            }

            public double getOutTime() {
                return outTime;
            }

            public int getFlag() {
                return flag;
            }
        }
    }
}
