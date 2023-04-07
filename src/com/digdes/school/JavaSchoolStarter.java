package com.digdes.school;

import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {
    private DataBase dataBase = new DataBase();
    public JavaSchoolStarter(){
    }
    public List<Map<String,Object>> execute(String request) throws Exception {
        StringParsers parsers = new StringParsers(request);
        if (parsers.getCommand().equalsIgnoreCase("insert"))
            return dataBase.insert(parsers.getValues());
        if (parsers.getCommand().equalsIgnoreCase("select"))
            return dataBase.select(parsers.getConditions());
        if (parsers.getCommand().equalsIgnoreCase("delete"))
            return dataBase.delete(parsers.getConditions());
        if (parsers.getCommand().equalsIgnoreCase("update"))
            return dataBase.update(parsers.getConditions(), parsers.getValues());
        throw new Exception("Unreal request check command list: select, update, delete, insert!");
    }
}

