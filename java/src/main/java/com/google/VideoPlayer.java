package com.google;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private HashMap<String, Boolean> playingvideoMap = new HashMap<>();
  private HashMap<String, Video> videoNameMap = new HashMap<String, Video>();
  private HashMap<String, VideoPlaylist> playlistNameMap = new HashMap<>();

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    List<Video> videos = videoLibrary.getVideos();
    for(Video video: videos){
      videoNameMap.put(video.getVideoId(),video);
    }
  }

  private String getPlayingVideoId(){
   return "" + playingvideoMap.keySet().toArray()[0]; 
  }
  private String getPlayingVideoName(){
    return videoNameMap.get(getPlayingVideoId()).getTitle();
  }


  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }


  public void showAllVideos() {
    Set<String> videoNamesSet = videoNameMap.keySet();
    List<String> videoNames = new ArrayList<>();
    videoNames.addAll(videoNamesSet);
    Collections.sort(videoNames);
    System.out.println("Here's a list of all available videos:");
    for (String name: videoNames){
      System.out.println(videoNameMap.get(name).videoInfo());
    }
  }

  public void playVideo(String videoId) {
    if(videoExists(videoId)){
      if(!playingvideoMap.isEmpty()){
        System.out.println("Stopping video: " + getPlayingVideoName());
      }
      playingvideoMap.put(videoId, false);
      System.out.println("Playing video: " + getPlayingVideoName());
    }
    else{
      System.out.println("Cannot play video: Video does not exist");
    }
  }


  public void stopVideo() {
    if(!playingvideoMap.isEmpty()){
      System.out.println("Stopping video: " + getPlayingVideoName());
      playingvideoMap.clear();
    }
    else{
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }


  public void playRandomVideo() {
    if(!videoNameMap.isEmpty()){
      Random rand = new Random();
      int number = rand.nextInt(videoNameMap.size());
      if(!playingvideoMap.isEmpty()){
        System.out.println("Stopping video: " + getPlayingVideoName());
      }
      List<Video> videos = new ArrayList(videoNameMap.values());
      playingvideoMap.put(videos.get(number).getVideoId(), false);
      System.out.println("Playing video: " + getPlayingVideoName());
    }
    else{
      System.out.println("No videos available");
    }
  }


  public void pauseVideo() {
    if(!playingvideoMap.isEmpty()){
      if(!playingvideoMap.get(getPlayingVideoId())){
        playingvideoMap.replace(""+ getPlayingVideoId(), true);
        System.out.println("Pausing video: " + getPlayingVideoName());
      }
      else{
        System.out.println("Video already paused: " + getPlayingVideoName());
      }
    }
    else{
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }


  public void continueVideo() {
    if(!playingvideoMap.isEmpty()){
      if(playingvideoMap.get(getPlayingVideoId())){
        System.out.println("Continuing video: "+ getPlayingVideoName());
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
    if(!playingvideoMap.isEmpty()){
      if(playingvideoMap.get(getPlayingVideoId())){
        System.out.println("Currently playing:" + videoNameMap.get(getPlayingVideoId()).videoInfo() + " - PAUSED");
        return;
      }
      else{
        System.out.println("Currently playing:" + videoNameMap.get(getPlayingVideoId()).videoInfo());
        return;
      }
    }
    System.out.println("No video is currently playing");
  }

  private boolean playlistExists(String playlistName){
    boolean exists= false;
    Set<String> list = playlistNameMap.keySet();
    for (String name : list){
      if(name.toLowerCase().equals(playlistName.toLowerCase())){
        exists = true;
      }
    }
    return exists;
  }

  private boolean videoExists(String videoId){
    boolean exists=false;
    Set<String> list = videoNameMap.keySet();
    for (String video : list){
      if (video.equals(videoId)){
        exists=true;
      }
    }
    return exists;
  }

  public void createPlaylist(String playlistName) {
    if(playlistExists(playlistName)){
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
    else{
      playlistNameMap.put(playlistName, new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: " + playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    if(playlistExists(playlistName)){
      if (videoExists(videoId)){
        if(!getPlaylistFromMap(playlistName).videoAlreadyExists(videoId)){
          getPlaylistFromMap(playlistName).addVideo(videoNameMap.get(videoId));
          System.out.println("Added video to " + playlistName + ": " + videoNameMap.get(videoId).getTitle());
        }
        else{
          System.out.println("Cannot add video to " + playlistName + ": Video already added");
        }
      }
      else{
        System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      }
    }
    else{
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    }
  }

  public void showAllPlaylists() {
    if (!playlistNameMap.isEmpty()){
      Set<String> playlistNames = playlistNameMap.keySet();
      List<String> sortedPlaylist = new ArrayList<>();
      sortedPlaylist.addAll(playlistNames);
      Collections.sort(sortedPlaylist);
      System.out.println("Showing all playlists:");
      for (String name: sortedPlaylist){
        System.out.println(" " + name);
      }
    }
    else{
      System.out.println("No playlists exist yet");
    }
  }

  public void showPlaylist(String playlistName) {
    if(playlistExists(playlistName)){
      List<Video> videos = getPlaylistFromMap(playlistName).getVideos();
      System.out.println("Showing playlist: "+ playlistName);
      if(!videos.isEmpty()){
        for (Video name: videos){
          System.out.println(" " + name.videoInfo());
        }
      }
      else{
        System.out.println(" No videos here yet");
      }
    }
    else{
      System.out.println("Cannot show playlist "+ playlistName + ": Playlist does not exist");
    }
  }

  private VideoPlaylist getPlaylistFromMap(String playlistName){
    Set<String> videoNameSet = playlistNameMap.keySet();
    for(String name : videoNameSet){
      if (name.toLowerCase().equals(playlistName.toLowerCase())){
        return playlistNameMap.get(name);
      }
    }
    return null;
  }
  
  public void removeFromPlaylist(String playlistName, String videoId) {
    if (playlistExists(playlistName)){
      if(videoExists(videoId)){
        if(getPlaylistFromMap(playlistName).videoAlreadyExists(videoId)){
          getPlaylistFromMap(playlistName).removeVideo(videoId);
          System.out.println("Removed video from "+ playlistName + ": " + videoNameMap.get(videoId).getTitle());
        }
        else{
          System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
        }
      }
      else{
        System.out.println("Cannot remove video from "+ playlistName + ": Video does not exists");
      }
    }
    else{
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }
  }

  public void clearPlaylist(String playlistName) {
    if(playlistExists(playlistName)){
      getPlaylistFromMap(playlistName).clearPlaylist();
      System.out.println("Successfully removed all videos from " + playlistName);
    }
    else{
      System.out.println("Cannot clear playlist "+ playlistName + ": Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName) {
    if(playlistNameMap.containsValue(getPlaylistFromMap(playlistName))){
      playlistNameMap.values().remove(getPlaylistFromMap(playlistName));
      //playlistNameMap.remove(playlistName);
      System.out.println("Deleted playlist: " + playlistName);
    }
    else{
      System.out.println("Cannot delete playlist "+ playlistName + ": Playlist does not exist");
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