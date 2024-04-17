# mc-plugins

This is a set of Minecraft Spigot plugins, developed for fun usage in our survival world we play on with some friends.

## Contributing

This is a monorepo using Gradle submodules.
Each plugin is available in it's own directory (starting with `plugin-`) and is developed, built and deployed separately.
A plugin can be implemented in either Java or Kotlin.
And the plugin.yml must not be written manually.
Instead, it is automatically generated during the Gradle build using a plugin.

### Local Development

TODO

### Release Workflow

To build and release, just merge or push to the main branch.
A GitHub Action will trigger and automatically create a tag and a release with bundled plugin `.jar` files of each plugin.
The released version number will be calculated based on conventional commits.
When no conventional commits are found, a release might not be created, so it is mandatory to use.
It also helps to maintain a readable changelog.
see: https://www.conventionalcommits.org
