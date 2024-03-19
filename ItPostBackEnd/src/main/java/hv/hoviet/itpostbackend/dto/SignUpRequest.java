package hv.hoviet.itpostbackend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequest {
    private String name;
    private LocalDate dob;
    private String phone;
    private String email;
    private String pass_word;
}
