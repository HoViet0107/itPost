package hv.hoviet.itpostbackend.respository;

import hv.hoviet.itpostbackend.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<Img, Integer> {
}
