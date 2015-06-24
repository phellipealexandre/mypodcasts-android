package com.mypodcasts.rss;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.rometools.fetcher.FetcherException;
import com.rometools.rome.io.FeedException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LatestEpisodeTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(1111);

  LatestEpisode latestEpisode;
  final String url = "http://localhost:1111/rss";

  @Before
  public void setup() throws IOException, FetcherException, FeedException {
    givenThat(get(urlEqualTo("/rss"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBodyFile("latest_episode_rss.xml")));

    Feed feed = new Feed(new URL(url));
    latestEpisode = new LatestEpisode(feed);
  }

  @Test
  public void itReturnsTitle() {
    assertThat(latestEpisode.getTitle(), is("Latest Episode!"));
  }

  @Test
  public void itReturnsDescription() {
    assertThat(latestEpisode.getDescription(), is("Latest episode description"));
  }

  @Test
  public void itReturnsPublishedDate() throws ParseException {
    DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
    Date expectedDate = formatter.parse("Sat Jun 20 00:10:01 BRT 2015");

    assertThat(latestEpisode.getPublishedDate(), is(expectedDate));
  }

  @Test
  public void itReturnsAudioUrl() {
    assertThat(latestEpisode.getAudioUrl(), is("http://example.com/my_latest_episode.mp3"));
  }

  @Test
  public void itReturnsAudioLength() {
    assertThat(latestEpisode.getAudioLength(), is(60000000L));
  }
}