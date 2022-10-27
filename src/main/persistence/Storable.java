package persistence;

import org.json.JSONObject;


public interface Storable {
    // EFFECTS: returns this as a JSON Object

    // CITATION: Method was taken from Writable interface:
    // in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    JSONObject toJson();

}
