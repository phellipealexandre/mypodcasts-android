package com.mypodcasts.repositories.models;

import java.io.Serializable;
import java.util.Date;

import static java.lang.String.format;
import static org.joda.time.format.ISODateTimeFormat.dateTimeParser;

public class Episode implements Serializable {

  private String title;
  private String publishedDate;
  private String description;
  private String duration;
  private Audio audio;
  private Podcast podcast;
  private Image image;

  public Image getImage() {
    return image;
  }

  public String getTitle() {
    return title;
  }

  public Date getPublishedDate() {
    if (publishedDate == null) { return null; }

    return dateTimeParser().parseDateTime(publishedDate).toDate();
  }

  public String getDescription() {
    return description == null ? "" : description;
  }

  public String getDuration() {
    return duration;
  }

  public Podcast getPodcast() {
    if (podcast == null) { return new Podcast(); }

    return podcast;
  }

  public String getAudioFilePath() {
    return getPodcast().getId() + "/" + getTitle() + ".mp3";
  }

  public String getAudioUrl() {
    return getAudio().getUrl();
  }

  public String getAudioLength() {
    return getAudio().getLength();
  }

  protected Audio getAudio() {
    if (audio == null) { return new EmptyAudio(); }

    return audio;
  }

  @Override
  public String toString() {
    return format(
        "{ \"title\": %s, " +
            "\"audioFilePath\": %s, " +
            "\"podcast\": { \"id\": %s, \"title\": %s } " +
        "}",
        getTitle(),
        getAudioFilePath(),
        getPodcast().getId(),
        getPodcast().getTitle()
    );
  }

  private class EmptyAudio extends Audio {
    @Override
    public String getUrl() {
      return "";
    }

    @Override
    public String getLength() {
      return "0";
    }
  }
}
