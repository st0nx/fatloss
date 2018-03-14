package ru.chacknoris.vk.datamine

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.httpclient.HttpTransportClient

import scala.concurrent.ExecutionContext.Implicits.global


object AppMain {

  def run(config: Config): Unit = {
    val transportClient = HttpTransportClient.getInstance
    val vk = new VkApiClient(transportClient)
    val authResponse = vk.oauth()
      .serviceClientCredentialsFlow(config.appId, config.accessToken)
      .execute()
    val actor = new UserActor(config.appId, config.accessToken)
  }


  def main(args: Array[String]): Unit = {
    import ScheduleService.callable
    ScheduleService.addTask(() => println("AAAAAA"))
      .onComplete((r) => println("BBBBBB"))

    val parser = new scopt.OptionParser[Config]("VK miner") {
      opt[String]('o', "outputPath").action((x, c) =>
        c.copy(outputPath = x)).text("Output dataframe path")
      opt[Int]('i', "id").action((x, c) =>
        c.copy(appId = x)).text("VK application id")
      opt[String]('t', "token").action((x, c) =>
        c.copy(outputPath = x)).text("Client access token")
    }

    parser.parse(args, Config()) match {
      case Some(config) => run(config)
      case None =>
        throw new IllegalStateException("Failed to read input params")
    }
  }

  case class Config(outputPath: String = "",
                    accessToken: String = "",
                    appId: Int = 0)

}
