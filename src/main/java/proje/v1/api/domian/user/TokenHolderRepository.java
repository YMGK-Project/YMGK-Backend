package proje.v1.api.domian.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenHolderRepository extends JpaRepository<TemporaryTokenHolder, String> {

}
