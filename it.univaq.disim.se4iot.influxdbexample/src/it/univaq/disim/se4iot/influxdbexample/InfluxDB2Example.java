package it.univaq.disim.se4iot.influxdbexample;

import java.time.Instant;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;

public class InfluxDB2Example {
	
	
  public static void main(final String[] args) {

	    // You can generate a Token from the "Tokens Tab" in the UI
	    String token = "5LW7B-5ySei2kPoLtPIwBFS0KfK-uAcnxYE04BoJhalwdzQjvRz5zi7bELcTNw_WDkfCUkJiDd-wvIIDvnYHjQ==";
	    String bucket = "se4iot";
	    String org = "univaq";

	    InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());

	  Point point = Point
			  .measurement("powerconsumption")
			  .addTag("device", "device3")
			  .addTag("host", "MyHost2")
			  .addField("power", 300)
			  .time(Instant.now(), WritePrecision.NS);
	
			try (WriteApi writeApi = client.getWriteApi()) {
			  writeApi.writePoint(bucket, org, point);
			}
 
  }
}