# mc-plugins

This is a set of minecraft spigot plugins, developed for fun usage in our survival world we play on with some friends.

## Contributing

### Local Development

TODO

### Release Workflow

This is a monorepo using gradle submodules to build all plugins separately.
To build and release, merge / push to the main branch.
A GitHub Action will trigger and automatically create a tag and a release with bundled plugin `.jar` files.
The released version number will be calculated based on conventional commits.
When no conventional commits are found, a release might not be created, so it is mandatory to use.
It also helps to maintain a readable changelog.
see: https://www.conventionalcommits.org
