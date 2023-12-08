package hv.hoviet.itpostbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {
    private String name;
    private String nick_name;
    private String phone;
    private String email;
    private String pass_word;
    private String avatar_link;
    private String banner_link;
}
