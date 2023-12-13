package mymb.springboot.repository;

import mymb.springboot.entity.ERC721Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ERC721ToeknRepository extends JpaRepository<ERC721Token, Long> {
}
