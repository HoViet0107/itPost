package hv.hoviet.itpostbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SignUpRequest {
    private String name;
    private String nick_name;
    private LocalDate dob;
    private String phone;
    private String email;
    private String pass_word;
    private String avatar_link;
    private String banner_link;
}
