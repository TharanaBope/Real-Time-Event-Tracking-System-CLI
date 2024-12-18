# Ticket Management System - CLI

## Introduction

The **Ticket Management System** is a Java-based application designed to
manage ticket distribution and purchasing processes. It supports
multi-threaded ticket operations with dynamic configuration settings and
real-time status updates. The system features a command-line interface
(CLI) for controlling ticket release and retrieval operations.

## Setup Instructions

### Prerequisites

-   Java Development Kit (JDK): Version 8 or higher.
-   Maven: For building the application (if used as a dependency
    manager).
-   Gson Library: Required for JSON configuration parsing (can be added
    via Maven or manually).
-   Command-Line Terminal: For running the CLI application.

### Building and Running the Application

1.  **Clone the Repository**: Clone the project from your repository to
    your local machine.

2.  **Compile the Code**: Use `javac` to compile all Java files:

        javac *.java

3.  **Run the Application**: Execute the main program using:

        java Main

    Ensure all compiled `.class` files are in the same directory or
    correctly referenced in the classpath.

## Usage Instructions

### Configuring the System

1.  **Start the Application**: Run the program using:

        java Main

2.  **Access Configuration**:
    -   You can choose to load an existing configuration file or
        configure settings manually.
    -   Use options displayed to specify:
        -   Total tickets
        -   Ticket release rate (seconds)
        -   Customer retrieval rate (seconds)
        -   Maximum ticket capacity per pool
    -   Optionally, save the configuration to a file for future use.

### Starting and Monitoring the System

1.  **Start Ticket Operations**:
    -   Use the `start` command to begin ticket distribution and
        customer ticket retrieval.
2.  **Monitor Status**:
    -   Use the `status` command to check the current state of the
        ticket pool.

### Commands Overview

Command    Description
  ---------- ------------------------------------------------
`start`    Starts ticket release and customer purchase.
`status`   Displays the current state of the ticket pool.
`config`   Configures the ticketing system settings.
`exit`     Exits the application.

### Explanation of UI Controls

-   The CLI interface is text-based, requiring users to type commands.
-   Prompts guide users through configuration, saving, and loading
    settings.
-   Logs provide real-time updates about ticket operations.

## Notes

-   Interrupting a running process (e.g., via `Ctrl+C`) will gracefully
    terminate threads like the vendor and customer.
-   Any errors during configuration or runtime will be logged to the
    terminal with descriptive messages.
