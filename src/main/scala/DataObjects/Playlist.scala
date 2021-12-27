package DataObjects

import ujson.Value

case class Playlist(id: String, name: String, desc: String, owner_id: String,
                    owner_name: String, public: Boolean, followers: Int, var track_ids: Set[String] = Set[String]()){
  override def equals(o: Any) = o match {
    case that: Playlist => that.id.equalsIgnoreCase(this.id)
    case _ => false
  }
  override def hashCode = id.hashCode
}

object Playlist {
  def toCSV(playlist: Playlist): String = {
    playlist match {
      case Playlist(id, name, desc, owner_id, owner_name, public, followers, track_ids) =>
        s"$id|$name|$desc|$owner_id|$owner_name|$public|$followers"
    }
  }
  def parsePlaylist(i: Value): Option[Playlist] = {
    try {
      val id = i("id").strOpt
      val name = i("name").strOpt
      val desc = i("description").strOpt
      val owner_id = i("owner")("id").strOpt
      val owner_name = i("owner")("display_name").strOpt
      val public = i("public").boolOpt
      val followers = i("followers")("total").numOpt

      val all = List(id, name, desc, owner_id, owner_name, public, followers)
      if (all.exists(_.isEmpty)) {
        println("Playlist fields not obtained: ")
        println(all.map(_.getOrElse("%")).mkString(" | "))
        return None
      }
      Some(Playlist(id.get, name.get, desc.get, owner_id.get, owner_name.get, public.get, followers.get.toInt))
    } catch {
      case ex: RuntimeException =>
        println(s"Runtime Exception parsing: $ex")
        None
    }
  }
}
