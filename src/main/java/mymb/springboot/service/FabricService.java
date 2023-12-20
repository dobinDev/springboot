package mymb.springboot.service;

import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FabricService {

    private static final Path networkConfigPath = Paths.get("path/to/your/connection-profile.yaml");
    private static final String channelName = "your-channel-name";
    private static final String contractName = "your-smart-contract-name";

    public String submitTransaction(String function, String... args) {
        try {
            Gateway.Builder builder = Gateway.createBuilder();

            try (Gateway gateway = builder.identity(walletPath, userName).networkConfig(networkConfigPath).connect()) {
                // Access the network
                Network network = gateway.getNetwork(channelName);

                // Get the contract
                Contract contract = network.getContract(contractName);

                // Submit the transaction
                byte[] result = contract.submitTransaction(function, args);

                return new String(result, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error submitting transaction";
        }
    }

    public String queryTransaction(String function, String... args) {
        try {
            Gateway.Builder builder = Gateway.createBuilder();

            try (Gateway gateway = builder.identity(walletPath, userName).networkConfig(networkConfigPath).connect()) {
                // Access the network
                Network network = gateway.getNetwork(channelName);

                // Get the contract
                Contract contract = network.getContract(contractName);

                // Query the transaction
                byte[] result = contract.evaluateTransaction(function, args);

                return new String(result, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error querying transaction";
        }
    }
}
