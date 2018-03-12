package ru.chacknoris.vk.datamine

import java.util.Date

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.httpclient.HttpTransportClient


object AppMain {

  def main(args: Array[String]): Unit = {
    val transportClient = HttpTransportClient.getInstance
    val vk = new VkApiClient(transportClient)
    val authResponse = vk.oauth()
      .serviceClientCredentialsFlow(, "")
      .execute()

    val actor = new ServiceActor(, "", authResponse.getAccessToken)
//    val actor = new UserActor(authResponse.getUserId, authResponse.getAccessToken)
  }

  case class Config(outputPath: String = "",
                    accessToken: String,
                    appId: Int)

}
