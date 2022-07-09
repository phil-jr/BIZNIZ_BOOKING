package com.fpo.app.api.model;

import java.util.ArrayList;

public class FormInputDatapiece {
     private String type;
     private String name;
     private String placement;
     private ArrayList<String> options;

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

     public String getPlacement() {
          return placement;
     }

     public void setPlacement(String placement) {
          this.placement = placement;
     }

     public ArrayList<String> getOptions() {
          return options;
     }

     public void setOptions(ArrayList<String> selectOptions) {
          this.options = selectOptions;
     }
}
