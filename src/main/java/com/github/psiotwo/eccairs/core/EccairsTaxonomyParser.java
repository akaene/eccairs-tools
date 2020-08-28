package com.github.psiotwo.eccairs.core;

import com.github.psiotwo.eccairs.core.model.EccairsDictionary;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EccairsTaxonomyParser {

    /**
     * Parses an ECCAIRS taxonomy file
     *
     * @param file
     * @return
     */
    public EccairsDictionary parse(File file) {
        log.info("Processing file '{}'", file);
        try (final FileInputStream fis = new FileInputStream(file);) {
            // Setup classes to parse XSD file for complex types
            final JAXBContext jaxbContext = JAXBContext.newInstance(EccairsDictionary.class);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (EccairsDictionary) jaxbUnmarshaller.unmarshal(fis);
        } catch (JAXBException | IOException e) {
            log.error("An exception during unmarshalling.", e);
        }
        return null;
    }

    public static void main(String[] args) {
        final EccairsDictionary dictionary = new EccairsTaxonomyParser().parse(new File(args[0]));
        EccairsTaxonomyUtils.statistics(dictionary,System.out);
    }
}