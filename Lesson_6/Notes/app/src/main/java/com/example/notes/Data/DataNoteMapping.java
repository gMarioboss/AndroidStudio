package com.example.notes.Data;

import com.example.notes.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class DataNoteMapping {

    public static Map<String, Object> toDocument(DataNote dataNote){
        Map<String, Object> result = new HashMap<>();
        result.put(Constants.TITLE, dataNote.getName());
        result.put(Constants.DESCRIPTION, dataNote.getDescription());
        result.put(Constants.DATE, dataNote.getDateTime());
        return result;
    }

    public static DataNote toDataNote(String id, Map<String, Object> doc){
        DataNote result = new DataNote((String)doc.get(Constants.TITLE),
                (String)doc.get(Constants.DESCRIPTION),
                (String)doc.get(Constants.DATE));
        result.setId(id);
        return result;
    }
}
