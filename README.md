# compojure-app

This is a simple Clojure web application using the **Compojure** library for routing and **Ring** for handling HTTP requests.

## Prerequisites

Make sure you have the following installed:

- [Clojure](https://clojure.org/guides/getting_started)
- [Leiningen](https://leiningen.org/)

## Installation

Clone the repository:

```bash
git clone https://github.com/your-username/compojure-project.git
cd compojure-project
```

Install dependencies using Leiningen:

```bash
lein deps
```

## Running the Application

To start the application in development mode, use:

```bash
lein ring server
```

This will start the app on http://localhost:3000.

## Project Structure

```bash
compojure-project/
├── src/
│   └── compojure-app/
│       ├── core.clj        
├── resources/
└── project.clj           
```

## License

Copyright © 2024 FIXME

This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary Licenses when the conditions for such availability set forth in the Eclipse Public License, v. 2.0 are satisfied: GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version, with the GNU Classpath Exception which is available at https://www.gnu.org/software/classpath/license.html.
