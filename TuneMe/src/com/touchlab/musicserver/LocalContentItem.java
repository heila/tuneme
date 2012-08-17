package com.touchlab.musicserver;

public class LocalContentItem {
	
	private String artist;
	private String album;
	private String song;
	private String remoteAccessUrl;
	private String localAccessUrl;
	private int artworkResourceId;
	
	public LocalContentItem(){
		
	}
	
	public LocalContentItem(String artist, String album, String song, String remoteAccessUrl, String localAccessUrl, int artworkResourceId) {
		this.artist = artist;
		this.album = album;
		this.song = song;
		this.remoteAccessUrl = remoteAccessUrl;
		this.localAccessUrl = localAccessUrl;
		this.artworkResourceId = artworkResourceId;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public String getRemoteAccessUrl() {
		return remoteAccessUrl;
	}

	public void setRemoteAccessUrl(String remoteAccessUrl) {
		this.remoteAccessUrl = remoteAccessUrl;
	}

	public String getLocalAccessUrl() {
		return localAccessUrl;
	}

	public void setLocalAccessUrl(String localAccessUrl) {
		this.localAccessUrl = localAccessUrl;
	}

	public int getArtworkResourceId() {
		return artworkResourceId;
	}

	public void setArtworkResourceId(int artworkResourceId) {
		this.artworkResourceId = artworkResourceId;
	}

	
	
	

}
