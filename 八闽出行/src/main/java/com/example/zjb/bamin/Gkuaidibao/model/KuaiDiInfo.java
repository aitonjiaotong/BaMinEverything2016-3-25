package com.example.zjb.bamin.Gkuaidibao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zjb on 2016/3/17.
 */
public class KuaiDiInfo implements Serializable {

    /**
     * EBusinessID : 1256347
     * ShipperCode : STO
     * Success : true
     * LogisticCode : 227354179135
     * State : 3
     * Traces : [{"AcceptTime":"2016-03-12 19:54:10","AcceptStation":"【收件】【上海罗泾公司】的收件员【操作部】已收件"},{"AcceptTime":"2016-03-12 20:04:44","AcceptStation":"【发件】由【上海罗泾公司】发往【上海航空部】"},{"AcceptTime":"2016-03-12 20:04:44","AcceptStation":"【装袋】【上海罗泾公司】正在进行【装袋】扫描"},{"AcceptTime":"2016-03-12 23:31:55","AcceptStation":"【发件】由【上海航空部】发往【福建厦门航空部】"},{"AcceptTime":"2016-03-14 23:08:35","AcceptStation":"【装车】【福建厦门中转部】正在进行【装车】扫描"},{"AcceptTime":"2016-03-14 23:08:35","AcceptStation":"【发件】由【福建厦门中转部】发往【福建厦门机场二部】"},{"AcceptTime":"2016-03-15 08:03:53","AcceptStation":"【到件】快件已到达【福建厦门机场二部】"},{"AcceptTime":"2016-03-15 08:42:28","AcceptStation":"【派件】【福建厦门机场二部】的派件员【朱世涛】正在派件"},{"AcceptTime":"2016-03-15 11:41:44","AcceptStation":"【签收】已签收,签收人是:【本人】"}]
     */

    private String EBusinessID;
    private String ShipperCode;
    private boolean Success;
    private String LogisticCode;
    private String State;
    /**
     * AcceptTime : 2016-03-12 19:54:10
     * AcceptStation : 【收件】【上海罗泾公司】的收件员【操作部】已收件
     */

    private List<TracesEntity> Traces;

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public void setState(String State) {
        this.State = State;
    }

    public void setTraces(List<TracesEntity> Traces) {
        this.Traces = Traces;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public String getState() {
        return State;
    }

    public List<TracesEntity> getTraces() {
        return Traces;
    }

    public static class TracesEntity implements Serializable{
        private String AcceptTime;
        private String AcceptStation;

        public TracesEntity(String acceptTime, String acceptStation) {
            AcceptTime = acceptTime;
            AcceptStation = acceptStation;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public String getAcceptStation() {
            return AcceptStation;
        }
    }
}

