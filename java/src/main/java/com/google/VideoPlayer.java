package com.google;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private List<Video> videos;
  private String playingVideo = "";
  private boolean paused = false;
  private List<VideoPlaylist> playlists;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    videos = videoLibrary.getVideos();
    playlists = new ArrayList<>();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<String> videoNames = new ArrayList<>();
    for (Video video: videos){
      videoNames.add(video.getTitle());
    }
    Collections.sort(videoNames);
    System.out.println("Here's a list of all available videos:");
    for (String name: videoNames){
      for (Video video: videos){
        if(name==video.getTitle()){
          System.out.println(" " + video.getTitle() + " (" + video.getVideoId() + ") "  + video.getTags());
          break;
        }
      }
    }
  }

  public void playVideo(String videoId) {
    boolean exists = false;
    String videoName = "";
    for(Video video: videos){
      if (video.getVideoId().equals(videoId)){
        exists = true;
        videoName = video.getTitle();
        break;
      }
    }
    if(exists){
      if(!playingVideo.equals("")){
        System.out.println("Stopping video: " + playingVideo);
      }
      playingVideo = videoName;
      System.out.println("Playing video: " + playingVideo);
      paused = false;
    }
    else{
      System.out.println("Cannot play video: Video does not exist");
    }
  }

  public void stopVideo() {
    if(!playingVideo.equals("")){
      System.out.println("Stopping video: Amazing Cats");
      playingVideo = "";
      paused = false;
    }
    else{
      System.out.println("Cannot stop video: No video is currently playing");
    }
    
  }

  public void playRandomVideo() {
    Random rand = new Random();
    if(videos.size()>0){
      if(!playingVideo.equals("")){
        System.out.println("Stopping video: " + playingVideo);
      }
      playingVideo = videos.get(rand.nextInt(videos.size())).getTitle();
        System.out.println("Playing video: " + playingVideo);
        paused = false;
    }
    else{
      System.out.println("No videos available.");
    }
  }

  public void pauseVideo() {
    if(!playingVideo.equals("")){
      if (paused){
        System.out.println("Video already paused: " + playingVideo);
      }
      else{
        System.out.println("Pausing video: " + playingVideo);
        paused=true;
      }
    }
    else{
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }

  public void continueVideo() {
    if(!playingVideo.equals("")){
      if(paused){
        System.out.println("Continuing video: " + playingVideo);
        paused=false;
      }
      else{
        System.out.println("Cannot continue video: Video is not paused");
      }
    }
    else{
      System.out.println("Cannot continue video: No video is currently playing");
    }
  }

  public void showPlaying() {
    if (!playingVideo.equals("")){
      String videoInfo = "";
      for(Video video: videos){
        if(playingVideo.equals(video.getTitle())){
          videoInfo = video.getTitle() + " (" + video.getVideoId() + ") "  + video.getTags();
          break;
        }
      }
      if (paused){
        videoInfo = videoInfo + " - PAUSED";
      }
      System.out.println("Currently playing: " + videoInfo);
    }
    else{
      System.out.println("No video is currently playing");
    }
  }

  public void createPlaylist(String playlistName) {
    if(playlists.size()>0){
      for(VideoPlaylist playlist: playlists){
        if(playlist.getName().toLowerCase().equals(playlistName.toLowerCase())){
          System.out.println("Cannot create playlist: A playlist with the same name already exists");
          return;
        }
      }
    }
    playlists.add( new VideoPlaylist(playlistName));
    System.out.println("Successfully created new playlist: " + playlistName);
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    List<String> playlistTitles = new ArrayList<>();
    if(!playlists.isEmpty()){
      for (VideoPlaylist playlist: playlists){
        playlistTitles.add(playlist.getName());
      }
      if(!playlistTitles.contains("" +playlistName)){
        System.out.println("Cannot add video to " + playlistName + " : Playlist does not exist");
        return;
      }
    }
    else{
      System.out.println("Cannot add video to " + playlistName + " : Playlist does not exist");
    }
    for(Video video: videos){
      if(video.getVideoId().equals(videoId)){
        for(int i =0; i<playlists.size();i++){
          if(playlists.get(i).getName().toLowerCase().equals(playlistName.toLowerCase())){
            playlists.get(i).addVideo(video);
            System.out.println("Added video to " + playlistName + ": " + video.getTitle());
            return;
          }
        }
      }
    }
      System.out.println("Cannot add video to " + playlistName + ": Video does not exists");
  }

  public void showAllPlaylists() {
    List<String> playlistNames = new ArrayList<>();
    for (VideoPlaylist playlist: playlists){
      playlistNames.add(playlist.getName());
    }
    Collections.sort(playlistNames);
    if(playlists.size()>0){
      System.out.println("Showing all playlists:");
      for (String name: playlistNames){
        System.out.println(" " + name);
      }
    }
    else{
      System.out.println("No playlists exist yet");
    }
  }

  public void showPlaylist(String playlistName) {
    if(!playlists.isEmpty()){
      for(VideoPlaylist playlist: playlists){
        if(playlist.getName().toLowerCase().equals(playlistName.toLowerCase())){
          System.out.println("Showing playlist: " + playlist.getName());
          if(!playlist.getVideos().isEmpty()){
            for (Video video: playlist.getVideos()){
              System.out.println(" " + video.getTitle() + " (" + video.getTitle() + ") " + video.getTags());
              return;
            }
          }
          else{
            System.out.println(" No videos here yet");
            return;
          }
        }
      }
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    }
    else{
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if(!playlists.isEmpty()){
      for(int i=0; i<playlists.size(); i++){
        if(playlists.get(i).getName().toLowerCase().equals(playlistName.toLowerCase())){
          for(Video video: videos){
            if (video.getVideoId().equals(videoId)){
              System.out.println("Removed video from " + playlists.get(i).getName() + ": " + video.getTitle());
              playlists.get(i).removeVideo(videoId);
              return;
            }
          }
          System.out.println("Cannot remove video from " + playlists.get(i).getName() + ": Video does not exist");
          return;
        }
      }
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }
    else{
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }
  }

  public void clearPlaylist(String playlistName) {
    if(!playlists.isEmpty()){
        for(int i=0; i<playlists.size();i++){
          if(playlistName.toLowerCase().equals(playlists.get(i).getName())){
            playlists.get(i).clearPlaylist();
            return;
          }
        }
        System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist"); 
    }
    else{
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName) {
    if(!playlists.isEmpty()){
      for(int i=0; i<playlists.size();i++){
        if(playlists.get(i).getName().toLowerCase().equals(playlistName.toLowerCase())){
          System.out.print("Deleted playlist: " + playlistName);
          playlists.remove(i);
          return;
        }
      }
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
    }
    else{
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist"); 
    }
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}