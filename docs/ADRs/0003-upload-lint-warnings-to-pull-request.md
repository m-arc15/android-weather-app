# Upload Lint warnings to GitHub pull request (using SARIF)

* Status: Accepted
* Deciders: Marcin
* Date: 2023-05-08

## Context and Problem Statement

There are various static analysers for Android code.

Is it possible to display static analysis issues in pull requests?

## Considered Options

* GitHub code scanning
* additional third-party tools

## Decision Outcome

Chosen option: "GitHub code scanning", because

* it is GitHub own tool to display static analysis issues in pull requests

Code scanning works in 2 different modes:

* CodeQL analysis mode: the analysis will be run in a separate tool maintained by the GitHub team and it supports a limited number of languages
* Report file mode: in this case, we will need to generate specially-formatted report files manually and upload them using github/codeql-action/upload-sarif action

> Note: because CodeQL does not support Kotlin (for now) and has no direct integrations with widely used Kotlin/Android related static analysis tools, we need to use the second mode to display errors and warnings in pull requests. Specially formatted files used for it are SARIF files. Android Lint supports generation of SARIF files.

## Consequences

* Good, because we don't need any third-party tools
* Good, because GitHub display warnings and errors directly in the GitHub interface without the need for bots
* Bad, because the feature is available for open-source projects and GitHub Enterprise Cloud, and not normal private repositories

## More Information

* [Bumble Tech example how to add Android Lint warnings in GitHub pull requests](https://medium.com/bumble-tech/android-lint-and-detekt-warnings-in-github-pull-requests-2880df5d32af)
