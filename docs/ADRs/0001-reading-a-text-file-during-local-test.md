# Reading a text file during local test

* Status: Accepted
* Deciders: Marcin
* Date: 2023-05-05

## Context and Problem Statement

It is useful to read a text file during unit tests to create mock JSON results.

**Which approach should developer follow in tests?**

## Considered Options

* Use `context` to read the file either from the `res` or `assets` folders
* Use `ClassLoader` to read the file from the `resources` folder

## Decision Outcome

Chosen option: "Use `ClassLoader` to read the file from the `resources` folder", because

* in unit tests, we aim to avoid using `context`
* `resources` folder provides stronger boundary between other app's resources and assets

## Consequences

* Good, because we do not use `context` in local tests
* Good, because we keep api responses in separation from other resources and assets

## How to use Java ClassLoader

    private fun getInputStreamFromResource(fileName: String): InputStream? {
        return ClassLoader.getSystemResourceAsStream(fileName)
    }

    private fun readFileFromResources(fileName: String): String {
        return getInputStreamFromResource(fileName)
                ?.bufferedReader()
                ?.use { it.readText() }
                ?: ""
    }
    
    private fun readBinaryFileFromResources(fileName: String): ByteArray {
        ByteArrayOutputStream().use { byteStream ->
            getInputStreamFromResource(fileName)?.copyTo(byteStream)
            return byteStream.toByteArray()
        }
    }
