package ru.chacknoris.vk.datamine.data

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.queries.users.UserField
import ru.chacknoris.vk.datamine.ScheduleService
import ru.chacknoris.vk.datamine.ScheduleService.callable

object UserInfo {

  class AvatarInfo(avatar_p_comment_friends_ : Double, avatar_p_like_friends_ : Double,
                   avatar_c_comment_ : Double, avatar_c_likes_ : Double) {
    val avatar_p_comment_friends: Double = avatar_p_comment_friends_
    val avatar_p_like_friends: Double = avatar_p_like_friends_
    val avatar_c_comment: Double = avatar_c_comment_
    val avatar_c_likes: Double = avatar_c_likes_
  }

  class WallInfo(wall_avg_2_post_all_ : Double, wall_avg_2_repost_ : Double, wall_avg_2_mine_ : Double,
                 wall_p_repost_ : Double, wall_p_mine_ : Double, wall_p_mine_url_ : Double,
                 wall_p_like_friends_ : Double, wall_reposted_group_list_to_date_ : List[(Long, Int)]) {
    val wall_avg_2_post_all: Double = wall_avg_2_post_all_
    val wall_avg_2_repost: Double = wall_avg_2_repost_
    val wall_avg_2_mine: Double = wall_avg_2_mine_
    val wall_p_repost: Double = wall_p_repost_
    val wall_p_mine: Double = wall_p_mine_
    val wall_p_mine_url: Double = wall_p_mine_url_
    val wall_p_like_friends: Double = wall_p_like_friends_
    val wall_reposted_group_list_to_date: List[(Long, Int)] = wall_reposted_group_list_to_date_
  }

  class PhotoInfo(photo_avg_2_post_all_ : Double, photo_p_comment_ : Double,
                  photo_p_urls_comment_ : Double, photo_p_like_friends_ : Double) {
    val photo_avg_2_post_all: Double = photo_avg_2_post_all_
    val photo_p_comment: Double = photo_p_comment_
    val photo_p_urls_comment: Double = photo_p_urls_comment_
    val photo_p_like_friends: Double = photo_p_like_friends_
  }


  class GroupInfo(group_c_reposted_ : Double, group_subscribe_list_ : List[Int]) {
    val group_c_reposted: Double = group_c_reposted_
    val group_subscribe_list: List[Int] = group_subscribe_list_
  }


  def loadAvatarInfo(vk: VkApiClient, ua: UserActor, friends: List[Int]): AvatarInfo = {
    ScheduleService.addTask(() => vk.users().get(ua).fields(UserField.PHOTO_ID).execute)
      .onComplete((r) => {
        r
      })
  }

}
