package com.hackbright.songify.respositories;

import com.hackbright.songify.models.Song;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long> {
    List<Song> findAll();
    List<Song> findByArtistContaining(String search);
    List<Song> findByOrderByRatingDesc(Limit limit);
}
