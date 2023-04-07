package com.digdes.school;

import java.util.HashMap;
import java.util.Map;

public class StringParsers {
    private String string;
    private Map<String, String> commands = new HashMap<>();

    public StringParsers(String string){
        this.string = string;
        commands.put("where", "where");
        commands.put("insert", "insert");
        commands.put("update", "update");
        commands.put("delete", "delete");
        commands.put("select", "select");
        commands.put("values", "values");
    }
    public String getString() {
        return string;
    }

    public String getCommand(){
        String[] parameters = string.trim().split(" ");
        return parameters[0];
    }

    public String[] getValues(){
        String[] parameters = string.trim().split(" ");
        String valuesString = "";
        for (String substring : parameters){
            if (substring.equalsIgnoreCase("where"))
                break;
            if (!commands.containsValue(substring.toLowerCase())){
                valuesString = valuesString.concat(substring.trim());
            }
        }
        return valuesString.split(",");
    }

    public String[] getConditions(){
        boolean conditionTemp = false;
        String conditionString = "";
        String[] parameters = string.trim().split(" ");
        for (String substring : parameters){
            if (substring.equalsIgnoreCase("where"))
                conditionTemp = true;
            if (conditionTemp && !substring.equalsIgnoreCase("where"))
                conditionString = conditionString.concat(substring.trim());
        }

        return conditionString.split("or");
    }

}
