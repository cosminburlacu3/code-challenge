public class UserDTO {
    int id ;
    String email ;
    String firstName ;
    String lastName ;
    String getFullName() {
        return firstName + " " + lastName;
    }
}