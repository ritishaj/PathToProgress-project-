package persistence;

import org.json.JSONObject;

// represents an interface with method declaration for JSON objects
// CITATION: Method was taken from Writable interface:
// in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Storable {

    // EFFECTS: returns this as a JSON Object
    JSONObject toJson();

}
