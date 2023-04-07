package com.digdes.school;
import java.util.*;

public class DataBase {
    private List<Map<String, Object>> dataBase;

    public DataBase(){
        dataBase = new ArrayList<>();
    }


    public List<Map<String, Object>> getDataBase() {
        return dataBase;
    }

    public List<Map<String, Object>> insert(String[] valuesArray) throws Exception {
        Map<String, Object> tempRow = new HashMap<>();
        tempRow.put("id", null);
        tempRow.put("lastName", null);
        tempRow.put("age", null);
        tempRow.put("cost", null);
        tempRow.put("active", null);
        for (String keyValuePairString : valuesArray){
            String[] keyValuePair = keyValuePairString.split("=");
            String key = keyValuePair[0];
            String value = keyValuePair[1];
            switch (key.toLowerCase()) {
                case ("'id'"):
                    tempRow.put("id", Integer.parseInt(value));
                    break;
                case ("'lastname'"):
                    value = value.replaceAll("'", "");
                    tempRow.put("lastName", value);
                    break;
                case ("'age'"):
                    tempRow.put("age", Long.parseLong(value));
                    break;
                case ("'cost'"):
                    tempRow.put("cost", Double.parseDouble(value));
                    break;
                case("'active'"):
                    tempRow.put("active", Boolean.parseBoolean(value));
                    break;
                default:
                    throw new Exception("Unreal field in Map<String, Object>" +
                            "(\"id\", \"lastName\", \"age\", \"cost\", \"active\")." +
                            " Your field: " + key);
            }
        }

        dataBase.add(tempRow);
        List<Map<String, Object>> tempDateBase = new ArrayList<>();
        tempDateBase.add(tempRow);
        printDataBase(tempDateBase);
        return tempDateBase;
    }

    public List<Map<String, Object>> select(String[] conditions) throws Exception {
        List<Map<String, Object>> tempDateBase = new ArrayList<>();
        if (conditions[0] == ""){
            tempDateBase = dataBase;
            printDataBase(tempDateBase);
            return tempDateBase;
        }
        for (Map<String, Object> row : dataBase){
            Boolean addRow = false;
            for (String condition : conditions){
                if (condition.trim().contains("and")) {
                    String[] operands = condition.split("and");
                    for (String operand : operands){
                        if (!isEqual(row, getFOV(operand))){
                            addRow = false;
                            break;
                        }
                        else
                            addRow = true;
                    }
                    if (addRow) {
                        tempDateBase.add(row);
                        break;
                    }
                    continue;
                }
                addRow = isEqual(row, getFOV(condition));
                if (addRow) {
                    tempDateBase.add(row);
                    break;
                }
            }
        }
        printDataBase(tempDateBase);
        return tempDateBase;
    }

    public List<Map<String, Object>> delete(String[] conditions) throws Exception{
        List<Map<String, Object>> tempDateBase = new ArrayList<>();
        if (conditions[0] == ""){
            throw new Exception("Unreal conditions for delete row");
        }
        for (String condition : conditions){
            if (condition.trim().contains("and")){
                String[] operands = condition.split("and");
                for (Map<String, Object> row : dataBase){
                    Boolean deleteRow = true;
                    for (String operand : operands){
                        if (!isEqual(row, getFOV(operand))){
                            deleteRow = false;
                        }
                    }
                    if (dataBase.contains(row) && deleteRow){
                        tempDateBase.add(row);
                    }
                }
                continue;
            }
            for (Map<String, Object> row : dataBase){
                if (isEqual(row, getFOV(condition)))
                    if (dataBase.contains(row)){
                        tempDateBase.add(row);
                    }
            }
        }
        for (Map<String, Object> tempRow : tempDateBase) {
            dataBase.remove(tempRow);
            System.out.println(tempRow.toString());
        }
        return tempDateBase;
    }

    public List<Map<String, Object>> update(String[] conditions, String[] values) throws Exception{
        List<Map<String, Object>> tempDateBase = new ArrayList<>();
        if (conditions[0] == "") {
            for (Map<String, Object> row : dataBase) {
                Boolean updateRow = false;
                for (String value : values) {
                    Map<String, Object> newKeyValuePair = getFOV(value);
                    if (!isEqual(row, newKeyValuePair)) {
                        putValueForKey(row, newKeyValuePair.get("field").toString(), newKeyValuePair.get("value").toString());
                        updateRow = true;
                    }
                }
                if (updateRow)
                    tempDateBase.add(row);
            }
            printDataBase(tempDateBase);
            return tempDateBase;
        }
        for (Map<String, Object> row : dataBase){
            Boolean updateRow = false;
            for (String condition : conditions){
                String[] operands;
                if (condition.trim().contains("and")) {
                    operands = condition.split("and");
                    for (String operand : operands){
                        if (!isEqual(row, getFOV(operand))){
                            updateRow = false;
                            break;
                        }
                        else
                            updateRow = true;
                    }
                    if (updateRow)
                        break;
                    continue;
                }
                updateRow = isEqual(row, getFOV(condition));
                if (updateRow)
                    break;
            }
            if (updateRow){
                Boolean updated = false;
                for (String value: values){
                    Map<String, Object> newKeyValuePair = getFOV(value);
                    if (!isEqual(row, newKeyValuePair)){
                        putValueForKey(row, newKeyValuePair.get("field").toString(), newKeyValuePair.get("value").toString());
                        updated = true;
                    }
                }
                if (updated)
                    tempDateBase.add(row);
            }
        }
        printDataBase(tempDateBase);
        return tempDateBase;
    }

    public void printDataBase(List<Map<String, Object>> dataBase){
        for (Map<String, Object> row : dataBase){
            System.out.println(row.toString());
        }
    }


    public Map<String, Object> getFOV(String string) throws Exception{
        Map<String, Object> tempRow = new HashMap<>();
        if (string.contains("!=")){
            String[] fields = string.split("!=");
            tempRow.put("field", fields[0].trim());
            tempRow.put("operator", "!=");
            tempRow.put("value", fields[1].trim());
            return tempRow;
        }
        if (string.contains(">=")){
            String[] fields = string.split(">=");
            tempRow.put("field", fields[0].trim());
            tempRow.put("operator", ">=");
            tempRow.put("value", fields[1].trim());
            return tempRow;
        }
        if (string.contains("<=")){
            String[] fields = string.split("<=");
            tempRow.put("field", fields[0].trim());
            tempRow.put("operator", "<=");
            tempRow.put("value", fields[1].trim());
            return tempRow;
        }
        if (string.contains("<")){
            String[] fields = string.split("<");
            tempRow.put("field", fields[0].trim());
            tempRow.put("operator", "<");
            tempRow.put("value", fields[1].trim());
            return tempRow;
        }
        if (string.contains(">")){
            String[] fields = string.split(">");
            tempRow.put("field", fields[0].trim());
            tempRow.put("operator", ">");
            tempRow.put("value", fields[1].trim());
            return tempRow;
        }
        if (string.contains("=")){
            String[] fields = string.split("=");
            tempRow.put("field", fields[0].trim());
            tempRow.put("operator", "=");
            tempRow.put("value", fields[1].trim());
            return tempRow;
        }
        if (string.contains("ilike")){
            String[] fields = string.split("ilike");
            tempRow.put("field", fields[0].trim());
            tempRow.put("operator", "iLike");
            tempRow.put("value", fields[1].trim());
            return tempRow;
        }
        if (string.contains("like")){
            String[] fields = string.split("like");
            tempRow.put("field", fields[0].trim());
            tempRow.put("operator", "like");
            tempRow.put("value", fields[1].trim());
            return tempRow;
        }
        throw new Exception("Unreal operator, field or value. Check parameters!");
    }

    public boolean isEqual(Map<String, Object> row, Map<String, Object> FOV) throws Exception{
        Object firstValue = getValueForKey(row, FOV.get("field").toString());
        Object secondValue = FOV.get("value");
        if (firstValue == null && FOV.get("operator").toString().equalsIgnoreCase("!="))
            return true;
        if (firstValue == null && !FOV.get("operator").toString().equalsIgnoreCase("!="))
            return false;
        if (secondValue.toString().equalsIgnoreCase("null") && firstValue != null)
            return false;
        if (secondValue.toString().equalsIgnoreCase("null") && firstValue == null)
            return true;
        if ((FOV.get("field").toString().equalsIgnoreCase("'id'") || (FOV.get("field").toString().equalsIgnoreCase("'age'"))) && (tryParseLong(secondValue.toString()) != null)){
            switch (FOV.get("operator").toString()){
                case ("!="):
                    return (!((Long) firstValue).equals(tryParseLong(secondValue.toString())));
                case (">="):
                    return (((Long) firstValue) >= tryParseLong(secondValue.toString()));
                case ("<="):
                    return (((Long) firstValue) <= tryParseLong(secondValue.toString()));
                case ("<"):
                    return (((Long) firstValue) < tryParseLong(secondValue.toString()));
                case (">"):
                    return (((Long) firstValue) > tryParseLong(secondValue.toString()));
                case ("="):
                    return (((Long) firstValue).equals(tryParseLong(secondValue.toString())));
                default:
                    throw new Exception("Unreal operation for type (Long)" + FOV.get("operator"));
            }
        }

        else if ((FOV.get("field").toString().equalsIgnoreCase("'cost'")) && tryParseDouble(secondValue.toString()) != null){
            switch (FOV.get("operator").toString()){
                case ("!="):
                    return (!(tryParseDouble(firstValue.toString()).equals(tryParseDouble(secondValue.toString()))));
                case (">="):
                    return (tryParseDouble(firstValue.toString()) >= (tryParseDouble(secondValue.toString())));
                case ("<="):
                    return (tryParseDouble(firstValue.toString()) <= (tryParseDouble(secondValue.toString())));
                case ("<"):
                    return (tryParseDouble(firstValue.toString()) < (tryParseDouble(secondValue.toString())));
                case (">"):
                    return (tryParseDouble(firstValue.toString()) > (tryParseDouble(secondValue.toString())));
                case ("="):
                    return ((tryParseDouble(firstValue.toString())).equals(tryParseDouble(secondValue.toString())));
                default:
                    throw new Exception("Unreal operation for type (Double)" + FOV.get("operator"));
            }
        }

        else if ((FOV.get("field").toString().equalsIgnoreCase("'active'")) && tryParseBoolean(secondValue.toString()) != null){
            switch (FOV.get("operator").toString()){
                case ("!="):
                    return (!(tryParseBoolean(firstValue.toString())).equals(tryParseBoolean(secondValue.toString())));
                case ("="):
                    return ((tryParseBoolean(firstValue.toString())).equals(tryParseBoolean(secondValue.toString())));
                default:
                    throw new Exception("Unreal operation for type (Boolean)" + FOV.get("operator"));
            }
        }

        else if ((FOV.get("field").toString().equalsIgnoreCase("'lastname'")) && (secondValue instanceof String)) {
            switch (FOV.get("operator").toString()) {
                case ("!="):
                    return (!((String) firstValue).equals((String) ((String) secondValue).replaceAll("'", "")));
                case ("like"):
                    return (like(firstValue.toString(), secondValue.toString().replaceAll("'", "")));
                case ("iLike"):
                    return (iLike(firstValue.toString(), secondValue.toString().replaceAll("'", "")));
                case ("="):
                    return ((firstValue).equals(secondValue.toString().replaceAll("'", "")));
                default:
                    throw new Exception("Unreal operation for type (String)" + FOV.get("operator"));
            }
        }

        throw new Exception("Unreal type of value in conditions");
    }

    public Object getValueForKey(Map<String, Object> row, String field) throws Exception{
        switch (field.toLowerCase()) {
            case ("'id'"):
                return Long.parseLong(row.get("id").toString());
            case ("'lastname'"):
                return row.get("lastName");
            case ("'age'"):
                return row.get("age");
            case ("'cost'"):
                return row.get("cost");
            case("'active'"):
                return row.get("active");
            default:
                throw new Exception("Unreal field in Map<String, Object>" +
                        "(\"id\", \"lastName\", \"age\", \"cost\", \"active\")." +
                        " Your field: " + field);
        }
    }

    public void putValueForKey(Map<String, Object> row, String field, String value) throws Exception{
        switch (field.toLowerCase()) {
            case ("'id'"):
                if (value.equalsIgnoreCase("null")){
                    row.put("id", null);
                    break;
                }
                row.put("id", Long.parseLong(value));
                break;
            case ("'lastname'"):
                if (value.equalsIgnoreCase("null")){
                    row.put("lastName", null);
                    break;
                }
                value = value.replaceAll("'", "");
                row.put("lastName", value);
                break;
            case ("'age'"):
                if (value.equalsIgnoreCase("null")){
                    row.put("age", null);
                    break;
                }
                row.put("age", Long.parseLong(value));
                break;
            case ("'cost'"):
                if (value.equalsIgnoreCase("null")){
                    row.put("cost", null);
                    break;
                }
                row.put("cost", Double.parseDouble(value));
                break;
            case("'active'"):
                if (value.equalsIgnoreCase("null")){
                    row.put("active", null);
                    break;
                }
                row.put("active", Boolean.parseBoolean(value));
                break;
            default:
                throw new Exception("Unreal field in Map<String, Object>" +
                        "(\"id\", \"lastName\", \"age\", \"cost\", \"active\")." +
                        " Your field: " + field);
        }
    }

    public boolean like(String string, String sample){
        if (sample.startsWith("%") && sample.endsWith("%")){
            String subString = sample.replace("%", "");
            return string.contains(subString);
        }
        else if (sample.startsWith("%")) {
            String subString = sample.substring(sample.indexOf("%") + 1);
            return string.endsWith(subString);
        }
        else if (sample.endsWith("%")){
            String subString = sample.substring(0, sample.lastIndexOf("%") - 1);
            return string.startsWith(subString);
        }
        else return sample.equals(string);
    }

    public boolean iLike(String string, String sample){
        string = string.toLowerCase();
        sample = sample.toLowerCase();
        if (sample.startsWith("%") && sample.endsWith("%")){
            String subString = sample.replace("%", "");
            return string.contains(subString);
        }
        else if (sample.startsWith("%")) {
            String subString = sample.substring(sample.indexOf("%") + 1);
            return string.endsWith(subString);
        }
        else if (sample.endsWith("%")){
            String subString = sample.substring(0, sample.lastIndexOf("%") - 1);
            return string.startsWith(subString);
        }
        else return sample.equals(string);
    }

    public Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public Long tryParseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public Boolean tryParseBoolean(String value){
        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
