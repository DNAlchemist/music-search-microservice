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
package one.chest.music.search.handler

import groovy.transform.CompileStatic
import one.chest.musiclibrary.MusicGuesser
import org.junit.Test
import ratpack.jackson.JsonRender

import static ratpack.groovy.test.handling.GroovyRequestFixture.handle

@CompileStatic
class SuggestionHandlerTest {

    @Test
    void testGuess() {
        def searchText = "Alan Jackson - You Never Know"
        def response = handle(new SuggestionHandler(
                guesser: [suggest: { text -> return ["Alan Jackson - Angels & Alcohol", "Alan Jackson - You Can Always Come Home", text] }] as MusicGuesser
        )) {
            uri "guess?text=${searchText}"
        }
        assert response.rendered(JsonRender).object instanceof List
        List<String> list = (List<String>) response.rendered(JsonRender).object
        assert list == ["Alan Jackson - Angels & Alcohol", "Alan Jackson - You Can Always Come Home", "Alan Jackson - You Never Know"]
    }
}