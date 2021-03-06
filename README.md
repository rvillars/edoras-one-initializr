# edoras-one-initializr [![Build Status](https://travis-ci.org/rvillars/edoras-one-initializr.svg?branch=master)](https://travis-ci.org/rvillars/edoras-one-initializr)

See https://edoras-one-initializr.cfapps.io

Still in early alpha state. 

Using the generated code needs an edoras one license (see http://www.edorasware.com) and access to the appropriate repositories. Don't even try to get it running without.

Already working:
- Maven project and pom.xml creation based on edoras-one-starter 1.6 or 2.0.
- Gradle project and build.xml creation based on edoras-one-starter 1.6 or 2.0.
- Support for additional "Short Name" property (used for config files, tenant, etc.)
- Gradle .war and .executable .jar project generation (needs gradle 3.0+)
- Maven .war and .executable .jar project generation
- First very unstable draft of addon support.
- Support for different Java versions.

Todo:
- Improve addon dependency support
- Config generation for addons
- Metadata from Repository/Addon Marketplace
- Code cleanup
- Fixing a lot of bugs
- Support for Groovy / Kotlin

## Running form Command Line

Its also possible to run the initializr from a command line using the curl command e.g. like this:

```bash
curl http://edoras-one-initializr.cfapps.io/starter.tgz -d artifactId=my-app -d groupId=com.edorasware.app -d shortName=myApp | tar -zvx
```

## Change Log

### 08-07-2017
- Updated VersionParser to allow Starter-Patch versions (Versioning scheme: ProductMajor.ProductMinor.ProductPatch-ProductQualifier-StarterPatch-StarterQualifier) 
- Corrected README.md template (minor formatting and spelling)
- Made the basic artifact metadata fields (artifactId, groupId and shortname) required.
- Added field description as help-block to html (e.g. for explanation of shortName)
- Added field validation to shortName field.
- Added change log to README.md

### 08-06-2017
- Added versions 1.6.8 and 2.0.0-M6