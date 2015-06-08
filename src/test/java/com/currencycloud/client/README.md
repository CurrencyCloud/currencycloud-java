Some of the tests in this package are [Betamax][betamax] tests. All of them have been adapted from the 
[VCR][vcr] tests in [currencycloud-ruby][cc-ruby]. Adapting the yamls of the tests from the VCR format to what
Betamax requires included things like the following:

* Renaming *.yml to *.yaml,
* Adding `name:` that seems to be required by Betamax in the first line,
* Removing some properties that don't seem to be supported by Betamax, such as `recorded_at`,
* Changing the request uri,
* Changing the yaml structure a bit, eg.:
   * changing request.body and response.body to strings (no nested properties like encoding),
   * changing response.status (no nested properties)
* Some URL-encoding tweaks to URL's and/or request bodies,
* Possibly some other minor changes.

Most or all of the changes can be (and were) done in bulk by simple regex search-and-replace actions (eg. in an IDE).

Betamax is in beta and I wasn't able to get it to record anything, so all tests function in read-only mode, ie.
if any new requests are added to the tests, they will fail.

The recommended way to expand the Betamax tests is to expand the currencycloud-ruby VCR tests first, and port the changes
(in yaml and in test code) into the Java library.



[betamax]:       http://freeside.co/betamax/
[vcr]:           https://relishapp.com/vcr/vcr/docs
[cc-ruby]:       https://github.com/CurrencyCloud/currencycloud-ruby
