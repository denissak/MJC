package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for working with certificates.
 */
@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Reads all certificates.
     *
     * @return all certificates
     */
    @GetMapping
    public ResponseEntity<?> readAllCertificates() {
        List<CertificateDto> certificateDtoList = certificateService.readAll();
        return ResponseEntity.ok(certificateDtoList);
    }

    /**
     * Reads certificate with passed id.
     *
     * @param id the id of certificate to be read
     * @return certificate with passed id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> readCertificate(@PathVariable long id) {
        CertificateDto certificateDto = certificateService.readById(id);
        return ResponseEntity.ok(certificateDto);
    }

    /**
     * Search by specified certificate values
     *
     * @param tagValue    tag name
     * @param name        whole or partial certificate name
     * @param description whole or partial certificate description
     * @param sortBy      Sort target field (name or date)
     * @param sortOrder   Sort type (asc or desc)
     * @return all certificates from search terms
     */
    @GetMapping("/search")
    public ResponseEntity<?> readCertificateWithParams(@RequestParam(required = false) String tagValue,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String description,
                                                       @RequestParam(required = false) String sortBy,
                                                       @RequestParam(required = false) String sortOrder) {
        return ResponseEntity.ok(certificateService.readCertificateWithDifferentParams(tagValue, name, description, sortBy, sortOrder));
    }

    /**
     * Creates and saves the passed certificate.
     *
     * @param certificateDto the certificate to be saved
     */
    @PostMapping
    public ResponseEntity<?> createCertificate(@RequestBody CertificateDto certificateDto) {
        certificateService.create(certificateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Update the passed certificate.
     *
     * @param id             the certificate id which need to update
     * @param certificateDto the certificate to be saved
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCertificate(@PathVariable long id, @RequestBody CertificateDto certificateDto) {
        certificateService.update(id, certificateDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes certificate with passed id.
     *
     * @param id the id of certificate to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        certificateService.delete(id);
    }
}