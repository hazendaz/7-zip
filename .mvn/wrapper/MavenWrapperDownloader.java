/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public final class MavenWrapperDownloader {
    private static final String WRAPPER_VERSION = "3.3.4";

    private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("MVNW_VERBOSE"));

    /**
     * Keep these conservative. This class is only a fallback when curl/wget aren't available.
     *
     * You can override via env vars:
     *  - MVNW_CONNECT_TIMEOUT_MS
     *  - MVNW_READ_TIMEOUT_MS
     */
    private static final int CONNECT_TIMEOUT_MS = intEnv("MVNW_CONNECT_TIMEOUT_MS",
            (int) Duration.ofSeconds(15).toMillis());
    private static final int READ_TIMEOUT_MS = intEnv("MVNW_READ_TIMEOUT_MS",
            (int) Duration.ofSeconds(60).toMillis());

    public static void main(String[] args) {
        log("Apache Maven Wrapper Downloader " + WRAPPER_VERSION);

        if (args.length != 2) {
            System.err.println(" - ERROR wrapperUrl or wrapperJarPath parameter missing");
            System.exit(1);
        }

        try {
            log(" - Downloader started");
            final URL wrapperUrl = URI.create(args[0]).toURL();
            enforceHttps(wrapperUrl);

            final Path baseDir = Path.of(".").toAbsolutePath().normalize();
            final Path wrapperJarPath = baseDir.resolve(args[1]).normalize();
            if (!wrapperJarPath.startsWith(baseDir)) {
                throw new IOException("Invalid path: outside of allowed directory");
            }
            downloadFileFromURL(wrapperUrl, wrapperJarPath);
            log("Done");
        } catch (IOException e) {
            System.err.println("- Error downloading: " + e.getMessage());
            if (VERBOSE) {
                e.printStackTrace();
            }
            System.exit(1);
        }
    }

    private static void downloadFileFromURL(URL wrapperUrl, Path wrapperJarPath) throws IOException {
        log(" - Downloading to: " + wrapperJarPath);

        final String username = System.getenv("MVNW_USERNAME");
        final String passwordEnv = System.getenv("MVNW_PASSWORD");
        if (username != null && passwordEnv != null) {
            final char[] password = passwordEnv.toCharArray();
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        }

        Files.createDirectories(wrapperJarPath.getParent());
        final Path temp = Files.createTempFile(wrapperJarPath.getParent(), wrapperJarPath.getFileName().toString(), ".tmp");

        try {
            final URLConnection connection = wrapperUrl.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(READ_TIMEOUT_MS);

            try (InputStream inStream = connection.getInputStream()) {
                Files.copy(inStream, temp, StandardCopyOption.REPLACE_EXISTING);
            }

            Files.move(temp, wrapperJarPath, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(temp);
        }

        log(" - Downloader complete");
    }

    private static void enforceHttps(URL url) throws IOException {
        final String protocol = url.getProtocol();
        if (!"https".equalsIgnoreCase(protocol)) {
            throw new IOException("Refusing to download over non-HTTPS: " + protocol);
        }
    }

    private static int intEnv(String name, int defaultValue) {
        final String raw = System.getenv(name);
        if (raw == null || raw.isBlank()) {
            return defaultValue;
        }
        try {
            final int parsed = Integer.parseInt(raw.trim());
            return parsed > 0 ? parsed : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static void log(String msg) {
        if (VERBOSE) {
            System.out.println(msg);
        }
    }

}
