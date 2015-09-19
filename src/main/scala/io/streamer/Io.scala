package io.streamer

import akka.actor._
import java.io.DataInputStream

object Io extends App {
  val system = ActorSystem("IO")
  new Server(9000,system, {
      socket =>
        val inputStream = new DataInputStream(socket.getInputStream)
        var isQuit=false
        while(!isQuit){
          val inData = inputStream.readLine match {
            case null => "quit"
            case validString:String => validString
          }
          println(inData+":"+socket.getRemoteSocketAddress+" "+inputStream)
          if(inData=="quit")
            isQuit = true 
        }

        inputStream.close
        socket.close
        
      }
  ).start()
}
