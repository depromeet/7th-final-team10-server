package com.depromeet.boiledegg.bookstore.domain.repository;

import com.depromeet.boiledegg.bookstore.domain.entity.Bookstore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {

    // TODO 쿼리 성능이 좋지 않아 추후 인덱싱 작업 필요 (Spatial Index)
    @Query(
            value = "SELECT b.* " +
                    "FROM (SELECT owner, POINT(latitude MOD 180, longitude MOD 90) AS location " +
                    "    FROM Bookstore " +
                    "    WHERE id = 1) owned " +
                    "INNER JOIN Bookstore b " +
                    "ORDER BY ST_DISTANCE_SPHERE(POINT(b.latitude MOD 180, b.longitude MOD 90), owned.location)",
            nativeQuery = true
    )
    Page<Bookstore> findNearMeById(
            final Long id,
            final Pageable pageable
    );
}
