# edoras-one-initializr [![Build Status](https://travis-ci.org/rvillars/edoras-one-initializr.svg?branch=master)](https://travis-ci.org/rvillars/edoras-one-initializr)

See https://edoras-one-initializr.cfapps.io

Still in early alpha state. 

Using the generated code needs an edoras one license (see http://www.edorasware.com) and access to the appropriate repositories. Don't even try to get it running without.

Already working:
- Maven project stub creation based on edoras-one-starter 2.0.
- Artifact coordinates are evaluated.
- First very unstable draft of addon support.
- Support for different Java versions
- .war and .executable .jar project generation
- Support for additional "Short Name" property (used for config files, tenant, etc.) 

Todo:
- Support for Version 1.0
- Support for Gradle Build
- Support for Groovy / Kotlin
- Metadata from Repository/Addon Marketplace
- IntelliJ integration
- Config generation for addons
