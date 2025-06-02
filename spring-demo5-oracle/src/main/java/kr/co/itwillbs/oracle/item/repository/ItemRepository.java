package kr.co.itwillbs.oracle.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.itwillbs.oracle.item.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
