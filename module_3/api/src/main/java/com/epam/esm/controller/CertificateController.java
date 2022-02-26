package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller for working with certificates.
 */
@RestController
@RequestMapping("/certificate")
public class CertificateController {

    private final CertificateService certificateService;

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
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CertificateDto> readAllCertificates(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                               @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<CertificateDto> certificateDtoList = certificateService.readAll(page, size);
        return addLinksToCertificate(certificateDtoList);
    }

    /**
     * Reads certificate with passed id.
     *
     * @param id the id of certificate to be read
     * @return certificate with passed id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto readCertificate(@PathVariable long id) {
        Link link = linkTo(CertificateController.class).withSelfRel();
        CertificateDto certificateDto = certificateService.readById(id);
        certificateDto.add(link);
        return certificateDto;
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
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CertificateDto> readCertificateWithParams(@RequestParam(required = false) String[] tagValue,
                                                                     @RequestParam(required = false) String name,
                                                                     @RequestParam(required = false) String description,
                                                                     @RequestParam(required = false) String sortBy,
                                                                     @RequestParam(required = false) String sortOrder,
                                                                     @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                     @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<CertificateDto> certificateDtoList =
                certificateService.readCertificateWithDifferentParams(tagValue, name, description, sortBy, sortOrder, page, size);
        return addLinksToCertificate(certificateDtoList);
    }

    /**
     * Creates and saves the passed certificate.
     *
     * @param certificateDto the certificate to be saved
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto createCertificate(@RequestBody CertificateDto certificateDto) {
        return certificateService.create(certificateDto);
    }

    /**
     * Update the passed certificate.
     *
     * @param id             the certificate id which need to update
     * @param certificateDto the certificate to be saved
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateCertificate(@PathVariable long id, @RequestBody CertificateDto certificateDto) {
        return certificateService.update(id, certificateDto);
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

    private CollectionModel<CertificateDto> addLinksToCertificate(List<CertificateDto> certificateDtoList) {
        for (final CertificateDto certificateDto : certificateDtoList) {
            Link selfLink = linkTo(methodOn(CertificateController.class)
                    .readCertificate(certificateDto.getId())).withSelfRel();
            certificateDto.add(selfLink);
        }
        Link link = linkTo(CertificateController.class).withSelfRel();
        return CollectionModel.of(certificateDtoList, link);
    }
}
