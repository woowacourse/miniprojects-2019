package techcourse.w3.woostagram.tag.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    List<HashTag> findTop10ByTag_NameContainingIgnoreCaseOrderByTag_Name(String name);

    Page<HashTag> findByTag_Name(String name, Pageable pageable);
}
