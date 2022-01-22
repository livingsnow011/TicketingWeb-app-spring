package ticket.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String id;
    private String name;
    private String pwd;

    private Long userId;
    private String crypted_pwd;
    private LocalDateTime created_date;
    private Integer current_point;
    private String role;
}
