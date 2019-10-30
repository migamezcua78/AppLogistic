package com.logistica.applogistic;

public class cSupplyPlanning {

    public  cSupplyPlanning(){

        SupplyPlanningAreaID =  "";
        LifeCycleStatusCode =  "";
        ProcurementTypeCode =  "";
        PlanningProcedureCode =  "";
        LotSizeProcedureCode =  "";
        GoodsReceiptProcessingDuration =  "";
    }

    public String  SupplyPlanningAreaID;
    public String  LifeCycleStatusCode;
    public String  ProcurementTypeCode;
    public String  PlanningProcedureCode;
    public String  LotSizeProcedureCode;
    public String  GoodsReceiptProcessingDuration;

    public String getSupplyPlanningAreaID() {
        return SupplyPlanningAreaID;
    }

    public String getLifeCycleStatusCode() {
        return LifeCycleStatusCode;
    }

    public String getProcurementTypeCode() {
        return ProcurementTypeCode;
    }

    public String getPlanningProcedureCode() {
        return PlanningProcedureCode;
    }

    public String getLotSizeProcedureCode() {
        return LotSizeProcedureCode;
    }

    public String getGoodsReceiptProcessingDuration() {
        return GoodsReceiptProcessingDuration;
    }

    public void setSupplyPlanningAreaID(String supplyPlanningAreaID) {
        SupplyPlanningAreaID = supplyPlanningAreaID;
    }

    public void setLifeCycleStatusCode(String lifeCycleStatusCode) {
        LifeCycleStatusCode = lifeCycleStatusCode;
    }

    public void setProcurementTypeCode(String procurementTypeCode) {
        ProcurementTypeCode = procurementTypeCode;
    }

    public void setPlanningProcedureCode(String planningProcedureCode) {
        PlanningProcedureCode = planningProcedureCode;
    }

    public void setLotSizeProcedureCode(String lotSizeProcedureCode) {
        LotSizeProcedureCode = lotSizeProcedureCode;
    }

    public void setGoodsReceiptProcessingDuration(String goodsReceiptProcessingDuration) {
        GoodsReceiptProcessingDuration = goodsReceiptProcessingDuration;
    }
}
