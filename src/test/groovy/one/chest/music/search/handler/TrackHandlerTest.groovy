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
import one.chest.musiclibrary.MusicLibrary
import one.chest.musiclibrary.Track
import one.chest.musiclibrary.TrackLocation
import org.junit.Test
import ratpack.jackson.JsonRender

import static ratpack.groovy.test.handling.GroovyRequestFixture.handle

@CompileStatic
class TrackHandlerTest {

    @Test
    void getTrack() {
        def response = handle(new TrackHandler(
                library: [getTrack: { TrackLocation location ->
                    assert location == new TrackLocation(1, 2)
                    return new Track() {
                        TrackLocation trackLocation = new TrackLocation(1, 2)
                        String artist = "Louis Armstrong"
                        String name = "Wonderful world"
                        Long duration = 500
                    }
                }] as MusicLibrary
        )) {
            uri "tracks/:albumId/:trackId"
            pathBinding([albumId: '1', trackId: '2'])
        }
        assert response.status.code == 200
        Track render = (Track) response.rendered(JsonRender).object
        assert render.trackLocation == new TrackLocation(1, 2)
        assert render.name == "Wonderful world"
        assert render.artist == "Louis Armstrong"
        assert render.duration == 500
    }
}