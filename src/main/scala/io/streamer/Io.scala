package io.streamer

import akka.actor._
import java.io.{DataInputStream,DataOutputStream}

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
          println(inData+":"+socket.getRemoteSocketAddress)
          if(inData=="quit")
            isQuit = true 
          if(inData == "ex")
            throw new Exception("dayum")  
        }

        inputStream.close
        socket.close
        
      }
  ).start()

  new Client("localhost",9000,{
      socket =>
        val outputStream = new DataOutputStream(socket.getOutputStream)
        outputStream.writeUTF("hello")
        outputStream.flush()
        outputStream.close
        socket.close
    }
  ).beam()
}
