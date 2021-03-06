package gradleupdate.controller

import groovy.xml.MarkupBuilder
import org.kohsuke.github.GitHub
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BadgeController {
    // TODO: extract domain class
    @Value('#{environment.GRADLEUPDATE_GITHUB_TOKEN}')
    private String githubToken

    @GetMapping(value = '/{owner}/{repo}/status.svg', produces = 'image/svg+xml')
    String status(@PathVariable String owner, @PathVariable String repo) {
        // TODO: extract domain class
        println githubToken
        def gitHub = GitHub.connectUsingOAuth(githubToken)
        def repository = gitHub.getRepository("$owner/$repo")
        def gradleWrapperProperties = repository.getFileContent('gradle/wrapper/gradle-wrapper.properties').read().text
        def version = parseGradleWrapperProperties(gradleWrapperProperties)

        // TODO
        render(version, '#9f9f9f')
    }

    // TODO: extract domain class
    static String parseGradleWrapperProperties(String gradleWrapperProperties) {
        assert gradleWrapperProperties
        try {
            def m = gradleWrapperProperties =~ /distributionUrl=.+?\/gradle-(.+?)-.+?\.zip/
            assert m
            def m0 = m[0]
            assert m0 instanceof List
            assert m0.size() == 2
            m0[1] as String
        } catch (AssertionError ignore) {
            null
        }
    }

    private static render(String message, String fill) {
        // unknown (7) -> 62
        // x.x.x   (5) -> 42
        // x.x     (3) -> 30
        final left = 47
        final c = message.size()
        final right = c.power(2) / 4 + c * 5 + 12
        final width = left + right
        final rightCentral = width - right / 2 - 1

        def writer = new StringWriter()
        new MarkupBuilder(writer).svg(xmlns: 'http://www.w3.org/2000/svg', width: width, height: 20) {
            linearGradient(id: 'a', x2: 0, y2: '100%') {
                stop(offset: 0, 'stop-color': '#bbb', 'stop-opacity': 0.1)
                stop(offset: 1, 'stop-opacity': 0.1)
            }
            rect(rx: 3, width: width, height: 20, fill: '#555')
            rect(rx: 3, x: left, width: right, height: 20, fill: fill)
            path(fill: fill, d: "M${left} 0h4v20h-4z")
            rect(rx: 3, width: width, height: 20, fill: 'url(#a)')
            g(fill: '#fff', 'text-anchor': 'middle', 'font-family': 'DejaVu Sans,Verdana,Geneva,sans-serif', 'font-size': '11') {
                text(x: 23.5, y: 15, fill: '#010101', 'fill-opacity': 0.3, 'Gradle')
                text(x: 23.5, y: 14, 'Gradle')
                text(x: rightCentral, y: 15, fill: '#010101', 'fill-opacity': 0.3, message)
                text(x: rightCentral, y: 14, message)
            }
        }
        writer.toString()
    }
}
