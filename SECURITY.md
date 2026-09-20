# Security Policy

## Overview

Security is an important part of the Daric project.

Daric is developed with a security-focused approach as part of an ongoing effort to explore Android application security, secure development practices, and mobile application security testing.

The project follows security principles inspired by the **OWASP Mobile Application Security Verification Standard (MASVS)** and **OWASP Mobile Application Security Testing Guide (MASTG)**.

---

## Supported Versions

Daric is currently under active development.

| Version | Supported |
| ------- | --------- |
| `1.0.x` | ✅ Yes     |
| `< 1.0` | ❌ No      |

Security fixes and improvements are primarily applied to the latest supported release.

---

## Reporting a Vulnerability

If you discover a potential security vulnerability in Daric, please report it privately rather than opening a public GitHub issue.

### Preferred Method

Please use **GitHub's private vulnerability reporting / Security Advisories** when available for the repository.

If private reporting is unavailable, you may contact the maintainer directly through the contact information available on the author's website.

### Please Do Not

Please do not:

* Publish the vulnerability in a public GitHub issue.
* Publish exploit details before the issue has been reviewed.
* Share sensitive vulnerability details publicly.
* Attempt to access or modify data belonging to other users.

---

## What to Include

When reporting a security issue, please provide as much relevant information as possible.

A useful report should include:

* A clear description of the vulnerability
* The affected version, branch, or commit
* Steps to reproduce the issue
* Expected behavior
* Actual behavior
* Potential security impact
* Relevant logs or screenshots
* Proof of concept, when appropriate
* Any required device, Android version, or configuration details

For Android-specific vulnerabilities, please also include relevant information such as:

* Android version
* Device or emulator configuration
* Application version
* Debug or release build
* Relevant application component or feature
* Reproduction conditions

---

## Responsible Disclosure

Please allow reasonable time for the issue to be investigated and addressed before publicly disclosing technical details.

After a vulnerability has been reviewed and, where applicable, fixed, the maintainer may document the issue and its resolution.

Security reports are handled on a case-by-case basis depending on their severity, reproducibility, and impact.

---

## Security Scope

Security research may include areas such as:

* Android application components
* Authentication and authorization logic, where applicable
* Network communication
* API interaction
* Local data handling
* Configuration and secrets management
* Root detection and security controls
* Runtime behavior
* Application tampering
* Repackaging
* Reverse engineering
* Improper platform usage
* Security-sensitive application flows

The absence of a feature from this list does not necessarily mean that it is out of scope.

---

## Security Features

Daric currently includes several security-oriented measures, including:

* Root detection
* Rooted-device execution blocking
* R8 code shrinking and obfuscation
* Resource shrinking
* Debug-only StrictMode checks
* Dedicated security abstractions
* Build-time configuration for sensitive development values

These controls are part of an ongoing security development process and should not be interpreted as a guarantee that the application is completely secure.

---

## Security Research

Daric is also used as a practical learning and research project for Android application security.

Security testing and research may involve static analysis, dynamic analysis, reverse engineering, runtime inspection, network analysis, and application resilience testing.

All security testing should be performed against authorized copies or environments of the application.

---

## Contact

For general project-related contact, please refer to the author's website:

**Ali Ayali**
[aliayali.ir](https://aliayali.ir)

GitHub: [@AliAyali](https://github.com/AliAyali)
