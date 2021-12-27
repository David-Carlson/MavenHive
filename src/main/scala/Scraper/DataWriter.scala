package Scraper
import DataObjects.{Album, Artist, Playlist, Track}
import scala.collection.mutable

object DataWriter {
  val verbose = false
  def main(args: Array[String]): Unit = {
    collectAndWriteAllData("doctorsalt")
  }
  def collectAndWriteAllData(username: String): Unit = {
    val startTime = System.nanoTime()
    val (genreMap, albums, artists, playlists, tracks) = DataCollector.startCollection(username)
    if (albums.size == 0 || artists.size == 0 || playlists.size == 0 || tracks.size == 0) {
      println("No data returning, aborting write procedure")
      return
    }
    val collTimeStamp = System.nanoTime()
    val collTime  = (collTimeStamp- startTime) / 1e9d

    println(s"Collection took $collTime seconds")
    println(s"Writing music data for $username")
    val wd = os.pwd/"spotifydata"/username
//    os.remove.all(wd)
//    os.makeDir.all(wd)

    writeGenreMap(genreMap)

    writeAlbums(albums, username)
    writeAlbumToArtists(albums, username)
    writeAlbumToGenres(albums, genreMap, username)
    writeAlbumToTracks(tracks, username)

    writeArtists(artists, username)
    writeArtistsToGenres(artists, genreMap, username)

    writePlaylists(playlists, username)
    writePlaylistsToTracks(playlists, username)
    writePlaylistOwnerToID(playlists, username)

    writeTracks(tracks, username)
    writeTracksToArtists(tracks, username)

    val writeTime = (System.nanoTime() - collTimeStamp) / 1e9d
    println(s"Writing to file took $writeTime seconds")

    val manifestPath = os.pwd/"spotifydata"/username/"manifest.txt"
    val timestamp = java.time.LocalDateTime.now
    val manifestTxt = List(
      s"Details of music for $username: \n",
      s"Number of Playlists: ${playlists.size}",
      s"Number of Albums: ${albums.size}",
      s"Number of Artists: ${artists.size}",
      s"Number of Tracks: ${tracks.size}",
      s"Number of Genres: ${genreMap.size}\n",
      s"Created: ${timestamp}",
      s"Time to Scrape data: ${collTime} seconds",
      s"Time to Write data: ${writeTime} seconds"
    )
    os.write.over(manifestPath, manifestTxt.mkString("\n"), createFolders = true)
  }

  def writeGenreMap(genreMap: Map[String, Int], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"genres.txt"
      os.write.over(path, genreMap.map { case (g,i) => s"$i|$g"}.mkString("\n"),createFolders = true)
      if(verbose) println(s"Genres written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing genres")
      case ex: NullPointerException => println(ex, " Null path occured when writing genres")
    }
  }

  def writeArtists(artists: mutable.Set[Artist], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"artists.txt"
      os.write.over(path, artists.map(Artist.toCSV(_)).mkString("\n"),createFolders = true)
      if(verbose) println(s"Artists written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing artists")
      case ex: NullPointerException => println(ex, " Null path occured when writing artists")
    }
  }
  def writeArtistsToGenres(artists: mutable.Set[Artist], genreMap: Map[String, Int], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"artist_to_genres.txt"
      os.write.over(path, artists.flatMap(a => a.genres.map(g => s"${a.id}|${genreMap(g)}")).mkString("\n"),createFolders = true)
      if(verbose) println(s"Artists/Genres written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Artists/Genres")
      case ex: NullPointerException => println(ex, " Null path occured when writing Artists/Genres")
    }
  }

  def writeAlbums(albums: mutable.Set[Album], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"albums.txt"
      os.write.over(path, albums.map(Album.toCSV(_)).mkString("\n"),createFolders = true)
      if(verbose) println(s"Albums written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing albums")
      case ex: NullPointerException => println(ex, " Null path occured when writing albums")
    }
  }

  def writeAlbumToArtists(albums: mutable.Set[Album], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"album_to_artists.txt"
      os.write.over(path, albums.flatMap(alb => alb.artists.map(art => s"${alb.id}|$art")).mkString("\n"),createFolders = true)
      if(verbose) println(s"Album/Artists written to $path")

    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Album/Artists")
      case ex: NullPointerException => println(ex, " Null path occured when writing Album/Artists")
    }
  }
  def writeAlbumToGenres(albums: mutable.Set[Album], genreMap: Map[String, Int], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"album_to_genres.txt"
      os.write.over(path, albums.flatMap(a => a.genres.map(g => s"${a.id}|${genreMap(g)}")).mkString("\n"), createFolders = true)
      if(verbose) println(s"Album/Genres written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Album/Genres")
      case ex: NullPointerException => println(ex, " Null path occured when writing Album/Genres")
    }
  }
  def writeAlbumToTracks(tracks: mutable.Set[Track], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"album_to_tracks.txt"
      os.write.over(path, tracks.map(t => s"${t.album_id}|${t.id}").mkString("\n"),createFolders = true)
      if(verbose) println(s"Album/Tracks written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Album/Tracks")
      case ex: NullPointerException => println(ex, " Null path occured when writing Album/Tracks")
    }
  }

  def writePlaylists(playlists: mutable.Set[Playlist], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"playlists.txt"
      os.write.over(path, playlists.map(Playlist.toCSV(_)).mkString("\n"),createFolders = true)
      if(verbose) println(s"Playlists written to $path")

    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Playlists")
      case ex: NullPointerException => println(ex, " Null path occured when writing Playlists")
    }
  }
  def writePlaylistOwnerToID(playlists: mutable.Set[Playlist], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"owner_to_name.txt"
      os.write.over(path, playlists.map(p => s"${p.owner_id}|${p.owner_name}").mkString("\n"),createFolders = true)
      if(verbose) println(s"Owners/Names written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Owners/Names")
      case ex: NullPointerException => println(ex, " Null path occured when writing Owners/Names")
    }
  }
  def writePlaylistsToTracks(playlists: mutable.Set[Playlist], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"playlist_to_tracks.txt"
      val allRows = playlists.map(p => p.track_ids.map(tr => s"${p.id}|${tr}").mkString("\n"))
      os.write.over(path, allRows.mkString("\n"),createFolders = true)
      if(verbose) println(s"Playlists/Tracks written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Playlists/Tracks")
      case ex: NullPointerException => println(ex, " Null path occured when writing Playlists/Tracks")
    }
  }
  def writeTracks(tracks: mutable.Set[Track], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"tracks.txt"
      os.write.over(path, tracks.map(Track.toCSV(_)).mkString("\n"),createFolders = true)
      if(verbose) println(s"Tracks written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Tracks")
      case ex: NullPointerException => println(ex, " Null path occured when writing Tracks")
    }
  }
  def writeTracksToArtists(tracks: mutable.Set[Track], username: String="doctorsalt"): Unit = {
    try {
      val path = os.pwd/"spotifydata"/username/"track_to_artists.txt"
      os.write.over(path, tracks.flatMap(t => t.artists.map(a => s"${t.id}|$a")).mkString("\n"),createFolders = true)
      if(verbose) println(s"Tracks/Artists written to $path")
    } catch {
      case ex: java.io.IOException => println(ex, " Occured when writing Tracks/Artists")
      case ex: NullPointerException => println(ex, " Null path occured when writing Tracks/Artists")
    }
  }
}
