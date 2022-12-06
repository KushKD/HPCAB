package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class BranchPojo implements Serializable {

    private String BranchName;
    private String BranchID;

    public String getBranchName() {
        return BranchName;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    @Override
    public String toString() {
        return BranchName;
    }
}
