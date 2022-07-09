package com.gm.fgcf.api.model;

import java.util.ArrayList;

public class FormMetadataPiece {
    private String type;
    private String name;
    private Integer placement;
    private String formName;
    private String formCreationDate;
    private String formContentId;
    private ArrayList<String> options;

    public FormMetadataPiece() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlacement() {
        return placement;
    }

    public void setPlacement(Integer placement) {
        this.placement = placement;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormCreationDate() {
        return formCreationDate;
    }

    public void setFormCreationDate(String formCreationDate) {
        this.formCreationDate = formCreationDate;
    }

    public String getFormContentId() {
        return formContentId;
    }

    public void setFormContentId(String formContentId) {
        this.formContentId = formContentId;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
