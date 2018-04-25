# Contributing

### **We welcome pull requests from everyone!**

By making a contribution to this project, you certify that:

**(1)** The contribution was created in whole or in part by you and you have the right to submit it under the [MIT License (MIT)][MIT]; or

**(2)** The contribution is based upon previous work that, to the best of your knowledge, is covered under the MIT or an MIT compatible open source license and you have the right under that license to submit that work with modifications, whether created in whole or in part by you; or

**(3)** The contribution was provided directly to you by some other person who certified **(1)**, **(2)** or **(3)** and you have not modified it.

**(4)** You are not expected to provide support for the contribution, except to the extent you desire to provide support. You may provide support for free, for a fee, or not at all.

**(5)** You understand and agree that this project and the contribution are public and that a record of the contribution (including all personal information you submit with it, including your sign-off)
is maintained indefinitely and may be redistributed consistent with this project or the open source license(s) involved.

## Steps
**1. Fork, then clone the repo:**
```Shell
	git clone git@github.com:your-username/currencycloud-java.git
```

**2. Work on your changes:**
- Make your change.
- Add tests for your change.
- Make commits of logical and atomic units.
- Check for unnecessary whitespace with `git diff --check` before committing.
- *nix users, verify file and directory permissions are 644 and 755 respectively before committing.
- Ensure your commit messages are clear, brief and to the point.
- If the commit fixes a bug, create an [issue][iss] and include the issue number in the commit message.

**3. Make sure ALL the tests pass:**
```Shell
	mvn test
```

**4. Push to your fork and [submit a pull request][pr] across forks.**

**5. At this point you're waiting on us.** We like to at least comment on pull requests within a week, but sometimes we may take longer. We may suggest some changes, improvements or alternatives. We may decline your contribution but we will explain our reasons. In all cases, the maintainer's decision is final.

Some things that will increase the chance that your pull request is accepted, in karma order:
- changes that are cosmetic in nature, or do not add anything substantial to the stability, functionality, or testability of the SDK will generally not be accepted.
- add tests for your changes and ensure **all** tests pass. The first thing our maintainers check on every pull request is whether Travis has given the All-OK. If there are valid reasons why the tests cannot pass, make your case in the comments section.
- follow the SDK's code style. Start reading our code and you'll get the hang of it. We try to optimize for readability which in turn leads to maintainability.
- don't change the SDK's version number. Our maintainers will assign one upon commit.
- don't add your name to the build. Our maintainers will include you in our [Hall of Fame][hof] upon commit, unless you **explicitly** tell us not to.

## Acknowledgement
Our sincere thanks for helping us create the best API for moving money anywhere around the world!

[MIT]: LICENSE.md
[iss]: https://github.com/CurrencyCloud/currencycloud-java/issues
[pr]: https://github.com/CurrencyCloud/currencycloud-java/compare
[hof]: HALL_OF_FAME.md