package com.example.admin.presentation.service;
//package com.aptech.mymusic.presentation.service;
//
//import com.aptech.mymusic.domain.entities.Enums;
//import com.aptech.mymusic.domain.entities.Song;
//import com.aptech.mymusic.domain.repository.*;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class ApiService {
//
//    private final AdsSongRepository adsSongRepository;
//    private final AlbumRepository albumRepository;
//    private final CategoryRepository categoryRepository;
//    private final PlaylistRepository playlistRepository;
//    private final SongRepository songRepository;
//    private final TopicRepository topicRepository;
//    private final EntityManager em;
//
//    public ApiService(AdsSongRepository adsSongRepository, AlbumRepository albumRepository, CategoryRepository categoryRepository, PlaylistRepository playlistRepository, SongRepository songRepository, TopicRepository topicRepository, EntityManager em) {
//        this.adsSongRepository = adsSongRepository;
//        this.albumRepository = albumRepository;
//        this.categoryRepository = categoryRepository;
//        this.playlistRepository = playlistRepository;
//        this.songRepository = songRepository;
//        this.topicRepository = topicRepository;
//        this.em = em;
//    }
//
//    public <T> List<T> getRandData(@NotNull Class<T> cls, int limit) {
//        String clsName = cls.getSimpleName();
//        return em.createQuery("SELECT e FROM " + clsName + " e ORDER BY RAND()", cls)
//                .setMaxResults(limit)
//                .getResultList();
//    }
//
//    public List<Song> getNewlyReleasedMusic() {
//        return em.createQuery("SELECT s FROM Song s WHERE s.status = :status ORDER BY s.id DESC", Song.class)
//                .setParameter("status", Enums.Status.ACTIVE)
//                .setMaxResults(10)
//                .getResultList();
//    }
//
//    public List<Song> getAllSongFrom(String type, String id) {
//        String query = "SELECT s FROM Song s WHERE s.status = :status AND FUNCTION('JSON_CONTAINS', s." + type + "Ids, :id) = 1 ORDER BY s.id DESC";
//        return em.createQuery(query, Song.class)
//                .setParameter("status", Enums.Status.ACTIVE)
//                .setParameter("id", id)
//                .getResultList();
//    }
//
//    public List<Song> getSuggestSong(Long id, Collection<Long> listIds, int limit) {
//        Song song = getSongRepository().findById(id).orElse(null);
//        if (song == null) {
//            return Collections.emptyList();
//        }
//        String query = "SELECT DISTINCT s FROM Song s " + "WHERE " +
//                "s.id NOT IN :listIds AND " +
//                "s.status = :status AND ( " +
//                "FUNCTION('JSON_OVERLAPS', s.albumIds, :albumIds) = 1 OR " +
//                "FUNCTION('JSON_OVERLAPS', s.categoryIds, :categoryIds) = 1 OR " +
//                "FUNCTION('JSON_OVERLAPS', s.playlistIds, :playlistIds) = 1 " +
//                ") ORDER BY RAND()";
//        return em.createQuery(query, Song.class)
//                .setParameter("listIds", listIds)
//                .setParameter("status", Enums.Status.ACTIVE)
//                .setParameter("albumIds", song.getAlbumIds())
//                .setParameter("categoryIds", song.getCategoryIds())
//                .setParameter("playlistIds", song.getPlaylistIds())
//                .setMaxResults(limit)
//                .getResultList();
//    }
//
//    public List<Song> searchSongByName(String nameSong) {
//        nameSong = nameSong.toUpperCase();
//        List<String> keySearches = Arrays.stream(nameSong.split(" "))
//                .filter(s1 -> s1 != null && !s1.trim().isEmpty())
//                .map(String::trim)
//                .collect(Collectors.toList());
//        if (keySearches.size() != 1) {
//            keySearches.add(0, nameSong);
//        }
//        List<String> tmp = new ArrayList<>();
//        for (int i = 0; i < keySearches.size(); i++) {
//            tmp.add(String.format("upper(s.name) like concat('%%', :search_name_%s, '%%')", i));
//            tmp.add(String.format("upper(s.singerName) like concat('%%', :search_singer_name_%s, '%%')", i));
//        }
//        String conditions = String.join(" or ", tmp);
//
//        String query = "SELECT DISTINCT s FROM Song s where s.status = :status and (" + conditions + ")";
//
//        TypedQuery<Song> q = em.createQuery(query, Song.class);
//        for (int i = 0; i < keySearches.size(); i++) {
//            q.setParameter("search_name_" + i, keySearches.get(i));
//            q.setParameter("search_singer_name_" + i, keySearches.get(i));
//        }
//
//        return q.setParameter("status", Enums.Status.ACTIVE)
//                .setMaxResults(10)
//                .getResultList();
//    }
//
//    public AdsSongRepository getAdsSongRepository() {
//        return adsSongRepository;
//    }
//
//    public AlbumRepository getAlbumRepository() {
//        return albumRepository;
//    }
//
//    public CategoryRepository getCategoryRepository() {
//        return categoryRepository;
//    }
//
//    public PlaylistRepository getPlaylistRepository() {
//        return playlistRepository;
//    }
//
//    public SongRepository getSongRepository() {
//        return songRepository;
//    }
//
//    public TopicRepository getTopicRepository() {
//        return topicRepository;
//    }
//
//}
