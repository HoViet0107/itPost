package hv.hoviet.itpostbackend.respository;

import hv.hoviet.itpostbackend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Integer> {
    public Tag existsTagByTagName(String tag_name);

    public Tag findByTagName (String tag_name);
}
