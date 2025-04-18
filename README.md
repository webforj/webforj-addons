# webforj-addons

<p>

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=webforj_webforj-addons&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=webforj-addons)
[![Verify](https://github.com/webforj/webforj-addons/actions/workflows/verify.yml/badge.svg)](https://github.com/webforj/webforj-addons/actions/workflows/verify.yml)

</p>

**webforj-addons** enriches the main [webforJ framework](https://github.com/webforj/webforj) with a comprehensive suite of additional UI components and specialized services, enhancing your web development capabilities with Java.

This repository contains supplementary modules designed to integrate seamlessly with `webforj`, providing extended functionality for modern web applications.

## Features

* **Extended UI Component Library**: Offers a growing collection of ready-to-use UI components beyond the core `webforj` set, enabling richer user interfaces.
* **Specialized Services**: Provides additional backend services and integrations (like WebAuthn and SimpleRouting) that complement the core framework's capabilities.
* **Seamless Integration**: Designed to work harmoniously within the `webforj` ecosystem.

## Getting Started

To use `webforj-addons` in your project, add the necessary dependencies to your `pom.xml`.  You can include the entire suite or pick specific component/service modules.

Example (including a specific component):

```xml
<dependency>
  <groupId>com.webforj.addons</groupId>
  <artifactId>webforj-side-menu</artifactId>
  <version>latest</version>
</dependency>
````

Or to include the WebAuthn service:

```xml
<dependency>
  <groupId>com.webforj.addons</groupId>
  <artifactId>webforj-webauthn</artifactId>
  <version>latest</version>
</dependency>
```

Replace the `artifactId` with the specific addon module you need and ensure the `version` matches the desired release.

## Documentation

General documentation for the core `webforj` framework can be found [here](https://docs.webforj.com/).

Key sections from the core documentation relevant to using these addons include:

* [Application Basics](https://docs.webforj.com/docs/introduction/basics)
* [Component Overviews](https://docs.webforj.com/docs/components/overview)