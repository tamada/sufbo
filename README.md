# Sufbo

## Goals

Sufbo aims to de-obfuscate the obfuscated programs by the name obfuscation tool, such as Dash-O.
Therefore, we collect opcode sequences of methods from existing Java packages in the Maven repository.

## Components

### Storage

This component extracts method information from given Java packages, and store them into a certain storage.

#### Stored information

* Group Id
    * Artifact Id
        * Version
            * Class name
                * Method name
                * signature
                * # of opcode sequence
                * sha256 hash of opcode sequence
                * opcode sequence

### Search

This component searches method names based on its information from above storages.


## Usage

### Store methods information

```
$ java -jar build/sufbo.jar extract --help
java -jar build/sufbo.jar extract [OPTIONS] <ARGUMENTS...>
    Extract method information and store them into some sotrage.
OPTIONS:
    -h, --help:            print this message.
    -a, --artifactId <ID>: specify the artifact id of a given jar file in argument.
    -g, --groupId <ID>:    specify the group id of a given jar file in argument.
    -v, --version <ID>:    specify the version of a given jar file in argument.
    -d, --dest <DEST>:     specify the destination of result.  Default is STDOUT.
ARGUMENTS:
    Directory of maven repository or jar file.
    If jar file was specified, ids must be specified by OPTIONS.
```
