import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserClient {
    public static void main(String[] args) {
        queryUsersAPI();
        tests();
    }
    public static List<UserDTO> queryUsersAPI(){
        List<UserDTO> users = new ArrayList<UserDTO>();

        try {
            URL url = new URL("https://reqres.in/api/users"); 
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
            conn.setRequestMethod("GET"); 
            conn.connect(); 

            int responseCode = conn.getResponseCode(); 

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner scanner = new Scanner(url.openStream());

                while(scanner.hasNext()) {
                    String inline = scanner.nextLine();
                    JSONParser parser = new JSONParser(); 
                    JSONObject parsedJson = (JSONObject)parser.parse(inline);
                    JSONArray dataArray = (JSONArray) parsedJson.get("data");
                    
                    for(int i = 0; i < dataArray.size(); i++) {
                        JSONObject jsonObj = (JSONObject)dataArray.get(i);
                        Integer id = Integer.parseInt(jsonObj.get("id").toString());
                        String firstName = jsonObj.get("first_name").toString();
                        String lastName = jsonObj.get("last_name").toString();
                        String email = jsonObj.get("email").toString();
                        UserDTO user = new UserDTO();
                        
                        user.id = id;
                        user.firstName = firstName;
                        user.lastName = lastName;
                        user.email = email;
                        
                        users.add(user);

                        System.out.println(user.getFullName());
                    }
                }
                scanner.close();
            }
        } 
        catch (Exception e) {
            System.out.println("Error!: \n" + e);
        }
        return users;
    }

    public static void tests() {
        List<UserDTO> result = UserClient.queryUsersAPI();
        String firstname = result.get(0).firstName;

        if (firstname.equals("George")) {  //comparing content not objects !
            System.out.println("PASSED. This test passed!");
        }
        else {
            System.out.println("Failed!");
        }

        if (result.size() == 6) {
            System.out.println("PASSED. This test passed!");
        }
        else {
            System.out.println("Failed!");
        }
    }

}