import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

import scala.collection.JavaConverters._

object Cons extends App {

  import java.util.Properties

  val TOPIC="test_topic"

  val  props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("auto.offset.reset", "earliest")
  props.put("enable.auto.commit", "false")
  props.put("group.id", "something_12")

  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(List(TOPIC).asJava)

  while(true){
    val records: ConsumerRecords[String, String] =consumer.poll(100)
    //    println("records size:" + records.count())
    for (record<-records.asScala){
      println(record.value())
    }
  }
}
