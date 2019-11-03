package proje.v1.api.domian.secretary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
}
