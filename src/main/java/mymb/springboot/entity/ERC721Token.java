package mymb.springboot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ERC721Token {

    @Id
    @GeneratedValue
    @Column(name = "token_id")
    private Long tokenId;

    private String owner;
    private String tokenUri;
}
