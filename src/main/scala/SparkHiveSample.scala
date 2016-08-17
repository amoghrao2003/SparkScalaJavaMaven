

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
object SparkHiveSample {

  //table schema  
  case class ConfigFeatures(PRODUCT: String, PRODUCT_SERIES: String,
    SOFTWARE_RELEASE: String, CUSTOMER_NAME: String,
    SRNO: String, HOST_NAME: String,
    FEATURE: String, SUB_FEATURE: String,
    FEATURE_PARENT: String, XPATH: String,
    XPATH_PARENT: String)
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
    val sc = new SparkContext("local[2]", "Spark Hive Sample", sparkConf)

    //get hive context
    val hiveContext = new org.apache.spark.sql.hive.HiveContext(sc)

    //create table from ORC
    hiveContext.sql("CREATE TABLE IF NOT EXISTS Config_Features_ORC( PRODUCT STRING, PRODUCT_SERIES STRING,SOFTWARE_RELEASE STRING, CUSTOMER_NAME STRING,SRNO STRING, HOST_NAME STRING,FEATURE STRING, SUB_FEATURE STRING,FEATURE_PARENT STRING,XPATH STRING,XPATH_PARENT String) stored as orc")
    //fetch the text file data into RDD
    val textData = sc.textFile("/home/cloudera/hiveData.txt")

    //if import is written above code breaks
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._

    //create dataframe from RDD
 //   val configFeaturesTableDF = textData.map(_.split("\t")).map(row => ConfigFeatures(row(0), row(1), row(2), row(3), row(4), row(5), row(6), row(7), row(8), row(9), row(10))).toDF()
    
    //Register table template for queries
//    configFeaturesTableDF.registerTempTable("conf_features_template")
   
    val results = sqlContext.sql("SELECT * FROM conf_features_template")
//    results.map(t => "Entry: " + t.toString).collect().foreach(println)
  
  }

}