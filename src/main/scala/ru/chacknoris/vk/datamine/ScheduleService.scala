package ru.chacknoris.vk.datamine

import java.util.concurrent.{Callable, LinkedBlockingQueue, TimeUnit}

import akka.actor.ActorSystem
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Promise}
import scala.language.implicitConversions

object ScheduleService {

  private val log = LoggerFactory.getLogger(ScheduleService.getClass)

  private val queue = new LinkedBlockingQueue[Runnable]()

  private val scheduleService = ActorSystem().scheduler

  scheduleService.schedule(
    Duration(0, TimeUnit.MILLISECONDS),
    Duration(200, TimeUnit.MILLISECONDS), new Runnable {
      override def run(): Unit = {
        try
          queue.take().run()
        catch {
          case e: Exception => log.error("Some ex", e)
        }
      }
    })

  def addTask[T](callable: Callable[T]): Future[T] = {
    val p = Promise[T]()
    queue.add(new Runnable {
      override def run(): Unit = p.success(callable.call())
    })
    p.future
  }


  implicit def runnable(f: () => Unit): Runnable =
    new Runnable() {
      def run(): Unit = f()
    }

  implicit def callable[T](f: () => T): Callable[T] =
    new Callable[T]() {
      def call(): T = f()
    }

}
