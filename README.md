# Cross-Library Test Automation Framework

![Framework Logo](T-Grid.png)

Welcome to the Cross-Library Test Automation Framework repository! This framework aims to provide a generic and flexible solution for conducting test automation across various libraries and technologies. It allows you to write tests, which can then be executed using different libraries and tools, such as Selenium, Playwright, Rest Assured, Spring Boot, JMeter, and Gatling, without requiring any changes to the test logic or core logic classes.

## Table of Contents
- [Introduction](#introduction)
- [Key Features](#key-features)
- [Getting Started](#getting-started)
- [Framework Architecture](#framework-architecture)
- [Test Categories](#test-categories)
- [Dynamic Remote Execution](#dynamic-remote-execution)
- [Contributing](#contributing)
- [License](#license)

## Introduction
In today's diverse tech landscape, organizations often use different libraries and tools for test automation based on specific use cases. This framework addresses the challenge of managing and executing tests across multiple libraries seamlessly. With the Cross-Library Test Automation Framework, you can create tests and actions using a unified interface, enabling consistent and efficient automation testing regardless of the underlying technology.

## Key Features
- Tests can be executed with different libraries and tools and can be specified using annotations or by controlling it from command line.
- Interface-based approach for implementing actions, ensuring consistency.
- Supports various categories: UI automation, API testing, and performance testing.

## Getting Started
Follow these steps to set up and use the framework:

1. Clone this repository: `git clone https://github.com/reahmedreda/Cross-Test-Automation-Framework.git`
2. List the required dependencies for the libraries and tools you plan to use in the pom.xml file.
3. Explore the provided examples and sample tests.
4. Create your own test cases and actions using the defined interfaces.
5. Execute tests using different libraries by specifying the desired library in the RunWith Annotation.

## Framework Architecture
The framework is built around a modular architecture to ensure extensibility and maintainability. It consists of the following components:
- **Tests**: test classes.
- **Modules** Contains the logic and the actions calling
- **Interfaces**: Define the contract for actions within different categories (UI, API, performance).
- **Actions**: Classes implementing actions interfaces for each library or tool.
- **Runners**: Execute the tests using the chosen library, passing the corresponding action class.


## Test Categories
The framework supports the following categories:

1. **UI Automation**:
   - Libraries: Selenium, Playwright
   - Interface: `ElementActions` and `BrowserActions`

2. **API Testing**:
   - Libraries: Rest Assured, Spring Boot
   - Interface: `APIActions`

3. **Performance Testing**:
   - Libraries: JMeter, Gatling
   - Interface: `PerformanceTestActions`


## Contributing
We welcome contributions to improve and expand this framework. If you have ideas, suggestions, or bug fixes, please feel free to open an issue or submit a pull request.

## License
This framework is provided under the [MIT License](LICENSE). You are free to use, modify, and distribute it according to the terms of the license.

---

Thank you for choosing the Cross-Library Test Automation Framework! We hope this tool simplifies your test automation efforts across diverse libraries and technologies. If you have any questions or need assistance, please reach out to our community or maintainers. Happy testing!
