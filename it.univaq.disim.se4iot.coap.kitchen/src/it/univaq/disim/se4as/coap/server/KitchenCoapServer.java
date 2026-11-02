package it.univaq.disim.se4as.coap.server;

import java.io.File;
import java.net.InetSocketAddress;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoAPEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;

import it.univaq.disim.se4as.coap.server.resources.TemperatureResource;

public class KitchenCoapServer extends CoapServer {

	public KitchenCoapServer() {
		super();
	}

	public KitchenCoapServer(NetworkConfig config) {
		super(config);
	}

	public KitchenCoapServer(int... ports) {
		super(ports);
	}

	public KitchenCoapServer(NetworkConfig config, int... ports) {
		super(config, ports);
	}

	public static void main(String[] args) {
		// Prioritize loading Californium.properties directly from the project folder (not the main/root folder)
		File configFile = new File("it.univaq.disim.se4iot.coap.kitchen", "Californium.properties");

		if (!configFile.exists()) {
			try {
				File codeLocation = new File(KitchenCoapServer.class.getProtectionDomain().getCodeSource().getLocation().toURI());
				File projectDir = codeLocation.isDirectory() ? codeLocation.getParentFile() : codeLocation.getParentFile().getParentFile();
				File candidate = new File(projectDir, "Californium.properties");
				if (candidate.exists()) {
					configFile = candidate;
				}
			} catch (Exception ignored) {
			}
		}

		// If running directly with the project directory as current working directory
		if (!configFile.exists()) {
			configFile = new File("Californium.properties");
		}

		// Load network configuration from the project folder's Californium.properties
		NetworkConfig config;
		if (configFile.exists()) {
			config = NetworkConfig.createStandardWithFile(configFile);
			System.out.println("Loaded project Californium properties from: " + configFile.getAbsolutePath());
		} else {
			config = NetworkConfig.getStandard();
			System.out.println("Using standard network configuration");
		}

		// Get the CoAP port defined in Californium.properties (COAP_PORT=5684)
		int port = config.getInt(NetworkConfig.Keys.COAP_PORT);
		if (port == 0) {
			port = 5684;
		}

		// Create KitchenCoapServer with the loaded configuration
		KitchenCoapServer server = new KitchenCoapServer(config);

		// Create and add the endpoint based on the configuration and port
		CoAPEndpoint endpoint = new CoAPEndpoint(new InetSocketAddress(port), config);
		server.addEndpoint(endpoint);
		System.out.println("CoAP server endpoint configured on port " + port);

		// Add temperature resource
		TemperatureResource temperature = new TemperatureResource("temperature");
		server.add(temperature);
		System.out.println("Added TemperatureResource");

		// Start the server
		server.start();
		System.out.println("KitchenCoapServer is running on port " + port + "...");
	}
}
