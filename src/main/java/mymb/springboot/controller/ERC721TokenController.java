package mymb.springboot.controller;

import lombok.RequiredArgsConstructor;
import mymb.springboot.dto.ERC721TokenDTO;
import mymb.springboot.service.ERC721TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/erc721")
public class ERC721TokenController {

    private final ERC721TokenService tokenService;

    @GetMapping("/{tokenId}")
    public ResponseEntity<ERC721TokenDTO> getTokenById(@PathVariable Long tokenId) {
        ERC721TokenDTO tokenDTO = tokenService.getTokenById(tokenId);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ERC721TokenDTO> createToken(@RequestBody ERC721TokenDTO tokenDTO) {
        ERC721TokenDTO createdToken = tokenService.createToken(tokenDTO);
        return new ResponseEntity<>(createdToken, HttpStatus.CREATED);
    }
}