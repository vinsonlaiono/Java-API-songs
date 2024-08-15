package com.hackbright.songify.services;

import com.hackbright.songify.models.Song;
import com.hackbright.songify.respositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepo;

    public List<Song> getAllSongs(){
        return songRepo.findAll();
    }

    public Song createSong(Song song){
        return songRepo.save(song);
}

    public Song findSong(Long id) {
        Optional<Song> optionalSong = songRepo.findById(id);
        if(optionalSong.isPresent()){
            return optionalSong.get();
        } else {
            return null;
        }
    }
    public void updateSong(Song song){
        songRepo.save(song);
    }
    public void deleteSong(Long id){
        songRepo.deleteById(id);
    }

    public List<Song> findByArtist(String artist) {
        return songRepo.findByArtistContaining(artist);
    }

    public List<Song> getTopTenSongs() {
        return songRepo.findByOrderByRatingDesc(Limit.of(10));
    }
}
