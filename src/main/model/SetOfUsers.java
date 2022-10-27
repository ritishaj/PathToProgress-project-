package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Storable;

import java.util.Collection;
import java.util.HashSet;

public class SetOfUsers implements Storable {
    Collection<User> users;

    // EFFECTS: creates a new set of users
    public SetOfUsers() {
        users = new HashSet<User>();
    }

    // MODIFIES: this
    // EFFECTS: adds a user to the set of users
    public void addUser(User user) {
        users.add(user);
    }


    // EFFECTS: return true if user is in the set of users, false if not
    public boolean hasUser(User user) {
        return users.contains(user);
    }


    // EFFECTS:  return true if a user with given username and password is in the set of users, false if not
    public boolean validateUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())
                    &&
                    u.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the name of user that matches with given username and password, otherwise return null
    public String getNameFromLogin(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())
                    &&
                    u.getPassword().equals(user.getPassword())) {
                return u.getName();
            }
        }
        return null;
    }

    // EFFECTS: returns number of courses in the list of courses
    public int numOfUsers() {
        return users.size();
    }

    // returns the user that matches with given username and password, otherwise return null
    public User getAUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())
                    &&
                    u.getPassword().equals(user.getPassword())) {
                return u;
            }
        }
        return null;
    }

    //getter
    public Collection<User> getUsers() {
        return users;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", usersToJson());
        return json;
    }

    // EFFECTS: returns users in the set of users as a JSON array
    // Method was taken from WorkRoom
    // in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (User u : users) {
            jsonArray.put(u.toJson());
        }

        return jsonArray;
    }
}
