package org.example.edufymusic.repository;

import org.example.edufymusic.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a LEFT JOIN FETCH a.songs WHERE a.id = :album_id")
    Optional<Album> findByIdWithSongs(@Param("album_id") Long id);
}
