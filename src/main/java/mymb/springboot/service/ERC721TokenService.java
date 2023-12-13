package mymb.springboot.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import mymb.springboot.dto.ERC721TokenDTO;
import mymb.springboot.entity.ERC721Token;
import mymb.springboot.exception.TokenNotFoundException;
import mymb.springboot.repository.ERC721ToeknRepository;
import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
@RequiredArgsConstructor
public class ERC721TokenService {

    private final ERC721ToeknRepository tokenRepository;
    private final NetworkService networkService;

    @Value("${fabric.channel}")
    private String fabricChannel;

    @Value("${fabric.mspId}")
    private String fabricMspId;

    @Value("${fabric.peerEndpoint}")
    private String fabricPeerEndpoint;

    @Value("${fabric.ordererEndpoint}")
    private String fabricOrdererEndpoint;

    // 데이터 불러오기
    public ERC721TokenDTO getTokenById(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new TokenNotFoundException("Token not found with ID : " + tokenId));
    }

    private ERC721TokenDTO convertToDTO(ERC721Token token) {
        ERC721TokenDTO tokenDTO = new ERC721TokenDTO();
        tokenDTO.setTokenId(token.getTokenId());
        tokenDTO.setOwner(token.getOwner());
        tokenDTO.setTokenUri(token.getTokenUri());
        return tokenDTO;
    }

    // 데이터 생성
    public ERC721TokenDTO createToken(ERC721TokenDTO tokenDTO) {
        // ERC721TokenDTO를 ERC721Token 엔터티로 변환
        ERC721Token newToken = mapDTOToEntity(tokenDTO);

        // NFT 발행 로직
        issueNFT(newToken);

        // 데이터베이스에 저장
        ERC721Token savedToken = tokenRepository.save(newToken);

        // 저장된 엔터티를 다시 DTO로 변환하여 반환
        return mapEntityToDTO(savedToken);
    }

    // DTO의 필드들을 entity에 매핑
    private ERC721Token mapDTOToEntity(ERC721TokenDTO tokenDTO) {
        ERC721Token token = new ERC721Token();
        token.setTokenId(tokenDTO.getTokenId());
        token.setOwner(tokenDTO.getOwner());
        token.setTokenUri(tokenDTO.getTokenUri());
        return token;
    }

    // entity의 필드들을 DTO에 매핑
    private ERC721TokenDTO mapEntityToDTO(ERC721Token token) {
        ERC721TokenDTO tokenDTO = new ERC721TokenDTO();
        tokenDTO.setTokenId(token.getTokenId());
        tokenDTO.setOwner(token.getOwner());
        tokenDTO.setTokenUri(token.getTokenUri());
        return tokenDTO;
    }

    private void issueNFT(ERC721Token token) throws Exception {
        // 네트워크 연결 및 체인코드 등록
        try (Gateway gateway = networkService.createGateway()) {
            Network network = networkService.connectToNetwork(gateway);
            Contract contract = networkService.getContract(network, "fabcar");

            // 트랜잭션 - issue(발행) 함수 실행
            byte[] result = contract.submitTransaction("issue", token.getTokenId(), token.getOwner(), token.getTokenUri());

            // 결과 로깅
            // 적절한 로깅 프레임워크나 방식을 사용하여 로그를 출력하도록 수정
            log.info("Issue transaction has been committed to the ledger: {}", Arrays.toString(result));
        }
    }
}