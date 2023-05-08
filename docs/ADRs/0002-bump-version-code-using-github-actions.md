# Bump version code using GitHub Actions

* Status: Accepted
* Deciders: Marcin
* Date: 2023-05-08

## Context and Problem Statement

Google Play Store has the policy: every artifact uploaded and served from Google Play Store should have a unique version code.

**How to increment the version code?**

## Considered Options

* Manually - developer is responsible of bumping and maintaining the version code
* Automatically - using GitHub Actions

## Decision Outcome

Chosen option: "Automatically - using GitHub Actions", because

* it is repetitive "boring" work for developer - to increment the version code number
* it is easy to introduce errors manually

The proposed workflow:

* create a branch called `bump/vX.Y.Z` and push to remote
* the GitHub Action gets triggered and will create a PR that
  * sets the version name to `X.Y.Z`
  * increases the version by 1
* review the PR to ensure that all the changes are correct and proceed with the new release

The **bump** script file:
`.github/workflows/bump.yaml`

## Consequences

* Good, because it reduces risk of version code duplicates
* Good, because it reduces risk of invalid version codes
* Good, because we have standardized approach for versioning for Google Play Store.
