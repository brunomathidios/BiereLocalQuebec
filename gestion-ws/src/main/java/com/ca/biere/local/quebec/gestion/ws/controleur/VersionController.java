package com.ca.biere.local.quebec.gestion.ws.controleur;

import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping(value = "${application.domain}/version")
public class VersionController {

    @GetMapping
    public String versionInformation() {
        return this.readGitProperties();
    }

    private String readGitProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.properties");
        try {
            return this.readFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return "Version information could not be retrieved";
        }
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
