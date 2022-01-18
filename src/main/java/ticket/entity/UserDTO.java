package ticket.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String id;
    private String name;
    private String pwd;

    private Long user_id;
    private String crypted_pwd;
    private LocalDateTime created_date;
    private int current_point;
}
