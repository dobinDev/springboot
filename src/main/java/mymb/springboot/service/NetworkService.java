package mymb.springboot.service;

import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Contract;

@Service
public class NetworkService {

    public Gateway createGateway(Wallet wallet, String userName, Path privateKeyPath, Path signedCertPath) throws IOException, IllegalAccessException, InstantiationException, NetworkConfigurationException {
        Gateway.Builder builder = Gateway.createBuilder();
        try (Gateway gateway = builder.identity(wallet, userName).networkConfig(networkConfig()).connect()) {
            // Gateway 사용 코드
            return gateway;
        }
    }

    public Network connectToNetwork(Gateway gateway, String channelName) {
        return gateway.getNetwork(channelName);
    }

    public Contract getContract(Network network, String chaincodeName) {
        return network.getContract(chaincodeName);
    }

    private Path networkConfig() throws IOException, NetworkConfigurationException {
        // 동적으로 파일 경로 생성 또는 다른 방법으로 네트워크 구성 파일 로드
        return Paths.get("path/to/network-config.yaml");
    }
}
