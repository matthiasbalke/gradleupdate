# Gradle Update [![CircleCI](https://circleci.com/gh/int128/gradleupdate.svg?style=svg)](https://circleci.com/gh/int128/gradleupdate) [![Gradle Status](https://gradleupdate.appspot.com/int128/gradleupdate/status.svg?branch=master)](https://gradleupdate.appspot.com/int128/gradleupdate/status)

Gradle Update keeps the latest Gradle Wrapper by pull requests for your repositories on GitHub.


## How works

### [TODO] When a _user_ requested update for a _repository_

1. Receive a request for _repository_.
  1. Check if _user_ have permission to push to the _repository_.
  2. Check if Gradle Update have permission to pull from the _repository_.
  3. Queue a task for the _repository_.
2. In a task,
  1. Check if the _repository_:_default-branch_ does not have the latest version of Gradle Wrapper.
  2. Fork the _repository_ as _fork_ and sync _default-branch_ to upstream.
  3. Create or update a commit with the latest version of Gradle Wrapper as _fork_:_gradle-branch_.
  4. Create or update a pull request from _fork_:_gradle-branch_ onto _repository_:_default-branch_.


### [TODO] Periodically

1. Check the latest version of Gradle from [services.gradle.org](https://services.gradle.org).
2. If a new version of Gradle is found,
  1. Trigger updating Gradle Wrapper on [latest-gradle-wrapper](https://github.com/int128/latest-gradle-wrapper).
  2. Wait until the new version is available on [latest-gradle-wrapper](https://github.com/int128/latest-gradle-wrapper).
  3. Get a list of repositories to be updated and queue tasks for each _repository_.


### [WIP] When a badge for the _repository_ is requested,

1. Check the version of Gradle Wrapper in the _repository_.
2. If it is up-to-date, respond the green image.
3. If it is out-of-date, respond the red image.
4. If it has no Gradle Wrapper, respond the grey image.


## Contribution

Gradle Update is an open source software licensed under the Apache License Version 2.0. Feel free to open issues or pull requests.


### Architecture

* API Server
  * Spring Boot
  * Heroku
  * Gradle


### How to Run

```bash
./gradlew bootRun
```
