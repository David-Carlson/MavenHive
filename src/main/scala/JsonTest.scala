import ujson._
import requests._
import upickle.default._

object JsonTest {
  def main(args: Array[String]): Unit = {
//    sample()
//    req()

    val wd = os.pwd/"spotifydata"
  }
  def req(): Unit = {
    val r = requests.get("https://api.github.com/users/lihaoyi")
//    println(r.text)
    val json = ujson.read(r.text)
    // {
    // "login":"lihaoyi",
    // "id":934140,
    // "node_id":"MDQ6VXNlcjkzNDE0MA==",
    // "avatar_url":"https://avatars.githubusercontent.com/u/934140?v=4",
    // "gravatar_id":"","url":"https://api.github.com/users/lihaoyi",
    // "html_url":"https://github.com/lihaoyi",
    // "followers_url":"https://api.github.com/users/lihaoyi/followers",
    // "following_url":"https://api.github.com/users/lihaoyi/following{/other_user}",
    // "gists_url":"https://api.github.com/users/lihaoyi/gists{/gist_id}",
    // "starred_url":"https://api.github.com/users/lihaoyi/starred{/owner}{/repo}",
    // "subscriptions_url":"https://api.github.com/users/lihaoyi/subscriptions",
    // "organizations_url":"https://api.github.com/users/lihaoyi/orgs",
    // "repos_url":"https://api.github.com/users/lihaoyi/repos",
    // "events_url":"https://api.github.com/users/lihaoyi/events{/privacy}",
    // "received_events_url":"https://api.github.com/users/lihaoyi/received_events",
    // "type":"User", "site_admin":false, "name":"Li Haoyi", "company":null, "blog":"https://www.handsonscala.com/",
    // "location":null,"email":null,"hireable":null,
    // "bio":"I'm a software engineer. If you like using my libraries or reading my blog https://www.lihaoyi.com/, you should check out my book https://www.handsonscala.co",
    // "twitter_username":null,"public_repos":32,"public_gists":59,"followers":4320,
    // "following":0,"created_at":"2011-07-23T12:52:53Z","updated_at":"2021-09-08T21:06:54Z"
    // }
    println(json("id"))


  }
  def sample(): Unit = {
//    println("Hello")
//    val data = ujson.read(getClass.getResourceAsStream("sample.json"))
//    println(data.value)
//    //  val datas = ujson.read(input)
//    //  data("name") = data("name").str.reverse
//    val updated = data.render()
//    println(updated)
  }

  def pickle(): Unit = {

//    os.read(os.pwd/"src"/"test"/"resources"/"phil.json")
//    val data = ujson.read(jsonString)
//    data.value
  }
}
