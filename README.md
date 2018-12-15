# BigPandaNew
Prerequesitse :
               win os,
               kafka server,
               zookeeper ,
               redis server,
               maven,
               jdk 1.8
               
  After installing/runiing the above , download the src ( mvn )
  
  run mvn clean install.
  create 3 topics in kafka using the following :
  
    kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic bptopic1
    kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic bptopic2
    kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic bptopic3
    
   Running kafka producer : java -jar producer-0.0.1-SNAPSHOT-jar-with-dependencies.jar path_to_generator
 
   Producer:
   Kafka producer starts the 'black box data generator ' and connect to it via stream.
   It reads the output lines as input for it, and immediately procudes the lines to 3 topics .
    in this simple application it just uses round robin algorithm.
  
  
  Consumers:
  To run the consumer run the following :
  java -jar consumer-0.0.1-SNAPSHOT-jar-with-dependencies.jar bptopic1 ( register on topic 1)
  java -jar consumer-0.0.1-SNAPSHOT-jar-with-dependencies.jar bptopic2 ( register on topic 2)
  java -jar consumer-0.0.1-SNAPSHOT-jar-with-dependencies.jar bptopic3 ( register on topic 3)
  
  Each consumer recieve the json lines , abd after processing it, it stores the data in redis server 
  
  
  Api:
  Is a spring boot application.
  
  run java -jar api-0.0.1-SNAPSHOT.jar
  it listens on port 8099.You can change the port number in application.properties in the project.
  
  to get a stat 
  go to http://localhost:8099/api/foo
     or
        http://localhost:8099/api/foo/all
    
    instead of 'foo' you can replace it with any other word
        
  
  
  
  
  
    
 
 
  
  
