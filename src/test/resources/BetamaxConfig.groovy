import co.freeside.betamax.TapeMode

betamax {
    tapeRoot = new File('src/test/resources/betamax/tapes')
    proxyPort = 5555
    proxyTimeout = 3000
    defaultMode = TapeMode.READ_ONLY
    ignoreHosts = []
    ignoreLocalhost = false
    sslSupport = true
}
