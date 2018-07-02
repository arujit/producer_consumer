package PubSub

import java.io.{File, FileOutputStream}

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

import scala.collection.JavaConverters._
import java.util.Properties

//import com.sun.java.util.jar.pack.Package.File
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
//import Cons.props
import org.apache.kafka.clients.producer.KafkaProducer
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write
import java.io.PrintWriter

class Consumer {
  implicit val formats = DefaultFormats
  val topic = "Test_topic"
  val consumer = new KafkaConsumer[String, String](configuration)
  consumer.subscribe(List(topic).asJava)

  private def configuration: Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "earliest")
    props.put("enable.auto.commit", "false")
    props.put("group.id", "something_12")
    props
  }

  //def parse() = ???

  //def empty_file(file_path)

  def dump_file(file_name : String,file_content : String) :Unit ={
    //new PrintWriter(file_name){write(file_content) ; close()}
    val write = new PrintWriter(new FileOutputStream(new File(file_name),true))
    write.write(file_content)
    write.close()
  }

  def receive_messages(): Unit = {
    println ("hello 1")
    while (true) {

      val records: ConsumerRecords[String, String] = consumer.poll(50)

      for (record <- records.asScala) {
        //println("hello 3")
        //println(parse(record.value()))
        var student = parse(record.value())
        val student_id = compact(student.children(0))
        val total_marks = compact(student.children(2)).toInt + compact(student.children(3)).toInt + compact(student.children(4)).toInt
        var average_marks = total_marks / 3
        println(average_marks)
        dump_file("data.txt",s"$student_id , $average_marks \n")
        //println(record.value())
      }
    }

  }
}
object Consumer extends App {
  val consumer = new Consumer()
  consumer.receive_messages()

  }

