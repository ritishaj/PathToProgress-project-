package persistence;

import org.json.JSONObject;


public interface Writable {
    // EFFECTS: returns this as a JSON Object

    // Method was taken from Writable
    // in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    JSONObject toJson();

}
