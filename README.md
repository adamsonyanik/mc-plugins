# mc-plugins

This is a monorepo using gradle sub modules to build all plugins seperately.
To build and release, just locally create and push a tag.
A GitHub Action will trigger, compile your code and upload the jar files to a release.
Please not, that a tag that should be turned into a release must start with v, e.g. v1.0.0
