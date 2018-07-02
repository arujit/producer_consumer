package PubSub

import java.util.Properties
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.clients.producer._
import scala.io.StdIn
import org.json4s._
import org.json4s.jackson.Serialization.write
import org.scalacheck.Gen
import scala.util.Random
import PubSub.Model.StudentData

case class StudentData(
                        id : String,
                        name : String,
                        maths : Int,
                        Science : Int,
                        English : Int
                      )


object StudentData {
    val student: Gen[StudentData] = for {
      c1 <- Gen.alphaStr
      c2 <- Gen.alphaStr
      c3 <- Gen.posNum[Int]
      c4 <- Gen.posNum[Int]
      c5 <- Gen.posNum[Int]
    } yield StudentData(c1, c2, c3,c4,c5)

}


class Producer {
  val producer = new KafkaProducer[String, String](configuration)

  private def configuration: Properties = {
    val props =new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }

  def random_gen() :StudentData = {
    //val id =
    val stud : StudentData = StudentData.student.sample.get
    stud
  }

  def sendmessage():Unit = {
    println("Records to be produced!")
    val topic = "Test_topic"
    for (i <- 0 to 49){

        implicit val formats = DefaultFormats
        val message = random_gen()
        println(write(message))
        val record = new ProducerRecord(topic, "key", write(message))
        producer.send(record)
      }
    producer.close()

  }
}

object Producer extends App {
  val producer = new Producer()
  producer.sendmessage()
}