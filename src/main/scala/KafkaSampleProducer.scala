



import org.apache.kafka.clients.producer.KafkaProducer
import java.util.HashMap
import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}
import java.io.File
// Produces some random words between 1 and 100.
object KafkaSampleProducer {

  def main(args: Array[String]) {
    /*if (args.length < 4) {
      System.err.println("Usage: KafkaWordCountProducer <metadataBrokerList> <topic> " +
        "<messagesPerSec> <wordsPerMessage>")
      System.exit(1)
    }*/

    val Array(brokers, topic, messagesPerSec, wordsPerMessage) = Array("quickstart.cloudera:9092", "test","10","10")
    //val directoryPath = "/home/cloudera/Documents/config/"
    // Zookeeper connection properties
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    //   /home/cloudera/Documents/data/
    //val directoryPath = "/home/cloudera/Documents/config/"
    
    val directoryPath = "/home/cloudera/Documents/data/"
    val myDirectory= new File(directoryPath)
    var lines =""
    var fileNameData = ""
    for (file <- myDirectory.listFiles) {
       //lines = scala.io.Source.fromFile(file).mkString + "\n" + "<EOF>" + "\n"
       fileNameData = file.getName
       println(fileNameData)
       lines = scala.io.Source.fromFile(file).mkString + "<EOF>" + fileNameData
       val message = new ProducerRecord[String, String](topic, null, lines)
       producer.send(message)
       //print(lines)
       Thread.sleep(1000)
    }
    
  }
  
}