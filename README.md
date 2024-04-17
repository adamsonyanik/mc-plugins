# mc-plugins

This is a set of minecraft spigot plugins, developed for fun usage in our survival world we play on with some friends.

## Contributing

This is a monorepo using gradle submodules.
Each plugin is available in the [plugin](plugin) directory and is developed, built and deployed separately.

### Local Development

TODO

### Release Workflow

To build and release, just merge or push to the main branch.
A GitHub Action will trigger and automatically create a tag and a release with bundled plugin `.jar` files of each plugin.
The released version number will be calculated based on conventional commits.
When no conventional commits are found, a release might not be created, so it is mandatory to use.
It also helps to maintain a readable changelog.
see: https://www.conventionalcommits.org
