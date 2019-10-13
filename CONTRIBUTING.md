# Contributing to HabitatUL

##### There are many ways to contribute to the HabitatUL project: logging bugs, submitting pull requests, reporting issues, and creating suggestions.

## Development Workflow

### Build the Project

In order to build the project, we use maven as build automation tool. Hence, you can simply go ahead and use `mvn exec:java` to start the application and start interacting with using your favorite HTTP request sender.

### Automated Testing

Run the unit tests directly from a terminal by running `mvn verify` from the root folder.

Coverage reports are generated using Jacoco during the report build phase. These reports are stored in the `HabitatUL/target/site/jacoco` folder. To consult these reports, you can simply open the index.html into your favorite browser.

If you are on a Unix platform, you can also launch the `scripts/test_coverage.sh` script, which will run all tests and display coverage reports on completion.

It is also possible to run unit tests seperately using `mvn surefire:test`.

Similarly for integration tests, you can run `mvn failsafe:integration-test`.

### Linting

We use google-java-format for linting our sources. You can run it across the sources by calling following the instruction guide available [here](https://github.com/google/google-java-format).

## Work Branches

Even if you have push rights on the SamuelCabralCruz/GLO-400 repository, you should create a personal fork and create feature branches there when you need them. This keeps the main repository clean and your personal workflow cruft out of sight.

## Pull Requests

To enable us to quickly review and accept your pull requests, always create one pull request per issue and link the issue in the pull request. Never merge multiple requests in one unless they have the same root cause. Be sure to follow our coding guidelines and keep code changes as small as possible. Avoid pure formatting changes to code that has not been modified otherwise. Pull requests should contain tests whenever possible.

## Merge Pull Requests

Pull requests should be squased into the default branch (master) and the commit should follow the standard dicted by [Conventional Changelog Standard](https://github.com/conventional-changelog/conventional-changelog-config-spec/blob/master/versions/2.1.0/README.md). Squased commit details should be removed before the merge. This way, your commit style does not really matter as long as it is on your feature branch, but the merge commit should follow the convential to keep the work history clean, easy to use and, above all, easy to read.

All commits made to the default branch should have the following format:

```<type_of_change> (#<issue_number>): <commit_message> (#<pull_request_number>)```

Here are some examples:

- `feat (#60): gateway quote resource (#62)`
- `refactor (#112): better quote response serialization (#113)`
- `fix (#187): change date format quote expiration (#193)`

## Knowledge Sharing

In order to make the knowledge sharing easier, our team adopted the usage of Wiki Pages. The task of maintaining these wiki pages up to date belongs to everyone. At the moment, you notify so element that are not true anymore or outdated, you should update this wiki and inform the rest of the team of these modifications.

At the moment, we principally use two wiki pages that are:

- Client Interaction: keep track of client answers to our questions.
- Ubiquitous Language: reference document to understand the terminology of the business domain we are modeling.

## Where to Contribute

Check out the full issues list for a list of all potential areas for contributions.

To improve the chances to get a pull request merged you should select an issue that is labelled with the help-wanted or bug labels. If the issue you want to work on is not labelled with help-wanted or bug, you can start a conversation with the issue owner asking whether an external contribution will be considered.

## Suggestions

We're also interested in your feedback for the future of HabitatUL. You can submit a suggestion or feature request through the issue tracker. To make this process more effective, we're asking that these include more information to help define them more clearly. Make sure to inspect the current backlog before creating duplicated issues to help the issue owner keeping it clean and steady.

## Discussion Etiquette

In order to keep the conversation clear and transparent, please limit discussion to English and keep things on topic with the issue. Be considerate to others and try to be courteous and professional at all times.

## Code of Conduct

This project has adopted the Contributor Covenant Code of Conduct. For more information see the [code of conduct](CODE_OF_CONDUCT.md).
