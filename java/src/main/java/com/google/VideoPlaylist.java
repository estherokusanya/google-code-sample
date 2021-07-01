package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {

  private String name;
  private List<Video> playlist;

  public VideoPlaylist(String name){
    this.name = name;
    playlist= new ArrayList<>();
  }

  /**Returns the title of playlist */
  String getName(){
    return name;
  }

  void addVideo(Video video){
    playlist.add(video);
  }
  
  List<Video> getVideos(){
    return playlist;
  }
  
  void removeVideo(String videoId){
    for(int i=0; i<playlist.size();i++){
      if(videoId.equals(playlist.get(i).getVideoId())){
        playlist.remove(i);
        return;
      }
    }
  }
  void clearPlaylist(){
    for(int i=0; i<playlist.size(); i++){
      playlist.remove(i);
    }
  }

}
