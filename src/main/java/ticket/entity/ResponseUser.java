package ticket.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseUser {
    private String id;
    private String name;
    private Long userId;
    private LocalDate created_date;
    private Integer current_point;
    private String role;
}
