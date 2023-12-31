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

            admin.setName("Ho Viet");
            admin.setPhone("0987654321");
            admin.setEmail("admin@hv.com");
            admin.setSubscribedOn(currentTime);
            admin.setPass_word(new BCryptPasswordEncoder().encode("123"));
            userRepository.save(admin);
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        }

        Optional<User> optUser = userRepository.findByEmail("user@hv.com");
        User user = new User();
        if (!optUser.isPresent()) {
            Optional<Role> userRoleOptional = roleRepository.findByRole_name(EnumRole.ROLE_ADMIN);
            Role userRole = userRoleOptional.orElseThrow(() -> new RuntimeException("Role not found"));

            user.setName("Hồ Quốc Việt");
            user.setPhone("0123456789");
            user.setEmail("user@hv.com");
            user.setSubscribedOn(currentTime);
            user.setPass_word(new BCryptPasswordEncoder().encode("123"));
            userRepository.save(user);
            user.getRoles().add(userRole);
            userRepository.save(user);
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
        post1.setContent("Java is an object-oriented programming language. React is a JavaScript library for building user interfaces.");
        post1.setPostedOn(LocalDateTime.now());
        post1.setNumsOfLike("0");
        post1.setNumsOfComment("1");
        post1.setNumsOfDislike("0");
        post1.setNumsOfShare("0");
        post1.getTags().add(tagRepository.findByTagName("Java"));
        post1.getTags().add(tagRepository.findByTagName("React"));
        post1.setUser(admin);
        postRepository.save(post1);

        // add img of post 1 to database
        Img img1 = new Img();
        img1.setUpdatedOn(LocalDateTime.now());
        img1.setImg_link("https://www.google.com.vn/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
        img1.setPost(post1);
        imgRepository.save(img1);
        Img img3 = new Img();
        img3.setUpdatedOn(LocalDateTime.now());
        img3.setImg_link("https://www.google.com.vn/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
        img3.setPost(post1);
        imgRepository.save(img3);

        // add post 2 to database
        Post post2 = new Post();
        post2.setContent("Sql server is an object-oriented programming language. Sql server is a JavaScript library for building user interfaces.");
        post2.setPostedOn(LocalDateTime.now());
        post2.setNumsOfLike("0");
        post2.setNumsOfComment("0");
        post2.setNumsOfDislike("0");
        post2.setNumsOfShare("0");
        post2.getTags().add(tagRepository.findByTagName("Sql server"));
        post2.setUser(user);
        postRepository.save(post2);

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

    public static void main(String[] args) {
        SpringApplication.run(ItPostBackEndApplication.class, args);
    }

}
