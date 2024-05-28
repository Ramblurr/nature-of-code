# Nature of Code - Clojure

[![License][license]][license-url]

This is a ClojureScript implementation of the samples and exercises from [Nature of Code (2nd Edition)](noc2) by Daniel Shiffman 

> ➡️View live at: https://ramblurr.github.io/nature-of-code/

## Dependencies

`clj`, `shadow-cljs`, `node` and

- [Clojure CLI tools](https://clojure.org/guides/install_clojure)
- [`babashka`](https://github.com/babashka/babashka#installation)
- [`nvm`](https://github.com/nvm-sh/nvm#installing-and-updating).

## Developing with Clerk

To start a server for local Clerk development, run

```sh
bb clerk-watch
```

This will start the Clerk server at http://localhost:7777 with a file
watcher that updates the page each time any file in the `notebooks` directory changes.

To manually start the Clerk webserver, start a REPL by running

```sh
clj
```

Then start the server:

```clj
(start-with-shadow!)
```

To show a file, pass it to `clerk/show!`:

```clj
(clerk/show! "notebooks/a_file.clj")
```

These commands work because dev/user.clj requires `nextjournal.clerk` under a
`clerk` alias, and defines a `start!` function.

## Static Builds

### Local Build

Run

```
bb build-static
```

To generate a static site locally.

### Garden

The easiest way to share a static build is to push to GitHub, then visit

https://github.clerk.garden/ramblurr/nature-of-code

## Linting

Run

```
clj-kondo --copy-configs --dependencies --lint "$(clojure -Spath)"
```

Then

```
bb lint
```

## License

```
Copyright © 2024 Casey Link

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
```

[license]: https://img.shields.io/github/license/ramblurr/nature-of-code
[license-url]: LICENSE
[noc2]: https://github.com/nature-of-code/noc-book-2
