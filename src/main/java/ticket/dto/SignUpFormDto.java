package ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SignUpFormDto {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Length(min = 4,message = "아이디는 4글자 이상으로 작성해주세요")
    private String id;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(min=8,max=16,message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @Builder
    public SignUpFormDto(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
