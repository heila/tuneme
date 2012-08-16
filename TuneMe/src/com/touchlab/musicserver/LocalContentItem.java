package com.touchlab.musicserver;

public class LocalContentItem {
	
	private String artist;
	private String album;
	private String song;
	private String remoteAccessUrl;
	private String localAccessUrl;
	private int artworkResourceId;
	
	public LocalContentItem(String artist, String album, String song, String remoteAccessUrl, String localAccessUrl, int artworkResourceId) {
		this.artist = artist;
		this.album = album;
		this.song = song;
		this.remoteAccessUrl = remoteAccessUrl;
		this.localAccessUrl = localAccessUrl;
		this.artworkResourceId = artworkResourceId;
	}

	protected String getArtist() {
		return artist;
	}

	protected String getAlbum() {
		return album;
	}

	protected String getSong() {
		return song;
	}

	protected String getRemoteAccessUrl() {
		return remoteAccessUrl;
	}

	protected String getLocalAccessUrl() {
		return localAccessUrl;
	}
	
	protected int getArtworkResourceId() {
		return this.artworkResourceId;
	}
	
	

}
