package com.hackbright.songify.controllers;

import com.hackbright.songify.models.Song;
import com.hackbright.songify.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SongController {

    @Autowired
    private SongService songServ;

    @GetMapping("/")
    public String index (){
        return "index";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model){
        List<Song> songs = songServ.getAllSongs();
        model.addAttribute("songs", songs);
        return "dashboard";
    }
    @GetMapping("/search/topTen")
    public String topTen(Model model){
        List<Song> topTenSongs = songServ.getTopTenSongs();
        System.out.println(topTenSongs);
        model.addAttribute("songs", topTenSongs);
        return "topTen";
    }
    @GetMapping("/search")
    public String search(@RequestParam("artist") String artist){
        if(artist.isEmpty()){
            return "redirect:/dashboard";
        }
        return "redirect:/search/"+ artist;
    }
    @GetMapping("/search/{artist}")
    public String searchByArtist(Model model,
                                 @ModelAttribute("song") Song song,
                                 @PathVariable("artist") String artist){
        List<Song> songsByArtist = songServ.findByArtist(artist);
        model.addAttribute("songs", songsByArtist);
        model.addAttribute("artist", artist);
        return "search";
    }
    @GetMapping("/songs/{id}")
    public String showSong(Model model, @PathVariable("id") Long id){
        Song song = songServ.findSong(id);
        model.addAttribute("song", song);
        return "show";
    }
    @GetMapping("/songs/new")
    public String newSong(@ModelAttribute("song") Song song){

        return "new";
    }
    @PostMapping("/songs")
    public String create(@Validated @ModelAttribute("song") Song song,
                         BindingResult result){
        if(result.hasErrors()){
            System.out.println(song.getTitle());
            System.out.println(song.getArtist());
            return "new";
        } else {
            System.out.println(song);
            songServ.createSong(song);
            return "redirect:/dashboard";
        }
    }
    @PutMapping("/song/{id}")
    public String edit(Model model,
                       @Validated @ModelAttribute("song") Song song,
                       BindingResult results){
        if(results.hasErrors()){
            model.addAttribute("song", song);
            return "edit";
        } else {
            songServ.updateSong(song);
            return "redirect:/dashboard";
        }
    }
    @DeleteMapping("/songs/{id}")
    public String destroy(@PathVariable("id") Long id){
        songServ.deleteSong(id);
        return "redirect:/dashboard";
    }


}
