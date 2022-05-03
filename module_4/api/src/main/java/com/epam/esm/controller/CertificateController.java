package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller for working with certificates.
 */
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Value("${upload.path}")
    private String uploadPath;

    private final CertificateService certificateService;
    private final TagService tagService;

    @Autowired
    public CertificateController(CertificateService certificateService, TagService tagService) {
        this.certificateService = certificateService;
        this.tagService = tagService;
    }

    /**
     * Read all certificates.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return all certificates
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CertificateDto> readAllCertificates(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                               @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<CertificateDto> certificateDtoList = certificateService.readAll(page, size);
        return addLinksToCertificate(certificateDtoList);
    }

    /**
     * Read certificate with passed id.
     *
     * @param id the id of certificate to be read
     * @return certificate with passed id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto readCertificate(@PathVariable long id) {
        try {
            Link link = linkTo(CertificateController.class).withSelfRel();
            CertificateDto certificateDto = certificateService.readById(id);
            certificateDto.add(link);
            return certificateDto;
        } catch (RuntimeException e) {
            throw NotFoundException.notFoundWithCertificateId(id).get();
        }
    }

    /**
     * Search by specified certificate values
     *
     * @param tagValue    tag name
     * @param name        whole or partial certificate name
     * @param description whole or partial certificate description
     * @param sortBy      Sort target field (name or date)
     * @param sortOrder   Sort type (asc or desc)
     * @param page        numbers of page
     * @param size        number of elements per page
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
     * Create and save the passed certificate.
     *
     * @param certificateDto the certificate to be saved
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCertificate(HttpServletResponse response,
                                  @RequestParam("name") String name,
                                  @RequestParam("description") String description,
                                  @RequestParam("price") String price,
                                  @RequestParam("duration") String duration,
                                  @RequestParam("tagid") String[] tagId,
                                  @RequestParam("image") MultipartFile file) throws IOException {
        try {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            List<TagDto> tagDtoList = Arrays.stream(tagId).map(tag -> tagService.readById(Long.parseLong(tag))).collect(Collectors.toList());
            CertificateDto certificateDto = CertificateDto.builder()
                    .name(name)
                    .description(description)
                    .price(Double.parseDouble(price))
                    .duration(Integer.parseInt(duration))
                    .image(resultFilename)
                    .tags(tagDtoList)
                    .build();
            certificateService.create(certificateDto);
            response.sendRedirect("mainpage");
        } catch (RuntimeException e) {
            throw DuplicateException.certificateExists().get();
        }
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
        try {
            return certificateService.update(id, certificateDto);
        } catch (RuntimeException e) {
            throw NotFoundException.notFoundWithCertificateId(id).get();
        }
    }

    /**
     * Delete certificate with passed id.
     *
     * @param id the id of certificate to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        try {
            certificateService.delete(id);
        } catch (RuntimeException e) {
            throw NotFoundException.notFoundWithCertificateId(id).get();
        }
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
