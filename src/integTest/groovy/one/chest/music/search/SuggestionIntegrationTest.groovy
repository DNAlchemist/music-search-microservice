/*
 * MIT License
 *
 * Copyright (c) 2017 Mikhalev Ruslan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package one.chest.music.search

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.junit.Test
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.http.client.ReceivedResponse

@CompileStatic
class SuggestionIntegrationTest {

    private GroovyRatpackMainApplicationUnderTest app = new GroovyRatpackMainApplicationUnderTest()

    @Test
    void health() {
        assert app.httpClient.getText("health") == "ok"
    }

    @Test
    void guess() {
        String response = app.httpClient.getText("guess?text=Bob+Marley")
        def jsonSlurper = new JsonSlurper().parseText(response)
        assert jsonSlurper instanceof List
        assert jsonSlurper.containsAll([
                "Bob marley",
                "Bob marley - 40 golden masters",
                "Bob marley - sun is shining",
                "Bob marley - legends"
        ])
    }

    @Test
    void getTrack() {
        ReceivedResponse response = app.httpClient.get("tracks/67172/628177")
        assert response.statusCode == 200
        def json = new JsonSlurper().parseText(response.body.text)
        assert json instanceof Map
        assert json == [artist: 'Ozzy Osbourne', duration: 289560, name: 'Crazy Train', trackLocation: [albumId: 67172, trackId: 628177]]
    }
}
