# mc-plugins

This is a monorepo using gradle submodules to build all plugins separately.
To build and release, merge / push to the main branch.
A GitHub Action will trigger and automatically create a tag and a release with bundled plugin `.jar` files.
The released version number will be calculated based on conventional commits.
It also helps to maintain a readable changelog.
see: https://www.conventionalcommits.org
