package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public ResponseEntity<?> readAllCertificates() {
        List<CertificateDto> certificateDtoList = certificateService.readAll();
        return ResponseEntity.ok(certificateDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> readCertificate(@PathVariable long id) {
        CertificateDto certificateDto = certificateService.readById(id);
        return ResponseEntity.status(HttpStatus.OK).body(certificateDto);
    }

    @GetMapping("/search")
    public List<CertificateDto> readCertificateWithParams(@RequestParam(required = false) String tagValue,
                                                          @RequestParam(required = false) String query,
                                                          @RequestParam(required = false) String sort,
                                                          @RequestParam(required = false) boolean ascending) {
        return certificateService.readCertificateWithDifferentParams(tagValue, query, sort, ascending);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto createCertificate(@RequestBody CertificateDto certificateDto) {
        return certificateService.create(certificateDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable long id, @RequestBody CertificateDto certificateDto) {
        certificateService.update(id, certificateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        certificateService.delete(id);
    }

    /*@GetMapping("/search")
    public List<CertificateDto> readCertificateWithParams() {
        return null;
    }*/
}
