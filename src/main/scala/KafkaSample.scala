

/**
 * @author amoghrao
 */
import org.apache.spark.SparkConf
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka.HasOffsetRanges
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.kafka.KafkaUtils

import kafka.serializer.DefaultDecoder
import kafka.serializer.StringDecoder
import kafka.serializer.StringDecoder
object KafkaSample {
  def main(args: Array[String]): Unit = {

    /*if (args.length < 2) {
      System.err.println("Usage: <broker-list> <zk-list> <topic>")
      System.exit(1)
    }*/

    val Array(broker, zk, topic) = Array("quickstart.cloudera:9092", "quickstart.cloudera:2181", "test")

    /* All configs*/  

    val sparkConf = new SparkConf()
    val sc = new SparkContext("local[2]", "Spark-from-Kafka-to-Hbase", sparkConf)
    
    
    
    
    
    val ssc = new StreamingContext(sc, Seconds(10))
    val kafkaConf = Map("metadata.broker.list" -> broker,
      "auto.offset.reset" -> "smallest",
      "zookeeper.connect" -> zk,
      "group.id" -> "spark-streaming-from-kafka",
      "zookeeper.connection.timeout.ms" -> "2500")

    val lines = KafkaUtils.createDirectStream[Array[Byte], String, DefaultDecoder, StringDecoder](ssc, kafkaConf, Set(topic)).map(_._2)

    val output = lines.foreachRDD(rdd => 
      rdd.foreachPartition { partition => 
        partition.foreach { file => runConfigParser(file)
          Thread.sleep(1000)}
      })
     
     
    ssc.checkpoint("./checkpoints")
    ssc.start()
    ssc.awaitTermination()
  
    
  }

  def runConfigParser(s:String):String = {
    
  //print(s)
  //  new Main().main1(s);
    return  "hi"
  }
}
