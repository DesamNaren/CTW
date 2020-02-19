package com.example.twdinspection.gcc.source.reports;

import com.example.twdinspection.gcc.source.inspections.DrDepot.GeneralFindings;
import com.example.twdinspection.gcc.source.inspections.DrDepot.HoardingsBoards;
import com.example.twdinspection.gcc.source.inspections.DrDepot.MFPRegisters;
import com.example.twdinspection.gcc.source.inspections.DrDepot.RegisterBookCertificates;
import com.example.twdinspection.gcc.source.inspections.DrDepot.StockDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrDepotInspReport {

    @SerializedName("stock_details")
    @Expose
    private DrDepotInspStockDetails stockDetails;
    @SerializedName("mfp_registers")
    @Expose
    private MFPRegisters mfpRegisters;
    @SerializedName("register_book_certificates")
    @Expose
    private RegisterBookCertificates registerBookCertificates;
    @SerializedName("hoardings_boards")
    @Expose
    private HoardingsBoards hoardingsBoards;
    @SerializedName("general_findings")
    @Expose
    private GeneralFindings generalFindings;

    public DrDepotInspStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(DrDepotInspStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public RegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public MFPRegisters getMfpRegisters() {
        return mfpRegisters;
    }

    public void setMfpRegisters(MFPRegisters mfpRegisters) {
        this.mfpRegisters = mfpRegisters;
    }

    public void setRegisterBookCertificates(RegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public HoardingsBoards getHoardingsBoards() {
        return hoardingsBoards;
    }

    public void setHoardingsBoards(HoardingsBoards hoardingsBoards) {
        this.hoardingsBoards = hoardingsBoards;
    }

    public GeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(GeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }

}
