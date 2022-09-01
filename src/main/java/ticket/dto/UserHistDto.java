package ticket.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ticket.constant.Role;
import ticket.entity.User;

@Getter
@Setter
public class UserHistDto {

    private String userId;

    private String name;

    private Integer currentPoint;

    private Role role;

    private static ModelMapper modelMapper = new ModelMapper();

    public static UserHistDto of(User user){
        return modelMapper.map(user, UserHistDto.class);
    }

}
