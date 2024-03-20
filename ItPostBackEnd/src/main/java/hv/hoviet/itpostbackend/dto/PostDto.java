package hv.hoviet.itpostbackend.dto;

import hv.hoviet.itpostbackend.model.Comment;
import hv.hoviet.itpostbackend.model.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PostDto {
    private Integer id;
    private String content;
    private LocalDateTime postedOn;
    private String numsOfLike;
    private String numsOfDislike;
    private String numsOfComment;
    private String numsOfShare;
    private List<Comment> comments = new ArrayList<>();
    private Set<Tag> tag = new HashSet<>();
}
