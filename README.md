# 7-zip Distro #

[![Java CI](https://github.com/hazendaz/7-zip/workflows/Java%20CI/badge.svg)](https://github.com/hazendaz/7-zip/actions?query=workflow%3A%22Java+CI%22)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.hazendaz.7zip/7zip?logo=apache-maven&label=maven%20central)](https://central.sonatype.com/artifact/com.github.hazendaz.7zip/7zip)
[![LGPL v3](https://img.shields.io/badge/License-LGPL_v3-blue.svg?logo=gnu)](https://www.gnu.org/licenses/lgpl-3.0)

![hazendaz](src/site/resources/images/hazendaz-banner.jpg)

This project takes 7-zip distribution from mirror downloads for distribution using maven central.

For more information on 7-zip, please see [7-zip](https://www.7-zip.org/)

# Motivation #

7-zip does not currently provide a maven central distribution of the project. This project aims to solve that by providing users an alternative location to pull distribution.

# Use Case #

Maven based storage of distribution in common location to offer more secure download location.

# Note #

This was primarly necessary when there were not immutability in releases and alternative distribution was necessary.  Now that sonatype is imposing caps, this is deprecated.
The 7-zip team does distribute to https://github.com/ip7z/7zip/releases.  They are not immutable yet, but I'll open a ticket to ask that become the standard.
