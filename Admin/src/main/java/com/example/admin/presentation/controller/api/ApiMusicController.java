package com.example.admin.presentation.controller.api;//package com.aptech.mymusic.presentation.controller.api;
//
//import com.aptech.mymusic.domain.entities.*;
//import com.aptech.mymusic.domain.entities.Enums.Status;
//import com.aptech.mymusic.presentation.service.ApiService;
//import com.aptech.mymusic.utils.JsonHelper;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api")
//public class ApiMusicController {
//
//    private final ApiService service;
//
//    public ApiMusicController(ApiService service) {
//        this.service = service;
//    }
//
//    @RequestMapping("/song_banner")
//    public List<AdsSong> getDataBanner() {
//        return service.getAdsSongRepository().findAll()
//                .stream()
//                .filter(adsSong -> adsSong.getSong().getStatus() == Status.ACTIVE && adsSong.getStatus() == Status.ACTIVE)
//                .collect(Collectors.toList());
//    }
//
//    ///////////////////////////////////////////////////////////////////////////
//    // RANDOM
//    ///////////////////////////////////////////////////////////////////////////
//
//    @RequestMapping("/rand_category_for_current_day")
//    public List<Category> getRandCategory() {
//        return service.getRandData(Category.class, 5);
//    }
//
//    @RequestMapping("/rand_album_for_current_day")
//    public List<Album> getRandAlbum() {
//        return service.getRandData(Album.class, 5);
//    }
//
//    @RequestMapping("/rand_playlist_for_current_day")
//    public List<Playlist> getRandPlaylist() {
//        return service.getRandData(Playlist.class, 5);
//    }
//
//    ///////////////////////////////////////////////////////////////////////////
//    // GET ALL
//    ///////////////////////////////////////////////////////////////////////////
//
//    @RequestMapping("/all_playlist")
//    public List<Playlist> getAllPlayList() {
//        return service.getPlaylistRepository().findAll();
//    }
//
//    @RequestMapping("/all_album")
//    public List<Album> getAllAlbum() {
//        return service.getAlbumRepository().findAll();
//    }
//
//    @RequestMapping("/all_topic")
//    public List<Topic> getAllTopic() {
//        return service.getTopicRepository().findAll();
//    }
//
//    @RequestMapping("/all_category_in_topic/{id}")
//    public List<Category> getAllCategoryInTopic(@PathVariable String id) {
//        return service.getCategoryRepository().getAllCategoryInTopic(id);
//    }
//
//    ///////////////////////////////////////////////////////////////////////////
//    // GET SONG
//    ///////////////////////////////////////////////////////////////////////////
//
//    @RequestMapping("/song/{id}")
//    public Song getSong(@PathVariable String id) {
//        return service.getSongRepository().findById(Long.valueOf(id)).orElse(null);
//    }
//
//    @RequestMapping("/newly_released_music")
//    public List<Song> getNewlyReleasedMusic() {
//        return service.getNewlyReleasedMusic();
//    }
//
//    @RequestMapping("/all_song_from/{type}/{id}")
//    public List<Song> getAllSongFrom(@PathVariable String type, @PathVariable String id) {
//        if (type == null || !Arrays.asList("album", "category", "playlist").contains(type.toLowerCase())) {
//            return Collections.emptyList();
//        }
//        return service.getAllSongFrom(type, id);
//    }
//
//    /**
//     * @param id             songID
//     * @param currentSongIds the blacklist song ids
//     * @param limit          limit
//     * @return list song if related
//     */
//    @PostMapping("/get_suggest_song")
//    public List<Song> getSuggestSong(@RequestParam(name = "id") Long id,
//                                     @RequestParam(name = "current_song_ids", required = false) String currentSongIds,
//                                     @RequestParam(name = "limit", required = false) Integer limit) {
//        if (id == null) return Collections.emptyList();
//
//        Set<Long> listIds;
//        try {
//            listIds = JsonHelper.jsonToSet(currentSongIds, Long.class);
//            if (listIds.isEmpty()) {
//                listIds.add(id);
//            }
//        } catch (Throwable t) {
//            listIds = Collections.singleton(id);
//        }
//        return service.getSuggestSong(id, listIds, limit == null || limit < 0 ? 10 : limit);
//    }
//
//    @PostMapping("/search_song")
//    public List<Song> searchSongByName(@RequestParam(name = "name") String nameSong) {
//        return service.searchSongByName(nameSong);
//    }
//
//}
