package hv.hoviet.itpostbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "img_links")
@AllArgsConstructor
@NoArgsConstructor
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private String img_link;
    /*
       định dạng avatar: Avatar <link>
       định dạng banner: Banner <link>
       ***
       post: link;
       comment: link1 <|> link2 <|> link3 <|> link4
       định dạng
    */
}
