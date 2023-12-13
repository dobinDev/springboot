package mymb.springboot.dto;

import lombok.Data;

@Data
public class ERC721TokenDTO {

    private Long tokenId;
    private String owner;
    private String tokenUri;
}
