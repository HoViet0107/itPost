package hv.hoviet.itpostbackend;

import hv.hoviet.itpostbackend.model.*;
import hv.hoviet.itpostbackend.model.enums.EnumRole;
import hv.hoviet.itpostbackend.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class ItPostBackEndApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImgRepository imgRepository;
    @Autowired
    private CommentRepository commentRepository;

    public void run(String... args) throws Exception {
        // add role to database
        Optional<Role> roleAdmin = roleRepository.findByRole_name(EnumRole.ROLE_ADMIN);
        if (!roleAdmin.isPresent()) {
            Role role = new Role();
            role.setRole_name(EnumRole.ROLE_ADMIN);
            roleRepository.save(role);
        }
        Optional<Role> roleUser = roleRepository.findByRole_name(EnumRole.ROLE_USER);
        if (!roleUser.isPresent()) {
            Role role = new Role();
            role.setRole_name(EnumRole.ROLE_USER);
            roleRepository.save(role);
        }

        /* ***************************************************************************************** */
        // add admin and user to database
        LocalDateTime currentTime = LocalDateTime.now();
        Optional<User> isAdminExists = userRepository.findByEmail("admin@hv.com");
        User admin = new User();
        if (!isAdminExists.isPresent()) {
            Optional<Role> adminRoleOptional = roleRepository.findByRole_name(EnumRole.ROLE_ADMIN);
            Role adminRole = adminRoleOptional.orElseThrow(() -> new RuntimeException("Role not found"));
            adminRoleOptional = Optional.empty();

            Img ava1 = new Img();
            ava1.setImg_link("Ava https://a.deviantart.net/avatars-big/c/a/cathrynedelamort.jpg?4");
            ava1.setUser(admin);
            Img ava2 = new Img();
            ava2.setImg_link("Banner https://sm.ign.com/t/ign_nordic/cover/a/avatar-gen/avatar-generations_prsz.300.jpg");
            ava2.setUser(admin);

            admin.setName("Ho Viet");
            admin.setPhone("0987654321");
            admin.setEmail("admin@hv.com");
            admin.setSubscribedOn(currentTime);
            admin.setPass_word(new BCryptPasswordEncoder().encode("123"));
            userRepository.save(admin);
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
            imgRepository.save(ava1);
            imgRepository.save(ava2);
        }

        Optional<User> optUser = userRepository.findByEmail("user@hv.com");
        User user = new User();
        if (!optUser.isPresent()) {
            Optional<Role> userRoleOptional = roleRepository.findByRole_name(EnumRole.ROLE_ADMIN);
            Role userRole = userRoleOptional.orElseThrow(() -> new RuntimeException("Role not found"));
            userRoleOptional = Optional.empty();

            Img ava1 = new Img();
            ava1.setImg_link("Ava https://cdn2.cellphones.com.vn/1200x400/https://cdn.sforum.vn/sforum/wp-content/uploads/2023/11/avatar-vo-tri-thumbnail.jpg");
            Img ava2 = new Img();
            ava2.setImg_link("Banner https://cdn2.cellphones.com.vn/1200x400/https://cdn.sforum.vn/sforum/wp-content/uploads/2023/11/avatar-vo-tri-thumbnail.jpg");
            ava1.setUser(user);
            ava2.setUser(user);

            user.setName("Hồ Quốc Việt");
            user.setPhone("0123456789");
            user.setEmail("user@hv.com");
            user.setSubscribedOn(currentTime);
            user.setPass_word(new BCryptPasswordEncoder().encode("123"));
            userRepository.save(user);
            user.getRoles().add(userRole);
            userRepository.save(user);
            imgRepository.save(ava1);
            imgRepository.save(ava2);
        }
        /* ***************************************************************************************** */
        // add tag to database
        List<String> tagList = Arrays.asList("Java", "React", "Sql server");
        tagList.stream()
                .filter(tag -> tagRepository.findByTagName(tag) == null)
                .forEach(tag -> {
                    Tag newTag = new Tag();
                    newTag.setTagName(tag);
                    tagRepository.save(newTag);
                });

        /* ***************************************************************************************** */
        // add post 1 to database
        Post post1 = new Post();
        post1.setContent("Java is an object-oriented programming language. React is a JavaScript library for building user interfaces.\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        post1.setPostedOn(LocalDateTime.now());
        post1.setNumsOfLike("0");
        post1.setNumsOfDislike("0");
        post1.setNumsOfShare("0");
        post1.setNumsOfComment("3");
        post1.getTags().add(tagRepository.findByTagName("Java"));
        post1.getTags().add(tagRepository.findByTagName("React"));
        post1.setUser(admin);
        postRepository.save(post1);

        // add post 2 to database
        Post post2 = new Post();
        post2.setContent("Sql server is an object-oriented programming language. Sql server is a JavaScript library for building user interfaces.");
        post2.setNumsOfComment("1");
        post2 = addPostToDb(post2, user, "Sql server");

        // add post 3-10 to database
        Post duplicatePost = new Post();
        duplicatePost.setContent("Sql server is an object-oriented programming language. Sql server is a JavaScript library for building user interfaces.");
        duplicatePost.setNumsOfComment("0");
        addPostToDb(duplicatePost, user, "Sql server");
        addPostToDb(duplicatePost, user, "Sql server");
        addPostToDb(duplicatePost, user, "Sql server");
        addPostToDb(duplicatePost, user, "Sql server");
        addPostToDb(duplicatePost, user, "Sql server");
        addPostToDb(duplicatePost, user, "Sql server");
        addPostToDb(duplicatePost, user, "Sql server");
        addPostToDb(duplicatePost, user, "Sql server");

        // add img of post 1 to database
        if (
                postRepository.existsById(post1.getId()) &&
                        postRepository.existsById(post2.getId()) &&
                        userRepository.existsById(admin.getId()) &&
                        userRepository.existsById(user.getId())
        ) {
            Img img1 = new Img();
            img1.setUpdatedOn(LocalDateTime.now());
            img1.setImg_link("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/1f91b7c0-fa6f-4cfd-9a5d-639a7e206906/dgxs8k7-927c125c-1057-4d21-9f54-4e70635d7473.jpg/v1/fill/w_900,h_547,q_75,strp/legendary_couple_by_kawacy_dgxs8k7-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NTQ3IiwicGF0aCI6IlwvZlwvMWY5MWI3YzAtZmE2Zi00Y2ZkLTlhNWQtNjM5YTdlMjA2OTA2XC9kZ3hzOGs3LTkyN2MxMjVjLTEwNTctNGQyMS05ZjU0LTRlNzA2MzVkNzQ3My5qcGciLCJ3aWR0aCI6Ijw9OTAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.9kORvHgP6ZJHBaLui2x3B1x4TQbT8T1bYALSw5pIgdg");
            img1.setPost(post1);
            imgRepository.save(img1);

            Img img3 = new Img();
            img3.setUpdatedOn(LocalDateTime.now());
            img3.setImg_link("https://www.google.com.vn/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
            img3.setPost(post1);
            imgRepository.save(img3);

            // add img of post 1 to database
            Img img2 = new Img();
            img2.setUpdatedOn(LocalDateTime.now());
            img2.setImg_link("https://www.google.com.vn/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
            img2.setPost(post2);
            imgRepository.save(img2);
            Img img4 = new Img();
            img4.setUpdatedOn(LocalDateTime.now());
            img4.setImg_link("https://www.google.com.vn/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
            img4.setPost(post2);
            imgRepository.save(img4);

            /* ***************************************************************************************** */
            // add comment to database
            Comment comment1 = new Comment();
            comment1.setContent("Java is an object-oriented programming language. React is a JavaScript library for building user interfaces.");
            comment1.setCommentedOn(LocalDateTime.now());
            comment1.setReplyTo("Post " + post1.getId());
            comment1.setPost(post1);
            comment1.setUser(user);
            commentRepository.save(comment1);

            Comment comment2 = new Comment();
            comment2.setContent("Sql server is an object-oriented programming language. Sql server is a JavaScript library for building user interfaces.");
            comment2.setCommentedOn(LocalDateTime.now());
            comment2.setReplyTo("Post " + post2.getId());
            comment2.setPost(post2);
            comment2.setUser(admin);
            commentRepository.save(comment2);

            Comment comment3 = new Comment();
            comment3.setContent("Reply to comment 1.");
            comment3.setCommentedOn(LocalDateTime.now());
            comment3.setReplyTo("Cmt " + comment1.getId());
            comment3.setPost(post1);
            comment3.setUser(admin);
            commentRepository.save(comment3);
            // add img to comment 3
            Img img5 = new Img();
            img5.setUpdatedOn(LocalDateTime.now());
            img5.setImg_link("https://www.google.com.vn/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
            img5.setComment(comment3);
            imgRepository.save(img5);

            Comment comment4 = new Comment();
            comment4.setContent("Reply to comment 3.");
            comment4.setCommentedOn(LocalDateTime.now());
            comment4.setReplyTo("Cmt " + comment3.getId());
            comment4.setPost(post1);
            comment4.setUser(user);
            commentRepository.save(comment4);
        }
    }

    public Post addPostToDb(Post oriPost, User user, String tagName) {
        Post post = new Post();
        post.setContent(oriPost.getContent());
        post.setPostedOn(LocalDateTime.now());
        post.setNumsOfLike("0");
        post.setNumsOfComment(oriPost.getNumsOfComment());
        post.setNumsOfDislike("0");
        post.setNumsOfShare("0");
        post.getTags().add(tagRepository.findByTagName(tagName));
        post.setUser(user);
        postRepository.save(post);
        return post;
    }

    public static void main(String[] args) {
        SpringApplication.run(ItPostBackEndApplication.class, args);
    }

}
