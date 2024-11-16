package iotsystem;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Sensor implements Runnable {
    private final String sensorType;
    private static final String QUEUE_NAME = "sensor_data";

    public Sensor(String sensorType) {
        this.sensorType = sensorType;
    }

    @Override
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); 

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            Random random = new Random();
            while (true) {
                int reading = random.nextInt(100);
                String message = sensorType + ": " + reading;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(sensorType + " sent: " + message);

                Thread.sleep(2000); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Sensor("Temperature")).start();
        new Thread(new Sensor("Humidity")).start();
        new Thread(new Sensor("Light")).start();
    }
}

