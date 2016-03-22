package com.example.zjb.bamin.Cdachezuche.models;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SingleCarInfo
{

    /**
     * id : 12
     * licensePlate : 闽D1T689
     * model : 长安
     * type : SUV
     * box : 3厢
     * pailiang : 1.2L
     * seat : 5
     * zidong : null
     * color : 银白色
     * engineCode : AA-BB-C-D
     * mileage : 10050.1
     * maintenanceMileage : 3000.0
     * status : 0
     * deposit : 1000.0
     * buyDate : 2015-2-1
     * inspection : 2015-4-3
     * image : null
     * note : 长安长安
     * planId : 1
     * lei : 0
     * store_id : null
     */

    private CarEntity car;
    /**
     * id : 1
     * name : 公务一型
     * price : 200.0
     * unitMileage : 100.0
     * outMileage : 10.0
     * outTime : 3.0
     * flag : 0
     * jijia : null
     */

    private PlanEntity plan;

    public CarEntity getCar()
    {
        return car;
    }

    public void setCar(CarEntity car)
    {
        this.car = car;
    }

    public PlanEntity getPlan()
    {
        return plan;
    }

    public void setPlan(PlanEntity plan)
    {
        this.plan = plan;
    }

    public static class CarEntity
    {
        private int id;
        private String licensePlate;
        private String model;
        private String type;
        private String box;
        private String pailiang;
        private int seat;
        private int zidong;
        private String color;
        private String engineCode;
        private double mileage;
        private double maintenanceMileage;
        private int status;
        private double deposit;
        private String buyDate;
        private String inspection;
        private String image;
        private String note;
        private int planId;
        private int lei;
        private int store_id;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getLicensePlate()
        {
            return licensePlate;
        }

        public void setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
        }

        public String getModel()
        {
            return model;
        }

        public void setModel(String model)
        {
            this.model = model;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getBox()
        {
            return box;
        }

        public void setBox(String box)
        {
            this.box = box;
        }

        public String getPailiang()
        {
            return pailiang;
        }

        public void setPailiang(String pailiang)
        {
            this.pailiang = pailiang;
        }

        public int getSeat()
        {
            return seat;
        }

        public void setSeat(int seat)
        {
            this.seat = seat;
        }

        public int getZidong()
        {
            return zidong;
        }

        public void setZidong(int zidong)
        {
            this.zidong = zidong;
        }

        public String getColor()
        {
            return color;
        }

        public void setColor(String color)
        {
            this.color = color;
        }

        public String getEngineCode()
        {
            return engineCode;
        }

        public void setEngineCode(String engineCode)
        {
            this.engineCode = engineCode;
        }

        public double getMileage()
        {
            return mileage;
        }

        public void setMileage(double mileage)
        {
            this.mileage = mileage;
        }

        public double getMaintenanceMileage()
        {
            return maintenanceMileage;
        }

        public void setMaintenanceMileage(double maintenanceMileage)
        {
            this.maintenanceMileage = maintenanceMileage;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }

        public double getDeposit()
        {
            return deposit;
        }

        public void setDeposit(double deposit)
        {
            this.deposit = deposit;
        }

        public String getBuyDate()
        {
            return buyDate;
        }

        public void setBuyDate(String buyDate)
        {
            this.buyDate = buyDate;
        }

        public String getInspection()
        {
            return inspection;
        }

        public void setInspection(String inspection)
        {
            this.inspection = inspection;
        }

        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

        public String getNote()
        {
            return note;
        }

        public void setNote(String note)
        {
            this.note = note;
        }

        public int getPlanId()
        {
            return planId;
        }

        public void setPlanId(int planId)
        {
            this.planId = planId;
        }

        public int getLei()
        {
            return lei;
        }

        public void setLei(int lei)
        {
            this.lei = lei;
        }

        public int getStore_id()
        {
            return store_id;
        }

        public void setStore_id(int store_id)
        {
            this.store_id = store_id;
        }
    }

    public static class PlanEntity
    {
        private int id;
        private String name;
        private double price;
        private double unitMileage;
        private double outMileage;
        private double outTime;
        private int flag;
        private Object jijia;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public double getPrice()
        {
            return price;
        }

        public void setPrice(double price)
        {
            this.price = price;
        }

        public double getUnitMileage()
        {
            return unitMileage;
        }

        public void setUnitMileage(double unitMileage)
        {
            this.unitMileage = unitMileage;
        }

        public double getOutMileage()
        {
            return outMileage;
        }

        public void setOutMileage(double outMileage)
        {
            this.outMileage = outMileage;
        }

        public double getOutTime()
        {
            return outTime;
        }

        public void setOutTime(double outTime)
        {
            this.outTime = outTime;
        }

        public int getFlag()
        {
            return flag;
        }

        public void setFlag(int flag)
        {
            this.flag = flag;
        }

        public Object getJijia()
        {
            return jijia;
        }

        public void setJijia(Object jijia)
        {
            this.jijia = jijia;
        }
    }
}
