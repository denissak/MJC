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
/*       return ResponseEntity.status(404).body();*/
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> readCertificate(@PathVariable long id) {
        CertificateDto certificateDto = certificateService.readById(id);
        return ResponseEntity.status(HttpStatus.OK).body(certificateDto);
    }

    @GetMapping("/search")
    public ResponseEntity<?> readCertificateWithParams(@RequestParam(required = false) String tagValue,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String description,
                                                          @RequestParam(required = false) String sortBy,
                                                          @RequestParam(required = false) String sortOrder) {
        return ResponseEntity.ok(certificateService.readCertificateWithDifferentParams(tagValue, name, description, sortBy, sortOrder));
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

}
